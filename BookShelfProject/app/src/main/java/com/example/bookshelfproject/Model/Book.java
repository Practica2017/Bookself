package com.example.bookshelfproject.Model;

/**
 * Created by filip on 8/3/2017.
 */

public class Book {
    private String id;
    private String title;
    private String author;
    private float score;
    private int votes;
    private String description;
    private String category;

    public Book(String id, String title, String author, float score, int votes, String description, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.score = score;
        this.votes = votes;
        this.description = description;
        this.category = category;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
