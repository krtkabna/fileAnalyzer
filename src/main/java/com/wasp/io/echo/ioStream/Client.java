package com.wasp.io.echo.ioStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private static final byte[] MSG = "Hello from client".getBytes();
    private static final byte[] buffer = new byte[64];

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 3000);
             OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            InputStream inputStream = new BufferedInputStream(socket.getInputStream())){
            outputStream.write(MSG);
            outputStream.flush();
            int count = inputStream.read(buffer);
            System.out.println(new String(buffer, 0, count));
        }
    }
}
