package com.fh.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;

            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 倒带position为0
            buffer.rewind();
        }
    }
}
