package com.example.bookshelfproject.Activity.Book.BookProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookshelfproject.Activity.Book.CategoriesActivity;
import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.Model.Rating;
import com.example.bookshelfproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by filip on 8/16/2017.
 */

public class BookReviewsFragment extends Fragment {
    private static final String TAG = "BookReviewsFragment";
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    private ArrayList<String> listViewArray = new ArrayList<>();
    private ArrayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_reviews_fragment,container,false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        listView = (ListView) view.findViewById(R.id.listview);

        //listView.getAdapter().notify();

        Gson gson = new Gson();
        String strObj = getActivity().getIntent().getStringExtra("selected_book");
        final Book book = gson.fromJson(strObj, Book.class);

        databaseReference.child("book-reviews").child(book.getId()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                listViewArray.clear();
                for (DataSnapshot child : children) {
                    Rating rating = child.getValue(Rating.class);
                    ratings.add(rating);
                    listViewArray.add(rating.getRating()+" - "+rating.getReview()+" - " + rating.getUsername());
                }
                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listViewArray);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
