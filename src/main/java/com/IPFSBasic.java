package com;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IPFSBasic {

    public static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

    //往IPFS中添加文件
    public static String add(String path) throws IOException {

        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(path));
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    //根据hash，从IPFS中拿文件
    public static String cat(String ipfsHash) throws IOException {
        Multihash filePointer = Multihash.fromBase58(ipfsHash);
        byte[] data = ipfs.cat(filePointer);
        return new String(data);
    }
    /*
           根据输入内容生成哈希，然后以哈希为名生成对应的文件
           路径名：src/main/resources/ipfsFile/somehash.txt
           有一个bug是输入内容相同的话，生成的哈希也相同，这样会在文件里重复添加该内容，为此引入时间戳
        */
    public static String createFile(String content) throws IOException, NoSuchAlgorithmException {
        //以该方式获得时间戳所需运行时间最短
        long timestamp = System.currentTimeMillis();
        //content为要存入IPFS中的数据，对content取hash生成对应的文件名
        String hash = getHash(content+ String.valueOf(timestamp));
        //目录
        String filePath = "src/main/resources/ipfsFile";
        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(filePath + "/"+hash+".txt");
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(checkFile, true);
            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
        return checkFile.getAbsolutePath();
    }

    // 根据输入输出哈希
    // 该哈希值用于对文件命名
    public static String getHash(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "SHA-256" );
        // Change this to UTF-16 if needed
        md.update( str.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        String hex = String.format( "%064x", new BigInteger( 1, digest ) );
        System.out.println("content hash: "+hex) ;
        return hex;
    }

    /*
           IPFS存储流程：
               1.根据内容生成哈希，作为文件名，getHash(content)，返回内容对应的哈希，在第2步中引用
               2.将待存储内容作为输入，createFile(content)，返回文件路径
               3.将文件路径作为输入，add(path)，存储IPFS，返回存在IPFS中的哈希
   */
    public static String IPFStorage(String content) throws IOException, NoSuchAlgorithmException {
        String path = createFile(content);
        String ipfsHash = add(path);
        System.out.println("IPFS Hash: "+ipfsHash);
        return ipfsHash;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String hash = IPFStorage("0616 test");
        String content = cat(hash);
        System.out.println(content);

    }

}
