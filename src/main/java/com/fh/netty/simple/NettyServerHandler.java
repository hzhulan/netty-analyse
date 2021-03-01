package com.fh.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 1. 自定义handler，继承netty定义的handlerAdapter
 * 2.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端消息
     * @param ctx 包含管道（pipline）、通道（channel）、地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 队列任务1
        ctx.channel().eventLoop().execute(() -> {
            try {
                // 耗时的业务逻辑，提交到channel对应的NioEventLoop对应的taskQueue中
                TimeUnit.SECONDS.sleep(5);
                System.out.println("任务队列任务1:" + Thread.currentThread().getName() + ", Time:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                System.out.println("发生异常" + e.getMessage());
            }
        });

        // 队列任务2
        ctx.channel().eventLoop().execute(() -> {
            try {
                // 耗时的业务逻辑，提交到channel对应的NioEventLoop对应的taskQueue中
                TimeUnit.SECONDS.sleep(5);
                System.out.println("任务队列任务2:" + Thread.currentThread().getName() + ", Time:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                System.out.println("发生异常" + e.getMessage());
            }
        });

        // 上述两个任务放在同一个队列中，由同一个线程执行，线程的id为同一个


        // 自定义定时任务
        ctx.channel().eventLoop().schedule(() -> {
            try {
                // 耗时的业务逻辑，提交到channel对应的NioEventLoop对应的taskQueue中
//                TimeUnit.SECONDS.sleep(5);
                System.out.println("自定义定时任务3:" + Thread.currentThread().getName() + ", Time:" + System.currentTimeMillis());
            } catch (Exception e) {
                System.out.println("发生异常" + e.getMessage());
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("... going on ...");

        System.out.println("服务器读取线程: " + Thread.currentThread().getName());
        System.out.println("serverCtx:" + ctx);

        // netty的byteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // write + flush
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，客户端。服务端已经读取完毕", CharsetUtil.UTF_8));
    }

    /**
     * 异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 通道关闭
        ctx.channel().close();
    }
}
