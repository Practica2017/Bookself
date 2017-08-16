package com.example.bookshelfproject.Activity.Book.BookProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshelfproject.R;

/**
 * Created by filip on 8/16/2017.
 */

public class BookReviewsFragment extends Fragment {
    private static final String TAG = "BookReviewsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_reviews_fragment,container,false);
        return view;
    }
}
