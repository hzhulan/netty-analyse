package com.fh.nio;

import java.nio.IntBuffer;

/**
 * nio实现非阻塞的核心为Buffer，通过缓冲区缓冲，满了再用线程进行读写
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建buffer, 大小为5
        IntBuffer buffer = IntBuffer.allocate(10);
        // 向buffer存放数据
        for (int i = 0; i < 10; i++) {
            buffer.put(i * 2);
        }
        // 从buffer读取数据

        // 将buffer转换，转为读取
        buffer.flip();
        while (buffer.hasRemaining()) {
            int number = buffer.get();
            System.out.println(String.format("读取数据:%d", number));
        }
    }
}
