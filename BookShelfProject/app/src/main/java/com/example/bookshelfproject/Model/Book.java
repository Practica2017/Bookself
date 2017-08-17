package com.example.bookshelfproject.Model;

import java.io.Serializable;

/**
 * Created by filip on 8/3/2017.
 */

public class Book implements Serializable {
    private long Id;
    private String Title;
    private String Author;
    private float Score;
    private int Votes;
    private String Description;
    private String Category;

    public Book(long Id, String Title, String Author, float Score, int Votes, String Description, String Category) {
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

    public float getScore() {
        return Score;
    }

    public void setScore(float Score) {
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

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Score='" + Score + '\'' +
                ", Votes=" + Votes +
                ", Description='" + Description + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }
}
