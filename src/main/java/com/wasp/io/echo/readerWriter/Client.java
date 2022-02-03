package com.wasp.io.echo.readerWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static java.lang.System.lineSeparator;

public class Client {
    private static final String EXIT = "exit";
    private static final String ENDLINE = lineSeparator();
    private static final String CLIENT_CLOSE_MSG = "EXIT has been called\r\n";

    public static void main(String[] args) throws IOException {
        while (true) {
            try (Socket socket = new Socket("localhost", 3000);
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                BufferedReader cli = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter message: ");
                String msg = cli.readLine();
                if (msg.equalsIgnoreCase(EXIT)) {
                    writer.write(EXIT + ENDLINE);
                    writer.flush();
                    cli.close();
                    System.out.println(CLIENT_CLOSE_MSG);
                    break;
                }
                writer.write(msg + ENDLINE);
                writer.flush();
                String response = reader.readLine();
                System.out.println(response);
            }
        }

    }
}
