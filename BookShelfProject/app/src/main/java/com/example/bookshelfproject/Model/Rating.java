package com.example.bookshelfproject.Model;

/**
 * Created by filip on 8/17/2017.
 */

public class Rating {
    private float rating;
    private String review;
    private String username;

    public Rating(float rating, String review,String username) {
        this.rating = rating;
        this.review = review;
        this.username = username;
    }

    public float getRating() {
        return rating;
    }

    public Rating() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
