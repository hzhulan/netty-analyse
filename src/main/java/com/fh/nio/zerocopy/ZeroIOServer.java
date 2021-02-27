package com.fh.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZeroIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int readCount = dis.read(bytes, 0, bytes.length);
                    if (readCount == -1) {
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
