package com.fh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 创建selector对象
        Selector selector = Selector.open();

        // 绑定一个端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // serverSocketChannel本身 注册到Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1s, 无连接");
                continue;
            }

            // 返回结果不为0: 已经获取到关注的事件
            // selector.selectedKeys() 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (Iterator<SelectionKey> keyIterator = selectionKeys.iterator(); keyIterator.hasNext(); ) {
                SelectionKey key = keyIterator.next();
                // 根据selectKey对应的通道做相应的处理

                if (key.isAcceptable()) {
                    // 客户端连接

                    // 连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);

                    System.out.println(String.format("======== 客户端连接成功[%s] ========", socketChannel.hashCode()));

                    // 注册读取事件, 同时给channel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                } else if (key.isReadable()) {
                    // 读动作 OP_READ
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    //
                    channel.read(buffer);
                    System.out.println("客户端发送数据:" + new String(buffer.array()));

                }

                // 手动从集合中移除当前的selectKey, 防止重复操作
                keyIterator.remove();
            }
        }

    }
}
