package com.fh.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))  // bossGroup添加日志处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                // netty提供的处理空闲状态的处理器
                                // 3: 3s没有都操作视为读空闲，发送心跳检测连接
                                // 5: 5s没有写操作视为写空闲，发送心跳检测连接
                                // 7: 7s没有读写操作视为读写空闲，发送心跳检测连接
                                .addLast(new IdleStateHandler(13, 5, 7, TimeUnit.SECONDS))
                                .addLast(new MyServerHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
