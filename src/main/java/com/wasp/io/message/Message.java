package com.wasp.io.message;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Date date;
    private int amount;
    private String message;

    public Message(Date date, int amount, String message) {
        this.date = date;
        this.amount = amount;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
            "date=" + date +
            ", amount=" + amount +
            ", message='" + message + '\'' +
            '}';
    }
}