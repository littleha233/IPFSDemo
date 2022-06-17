package com;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.IPFSBasic.cat;
import static com.IPFSBasic.ipfs;

public class IPFSDemo {

    public static void main(String[] args) throws IOException {

//        String hash = IPFStorage("blog publish article");
//        System.out.println(hash);
        //IPFS版本号
        System.out.println(ipfs.version());

        String content = cat("QmVFRQABYYHeb84q7WiscT1QRMJvp7vwEjgN3CLj8BH39Y");
        System.out.println(content);

    }
}
