package com.example.bookshelfproject.Model;

/**
 * Created by filip on 8/10/2017.
 */

public class Conversation {
    private String lastMessage;
    private String name;

    public Conversation(String lastMessage, String name) {
        this.lastMessage = lastMessage;
        this.name = name;
    }

    public Conversation() {
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
