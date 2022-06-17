# IPFSDemo
Utilizing Java to implement IPFS invocation by adding files into IPFS and cat files from IPFS.

IPFS is a decentralized file system which can be used to store data in a decentralzied manner by file format.

### Start the IPFS
using the instruction `ipfs daemon`

### some tips
peer identity: 12D3KooWFS2AmXsEaLbMN3ZBjzNz95FTeSjBXiDQxU5UDy9LCw3r

IPFS：浏览器界面：`localhost:5001/webui`

initializing IPFS node at /Users/meng/.ipfs

小熊头像：QmeTW8KQw8X5ktoFgq44QwWBJvbcx53RnMQuDJJGvcKAha

### the dependency and the configuration the pom.xml 
```
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.ipfs</groupId>
            <artifactId>java-ipfs-http-client</artifactId>
            <version>v1.3.3</version>
        </dependency>
    </dependencies>
```
### Java invocation

init IPFS
```
 public static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
 ```
 
 The data is stored in a file which is named by the data hash and stored in the directory `resource/ipfsFile`.
 
 The basic operation is `add` and `cat`.
 ```
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
```
 
All the operations aiming to store data are integrated in the function `IPFStorage`
```
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
```

### An invocation example

As is shown in `IPFSDemo.Java`
```
  // store data in IPFS
  String hash = IPFStorage("0617 test");

  // get the data stored previously
  String content = cat(hash);
  System.out.println(content);
 ```
 
