package com.fh.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        // 创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello, java!", Charset.forName("UTF-8"));
        // 使用相关的API方法
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();

            // 方式一：将content转为字符串,这样不会修改readerIndex
            System.out.println(new String(content, Charset.forName("UTF-8")));
            System.out.println("buffer=" + byteBuf);

            // 方式二：循环读取
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char) byteBuf.getByte(i));
            }

            // 方式三：
            String str = byteBuf.getCharSequence(0, 10, Charset.forName("UTF-8")).toString();
            System.out.println(str);
        }
    }
}
