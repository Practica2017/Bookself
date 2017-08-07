package com.example.bookshelfproject.Activity.Book;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookshelfproject.Model.Book;

import com.example.bookshelfproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by filip on 8/3/2017.
 */

public class BookPopularActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> bestBooks = new ArrayList<>();
    private ArrayList<Book> bestBooksBook = new ArrayList<>();
    private ArrayAdapter adapter;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_books);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("books").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = new Book();
               // book = dataSnapshot.getValue(Book.class);
                dataSnapshot.getValue(Book.class).getTitle();
                bestBooksBook.add(1, book);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //  Log.d("myTag", "This is my message" + bestBooksBook.get(1).getTitle());
     
        listView = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < 10; i++) {
            bestBooks.add(i, "book nr " + i);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bestBooks);
        listView.setAdapter(adapter);
    }

    private void readBooks(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            int i = 1;
            Book book = new Book();
            book.setTitle(ds.child(i + "").getValue(Book.class).getTitle());
            book.setAuthor(ds.child(i + "").getValue(Book.class).getAuthor());
            book.setDescription(ds.child(i + "").getValue(Book.class).getDescription());
            book.setScore(ds.child(i + "").getValue(Book.class).getScore());
            book.setVotes(ds.child(i + "").getValue(Book.class).getVotes());
            bestBooksBook.add(i, book);
            i++;
        }
    }

}
