package com;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.IPFSBasic.*;

public class IPFSDemo {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

//        String hash = IPFStorage("blog publish article");
//        System.out.println(hash);
        //IPFS版本号
        System.out.println(ipfs.version());

        // store data in IPFS
        String hash = IPFStorage("0617 test");

        // get the data stored previously
        String content = cat(hash);
        System.out.println(content);

    }
}
