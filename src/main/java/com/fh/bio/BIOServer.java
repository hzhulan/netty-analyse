package com.fh.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty BIO
 * 缺点：
 * 1. 并发较大的时候需要创建大量的线程
 * 2. 没有数据传输的时候线程会闲置阻塞，浪费资源
 */
public class BIOServer {

    /**
     * 测试方法：
     * 1. 启动main方法开启服务端
     * 2. 开启telnet客户端：卸载程序-->开启和关闭功能-->勾选开启telnet客户端
     * 3. cmd ==> telnet 172.0.0.1 6666
     * 4. ctrl + ] 进入输入模式
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // 线程池机制
        // 思路 1. 创建线程池 2. 有客户端连接，创建一个线程与之通讯

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器已启动...");

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("连接一个客户端");

            // 创建线程与之通讯
            newCachedThreadPool.execute(() -> {
                handler(socket);
            });

        }

    }

    // 编写一个handler方法进行通讯
    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            int read;
            while ((read = inputStream.read(bytes)) != -1) {
                // 输出客户端发送的数据
                System.out.println(String.format("[%s]%s", Thread.currentThread().getId(), new String(bytes, 0, read, "gbk")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
