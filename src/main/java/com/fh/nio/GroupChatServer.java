package com.fh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int port = 6667;

    /**
     * 初始化
     */
    public GroupChatServer() {
        try {
            this.selector = Selector.open();
            this.listenChannel = ServerSocketChannel.open();

            // 绑定端口
            this.listenChannel.socket().bind(new InetSocketAddress(6667));
            this.listenChannel.configureBlocking(false);

            this.listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = this.selector.select();
                if (count == 0) {
                    System.out.println("等待中...");
                    continue;
                }

                for(Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator(); keyIterator.hasNext();) {
                    SelectionKey key = keyIterator.next();

                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = this.listenChannel.accept();

                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println(String.format("【%s】上线了", socketChannel.getRemoteAddress()));
                    } else if (key.isReadable()) {

                        // 读取并转发消息
                        readData(key);
                    }

                    // 删除当前的key，防止重复操作
                    keyIterator.remove();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据
     * @param key
     */
    private void readData(SelectionKey key) {

        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
//        ByteBuffer buffer = (ByteBuffer) key.attachment();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);

            if (count > 0) {
                // 将缓存区的数据转为字符串
                String msg = new String(buffer.array());
                System.out.println("from 客户端:" + msg);
                // 向其他客户端转发消息
                sendInfoToOtherClients(msg, channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(String.format("【%s】离线了", channel.getRemoteAddress()));
                // 取消注册
                key.channel();
                // 关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        // 服务器转发消息
        System.out.println("服务器转发消息中...");

        for (SelectionKey key : this.selector.keys()) {
            SelectableChannel targetChannel = key.channel();

            // 排除自己
            if (targetChannel instanceof SocketChannel) {
                // 转型

                String sendMsg = msg;
                if(targetChannel == self) {
                    sendMsg = "【我】" + msg.split(":")[2];
                }
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer wrap = ByteBuffer.wrap(sendMsg.getBytes());
                dest.write(wrap);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
