package com.wasp.io.message;

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
        try (DataOutputStream dataOutputStream =
                 new DataOutputStream(new FileOutputStream("message.txt"))) {
            dataOutputStream.writeLong(message.getDate().getTime());
            dataOutputStream.writeInt(message.getAmount());
            dataOutputStream.writeInt(message.getMessage().length());
            dataOutputStream.writeBytes(message.getMessage());
        }
    }

    @Override
    public Message load() throws FileNotFoundException, IOException, ClassNotFoundException {
        try (DataInputStream inputStream = new DataInputStream(
            new FileInputStream("message.txt"))) {
            return getMessage(inputStream);
        }
    }

    private Message getMessage(DataInputStream inputStream) throws IOException {
        Date date = new Date(inputStream.readLong());
        int amount = inputStream.readInt();
        int length = inputStream.readInt();
        byte[] rawMessage = new byte[length];
        inputStream.readFully(rawMessage);
        return new Message(date, amount, new String(rawMessage));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MessageDao messageDao = new DataMessageDao();
        messageDao.save(new Message(new Date(), 10, "hello"));
        Message message = messageDao.load();
        System.out.println(message);
    }
}