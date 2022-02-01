package com.wasp.io.echo.inputOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final byte[] ECHO = "echo ".getBytes();
    private static final byte[] buffer = new byte[64];

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        //listen
        while (true) {
            //resources close in descending order
            try (Socket socket = serverSocket.accept();
                 InputStream inputStream = new BufferedInputStream(socket.getInputStream());
                 OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())) {
                int count = 0;
                //read while available()
                if (inputStream.available() <= 0
                        && (count += inputStream.read(buffer)) != -1) {
                    System.out.println(new String(buffer, 0, count));
                    outputStream.write(ECHO);
                    outputStream.write(buffer, 0, count);
                }
            }
        }
    }
}