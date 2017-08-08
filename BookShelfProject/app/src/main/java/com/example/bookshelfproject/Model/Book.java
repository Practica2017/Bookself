package com.example.bookshelfproject.Model;

/**
 * Created by filip on 8/3/2017.
 */

public class Book {
    private long Id;
    private String Title;
    private String Author;
    private String Score;
    private int Votes;
    private String Description;
    private String Category;

    public Book(long Id, String Title, String Author, String Score, int Votes, String Description, String Category) {
        this.Id = Id;
        this.Title = Title;
        this.Author = Author;
        this.Score = Score;
        this.Votes = Votes;
        this.Description = Description;
        this.Category = Category;
    }

    public Book() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public int getVotes() {
        return Votes;
    }

    public void setVotes(int Votes) {
        this.Votes = Votes;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }
}
