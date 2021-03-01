package com.fh.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * HttpObject: 客户端和服务端相互通信的数据
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws URISyntaxException {
        // 判断msg是否是httpRequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg类型: " + msg.getClass());
            System.out.println("客户端地址: " + channelHandlerContext.channel().remoteAddress());
            System.out.println(String.format("handler: %s, pipline: %s", channelHandlerContext.handler().hashCode(), channelHandlerContext.pipeline().hashCode()));

            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.getUri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求图标资源，不做响应");
                return;
            }

            // 回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("你好，我是服务器", CharsetUtil.UTF_8);

            // 根据HTTP协议, 创建response
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            channelHandlerContext.writeAndFlush(response);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler已经添加");
    }
}
