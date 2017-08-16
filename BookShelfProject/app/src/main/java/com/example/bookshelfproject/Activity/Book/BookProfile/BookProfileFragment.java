package com.example.bookshelfproject.Activity.Book.BookProfile;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.R;
import com.google.gson.Gson;

/**
 * Created by filip on 8/16/2017.
 */

public class BookProfileFragment extends Fragment {
    private static final String TAG = "BookProfileFragment";
    private Book book;
    private TextView titleTextView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_details_fragmnet,container,false);

        Bundle args = getArguments();
        //String myValue = args.getString("message");
        Gson gson = new Gson();
        //Book book = gson.fromJson(myValue,Book.class);

        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        titleTextView.setText(book.getTitle());

        return view;
    }
}
