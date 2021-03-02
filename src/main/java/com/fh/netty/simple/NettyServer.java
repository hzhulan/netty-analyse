package com.fh.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

public class NettyServer {

    private final Map<String, SocketChannel> cache = new HashMap<String, SocketChannel>();

    public static void main(String[] args) throws InterruptedException {
        // 创建BossGrouper和WorkerGroup
        // bossGroup只处理连接请求
        // workGroup处理真正的业务逻辑
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try {
            // 创建服务器端的启动对象，配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 使用链式编程进行设置
            bootstrap.group(bossGroup, workerGroup) // 设置2个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 向pipline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            System.out.println("客户socketChannel hashCode=" + socketChannel.hashCode());

                            // 将socketChannel放到集合中管理，当有消息推送式加入到NioEventLoop对应的taskQueues中执行

                            socketChannel.pipeline().addLast(new NettyServerHandler());

                        }
                    }); // 给eventLoop对应的管道

            System.out.println("... Server is ready...");

            // 绑定端口并且同步，生成一个channelFuture对象
            ChannelFuture cf = bootstrap.bind(6668).sync();

            cf.addListener((future) -> {
                if (cf.isSuccess()) {
                    System.out.println("监听端口6668成功");
                } else {
                    System.out.println("监听端口6668失败");
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
//            cf.channel().closeFuture();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
