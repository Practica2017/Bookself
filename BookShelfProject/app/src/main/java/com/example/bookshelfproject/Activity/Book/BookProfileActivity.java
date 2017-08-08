package com.example.bookshelfproject.Activity.Book;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.bookshelfproject.R;

import layout.book.BookDetailsFragment;
import layout.book.BookReviewsFragment;

/**
 * Created by filip on 8/8/2017.
 */

public class BookProfileActivity extends AppCompatActivity{
    private Fragment fragmentBookDetails;
    private Fragment fragmentBookReview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_profile);
        fragmentBookDetails = new BookDetailsFragment();
        fragmentBookReview = new BookReviewsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(fragmentBookDetails);

    }
}
