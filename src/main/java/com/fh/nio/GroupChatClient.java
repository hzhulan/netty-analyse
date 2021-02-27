package com.fh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupChatClient {

    private final String HOST = "127.0.0.1";

    private final int PORT = 6667;

    private Selector selector;

    private SocketChannel socketChannel;

    private String username;

    public GroupChatClient() throws IOException {
        this.selector = Selector.open();
        this.socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        this.socketChannel.configureBlocking(false);

        this.socketChannel.register(this.selector, SelectionKey.OP_READ);

        this.username = this.socketChannel.getLocalAddress().toString().substring(1);

        System.out.println(String.format("【%s】 is ok", this.username));
    }

    public void sendInfo(String info) {
        info = String.format("【%s】:%s", this.username, info);

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int readChannels = this.selector.select();
            if (readChannels > 0) {
                for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    if (key.isReadable()) {
                        SocketChannel readChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = readChannel.read(buffer);
                        if (count > 0) {
                            String msg = new String(buffer.array());
                            System.out.println(msg.trim());
                        }

                        buffer.clear();
                    }

                    it.remove();
                }
            } else {
//                System.out.println("没有可用的通道");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chatClient = new GroupChatClient();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 读取数据
        executor.submit(() -> {
            while (true) {
                chatClient.readInfo();

                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 发送数据
        Scanner sc = new Scanner(System.in);

        executor.submit(() -> {
            while (sc.hasNext()) {
                String msg = sc.nextLine();
                chatClient.sendInfo(msg);
            }
        });

    }
}
