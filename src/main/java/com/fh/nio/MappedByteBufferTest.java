package com.fh.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer可以让文件直接在堆外内存修改
 */
public class MappedByteBufferTest {
    public static void main(String[] args) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("target/file01.txt", "rw");
             // 获取对应的通道
             FileChannel channel = randomAccessFile.getChannel()) {

            /**
             * 参数1： 读写模式
             * 参数2： 0 起始位置
             * 参数3： 5 映射到内容的大小 可以直接修改的范围 0 - 5， 即下标为0-4的字符
             */
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
            mappedByteBuffer.put(9, (byte) 'M');
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
