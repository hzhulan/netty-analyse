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

        long count = 0;
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("============= 有客户端连接 =============");
            int readCount = 0;

            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(buffer);
                    count += readCount;
                    System.out.println("读取总字节:" + count + ". 当前拷贝字节: " + readCount);
                } catch (IOException e) {
                    e.printStackTrace();
                    readCount = -1;
                    System.out.println("读取异常，退出");
                }
                // 倒带position为0
                buffer.rewind();
            }
        }
    }
}
