package com.fh.netty.simple;

import io.netty.util.NettyRuntime;

public class NettyTest {

    public static void main(String[] args) {
        // computer的核数2
//        System.out.println(NettyRuntime.availableProcessors());

        long time1 = 1614584186606L;
        long time2 = 1614584191606L;
        long time3 = 1614584196606L;
        System.out.println(time2 - time1);
        System.out.println(time3 - time2);
    }
}
