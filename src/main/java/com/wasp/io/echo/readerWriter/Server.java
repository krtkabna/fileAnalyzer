package com.wasp.io.echo.readerWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String ECHO = "echo ";
    private static final String EXIT = "EXIT";
    private static final String CLIENT_CLOSE_MSG = "EXIT has been called\r\n";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        while (true) {
            try (Socket socket = serverSocket.accept();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                String input = reader.readLine();
                if (EXIT.equalsIgnoreCase(input)) {
                    System.out.println(CLIENT_CLOSE_MSG);
                } else {
                    String output = ECHO + input;
                    writer.write(output);
                    writer.flush();
                    System.out.println(output);
                }
            }
        }
    }
}
