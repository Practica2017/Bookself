package com.example.bookshelfproject.Model;

import java.util.Date;

/**
 * Created by filip on 8/9/2017.
 */

public class User {
    private long Id;
    private String Email;
    private String Name;

    public User(long id, String email, String name) {
        this.Id = id;
        this.Email = email;
        this.Name = name;
    }

    public User() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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
