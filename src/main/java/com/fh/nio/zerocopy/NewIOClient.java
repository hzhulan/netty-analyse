package com.fh.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress(7001));

        FileInputStream fis = new FileInputStream("target/Archive.zip");
//        FileInputStream fis = new FileInputStream("target/32.zip");
        FileChannel fileChannel = fis.getChannel();

        // 准备发送
        long startTime = System.currentTimeMillis();
        // linux transferTo就可以实现 window下一次调用只能发送8M就需要分段传输文件，注意传输起点位置


        long size = fileChannel.size();

        // 8M ==> bit数
        long every = 8 * 1024 * 1024;

//        long time = size/every + 1;

        long transferCount = 0;

//        while (transferCount < size) {
//            long perTransferSize = transferCount + every <= size ? every : (size - transferCount);
//            transferCount += fileChannel.transferTo(0, perTransferSize, socketChannel);
//            System.out.println("============== 进行一次拷贝 =============");
//        }

        transferCount += fileChannel.transferTo(0, size, socketChannel);

        System.out.println(String.format("发送总字节数:%s, 总耗时: %s", transferCount, (System.currentTimeMillis() - startTime)));

        fileChannel.close();
        fis.close();

    }
}
