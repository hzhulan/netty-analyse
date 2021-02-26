package com.fh.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 使用通道进行文件的快速拷贝
 */
public class NIOChannel04 {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("target/file01.txt");
             FileOutputStream fos = new FileOutputStream("target/file03.txt");
             FileChannel sourceChannel = fis.getChannel();
             FileChannel destChannel = fos.getChannel()
        ) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
