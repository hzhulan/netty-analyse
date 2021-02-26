package com.fh.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "你好, netty!";
        FileOutputStream fos = new FileOutputStream("target/file01.txt");

        // 通过outputStream获取channel
        try(FileChannel fileChannel = fos.getChannel()) {

            // 将字符串放入buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            buffer.put(str.getBytes());

            buffer.flip();

            fileChannel.write(buffer);
        }
    }
}
