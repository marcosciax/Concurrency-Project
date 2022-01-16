package com.client.Model;

import java.util.Date;

public class Message {

    String from;
    String to;
    String content;
    Date createdAt;

    public Message(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
        createdAt = new Date();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
