package com.fh.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // netty提供的http编码-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}
