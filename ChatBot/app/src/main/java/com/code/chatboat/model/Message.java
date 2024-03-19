package com.code.chatboat.model;

public class Message {
    private String content;
    private String senderId;
    private String receiverId;

    public Message(String content, String senderId, String receiverId) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
