package com.example.bookshelfproject.Activity.Book;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.bookshelfproject.Activity.Messages.ChatActivity;
import com.example.bookshelfproject.Activity.User.LoginActivity;
import com.example.bookshelfproject.Model.Book;

import com.example.bookshelfproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;

/**
 * Created by filip on 8/3/2017.
 */

public class BookPopularActivity extends Activity {

    private ListView listView;
    private ArrayList<String> bookTitles = new ArrayList<>();
    private static List<Book> bestBooks = new ArrayList<>();
    private ArrayAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_books);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                //
                            case R.id.navigation_chat:
                                startActivity(new Intent(BookPopularActivity.this, ChatActivity.class));

                        }
                        return true;
                    }
                });


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        addBestBooks(databaseReference);
    }

    private void addBestBooks(DatabaseReference databaseReference) {
        bestBooks.clear();
        bookTitles.clear();
        databaseReference.child("books").orderByChild("score").startAt("4").endAt("5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot child : children) {
                    Book book = child.getValue(Book.class);
                    bestBooks.add(i, book);
                    i++;
                }
                for (i = 0; i < bestBooks.size(); i++) {
                    bookTitles.add(i, bestBooks.get(i).getTitle());
                }
                listView = (ListView) findViewById(R.id.listview);
                adapter = new ArrayAdapter(BookPopularActivity.this, android.R.layout.simple_list_item_1, bookTitles);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
