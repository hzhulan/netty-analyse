package com.fh.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\git\\netty-analyse\\target\\file01.txt");
        try(FileInputStream fis = new FileInputStream(file)) {
            FileChannel fileChannel = fis.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());


            fileChannel.read(byteBuffer);


            byteBuffer.flip();


//            while (true) {
//                if (!byteBuffer.hasRemaining()) {
//                    break;
//                }
//                byte b = byteBuffer.get();
//                System.out.println(b);
//            }

            System.out.println(String.format("读取内容: %s", new String(byteBuffer.array(), "utf-8")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
