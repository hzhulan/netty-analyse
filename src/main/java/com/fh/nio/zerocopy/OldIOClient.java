package com.fh.nio.zerocopy;

import java.io.*;
import java.net.Socket;

public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7001);
//        FileInputStream fis = new FileInputStream("target/32.zip");
        FileInputStream fis = new FileInputStream("target/Archive.zip");
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];

        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();

        while ((readCount = fis.read(buffer)) >= 0) {
            total += readCount;
            dos.write(buffer);
        }

        System.out.println("发送总字节数: " + total + ", 总耗时: " + (System.currentTimeMillis() - startTime));

        dos.close();
        socket.close();
        fis.close();

    }
}
