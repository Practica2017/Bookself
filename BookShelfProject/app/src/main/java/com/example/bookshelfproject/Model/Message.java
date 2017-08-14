package com.example.bookshelfproject.Model;

import java.security.Timestamp;

/**
 * Created by filip on 8/11/2017.
 */

public class Message {
    private String id;
    private String text;
    private String userAuthor;
    private long timestamp;

    public Message(String id, String text, String userAuthor, long timestamp) {
        this.id = id;
        this.text = text;
        this.userAuthor = userAuthor;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(String userAuthor) {
        this.userAuthor = userAuthor;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
