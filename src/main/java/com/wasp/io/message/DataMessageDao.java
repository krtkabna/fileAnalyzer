package com.wasp.io.message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class DataMessageDao implements MessageDao {
    @Override
    public void save(Message message) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(
            new BufferedOutputStream(new FileOutputStream("message.txt")))) {
            dataOutputStream.writeLong(message.getDate().getTime());
            dataOutputStream.writeInt(message.getMessage().length());
            dataOutputStream.writeBytes(message.getMessage());
            dataOutputStream.writeInt(message.getAmount());
        }
    }

    @Override
    public Message load() throws FileNotFoundException, IOException, ClassNotFoundException {
        try (DataInputStream inputStream = new DataInputStream(
            new BufferedInputStream(new FileInputStream("message.txt")))) {
            return getMessage(inputStream);
        }
    }

    private Message getMessage(DataInputStream inputStream) throws IOException {
        Date date = new Date(inputStream.readLong());
        int length = inputStream.readInt();
        byte[] rawMessage = new byte[length];
        inputStream.readFully(rawMessage);
        int amount = inputStream.readInt();
        return new Message(date, new String(rawMessage), amount);
    }
}