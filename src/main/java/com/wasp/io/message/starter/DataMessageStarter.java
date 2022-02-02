package com.wasp.io.message.starter;

import com.wasp.io.message.DataMessageDao;
import com.wasp.io.message.Message;
import com.wasp.io.message.MessageDao;
import java.io.IOException;
import java.util.Date;

public class DataMessageStarter {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MessageDao messageDao = new DataMessageDao();
        messageDao.save(new Message(new Date(), "hello there", 42));
        Message message = messageDao.load();
        System.out.println(message);
    }
}
