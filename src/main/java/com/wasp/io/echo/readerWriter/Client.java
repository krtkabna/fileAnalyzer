package com.wasp.io.echo.readerWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    private static final String MSG = "Hello from client";

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 4000);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer.write(MSG);
            String response = reader.readLine();
            System.out.println(response);
        }
    }
}
