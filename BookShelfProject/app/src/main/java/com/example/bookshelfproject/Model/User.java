package com.example.bookshelfproject.Model;

import java.util.Date;

/**
 * Created by filip on 8/9/2017.
 */

public class User {
    private String Email;
    private String Name;

    public User(String email, String name) {
        Email = email;
        Name = name;
    }

    public User() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
