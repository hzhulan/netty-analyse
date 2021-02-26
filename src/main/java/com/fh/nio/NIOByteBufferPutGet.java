package com.fh.nio;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('尚');
        buffer.putShort((short) 4);
        // 取出
        buffer.flip();

        int anInt = buffer.getInt();
        long aLong = buffer.getLong();
        char aChar = buffer.getChar();
        short aShort = buffer.getShort();

        System.out.println(String.format("数据分别为:%s, %s, %s, %s", anInt, aLong, aChar, aShort));
    }
}
