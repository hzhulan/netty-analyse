package com.fh.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocket服务器端
 * 客户端为 hello.html
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 基于http的协议，需要使用http的编码解码器
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        // 块方式写
                        pipeline.addLast(new ChunkedWriteHandler());
                        // http数据在传输过程中是分段的,就是可以将多个段聚合起来
                        // 这就是为什么当浏览器发送大量数据时，就会发出多次http请求
                        pipeline.addLast(new HttpObjectAggregator(8192));

                        /**
                         * 1. webSocket数据是以帧(frame)的形式传递的
                         * 2. webSocketFrame下面有六个子类
                         * 3. webSocket请求时 ws://localhost:7000/hello hello为请求的url在此处配置
                         * 4. WebSocketServerProtocolHandler核心功能为将一个http协议升级为一个webSocket协议保持长连接
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                        // 自定义处理器，处理业务逻辑
                        pipeline.addLast(new MyTextWebSocketFrameHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
