package com.example.bookshelfproject.Model;

import java.security.Timestamp;

/**
 * Created by filip on 8/11/2017.
 */

public class Message {
    private String id;
    private String text;
    private String userAuthorId;
    private String userAuthrorName;
    private long timestamp;

    public Message(String id, String text, String userAuthorId, long timestamp, String userAuthrorName) {
        this.id = id;
        this.text = text;
        this.userAuthorId = userAuthorId;
        this.userAuthrorName = userAuthrorName;
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

    public String getUserAuthorId() {
        return userAuthorId;
    }

    public void setUserAuthorId(String userAuthorId) {
        this.userAuthorId = userAuthorId;
    }

    public String getUserAuthrorName() {
        return userAuthrorName;
    }

    public void setUserAuthrorName(String userAuthrorName) {
        this.userAuthrorName = userAuthrorName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
