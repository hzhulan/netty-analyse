package com.fh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * scattering: 将数据写入到buffer, 可以采用数组, 依次写
 * gathering: 从buffer读取数据, 可以采用buffer数组，依次读
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            InetSocketAddress address = new InetSocketAddress(7000);

            // 绑定端口到socket并启动
            serverSocketChannel.socket().bind(address);

            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);

            // 等待客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            int messageLength = 8;
            while (true) {
                int byteRead = 0;
                while (byteRead < messageLength) {
                    long length = socketChannel.read(byteBuffers);
                    byteRead += length;

                    System.out.println("byteRead=" + byteRead);
                    // 使用流打印当前的buffer的position和limit
                    Arrays.asList(byteBuffers).stream().map(buffer -> String.format("position=%s, limit=%s", buffer.position(), buffer.limit())).forEach(System.out::println);
                }

                // 将所有的buffer进行反转
                Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

                // 将数据读出显示到客户端
                long byteWrite = 0;
                while (byteWrite < messageLength) {
                    long write = socketChannel.write(byteBuffers);
                    byteWrite += write;
                }

                // 清空
                Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

                System.out.println(String.format("byteRead=%s, byteWrite=%s, messageLength=%s", byteRead, byteWrite, messageLength));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
