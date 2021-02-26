package com.fh.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {

        try (
                FileInputStream fis = new FileInputStream("target/file01.txt");
                FileOutputStream fos = new FileOutputStream("target/file02.txt")) {
            FileChannel fileChannel01 = fis.getChannel();

            FileChannel fileChannel02 = fos.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            while (true) {

                // 清空buffer
                byteBuffer.clear();
                int read = fileChannel01.read(byteBuffer);
                if (read == -1) {
                    break;
                }

                byteBuffer.flip();
                fileChannel02.write(byteBuffer);
            }
        }
    }
}
