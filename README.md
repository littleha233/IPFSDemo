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
`
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

`
