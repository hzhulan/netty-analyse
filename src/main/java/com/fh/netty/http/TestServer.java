package com.fh.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) throws InterruptedException {
        // 创建BossGrouper和WorkerGroup
        // bossGroup只处理连接请求
        // workGroup处理真正的业务逻辑
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 默认CPU核数 * 2
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .handler(null)                                  // 作用在bossGroup上
                    .childHandler(new TestServerInitializer());     // 作用在workGroup上

            // 调试时没反应的时候尝试切换端口,防止端口被禁用
            ChannelFuture channelFuture = serverBootstrap.bind(5668).sync();

            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
