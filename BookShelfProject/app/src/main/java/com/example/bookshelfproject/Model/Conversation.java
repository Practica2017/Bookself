package com.example.bookshelfproject.Model;

import java.io.Serializable;

/**
 * Created by filip on 8/10/2017.
 */

public class Conversation implements Serializable{
    private String id;
    private String lastMessage;
    private String name;
    private String myName;

    public Conversation(String id, String lastMessage, String name,String myName) {
        this.id=id;
        this.lastMessage = lastMessage;
        this.name = name;
        this.myName = myName;
    }

    public Conversation() {
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", lastMessage='" + lastMessage + '\'' +
                ", name='" + name + '\'' +
                ", myName='" + myName + '\'' +
                '}';
    }
}
