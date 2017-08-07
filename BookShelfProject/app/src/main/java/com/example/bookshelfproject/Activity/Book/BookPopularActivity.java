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
import java.util.List;

/**
 * Created by filip on 8/3/2017.
 */

public class BookPopularActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> bookTitles = new ArrayList<>();
    private List<Book> bestBooks = new ArrayList<>();
    private ArrayAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_books);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        /*Book bookTest = new Book(1,"Moara cu Noroc","Ioan Slavici",5,10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest.getId()+"").setValue(bookTest);*//*
        Book bookTest1 = new Book(1,"Moara cu Noroc","Ioan Slavici",5,10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest1.getId()+"").setValue(bookTest1);
        Book bookTest2 = new Book(3,"Fram ursul polar","Ioan Slavici",5,10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest2.getId()+"").setValue(bookTest2);
        Book bookTest3 = new Book(4,"Amintiri din copilarie","Ioan Slavici",5,10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest3.getId()+"").setValue(bookTest3);
        Book bookTest4 = new Book(5,"Ion","Ioan Slavici",5,10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest4.getId()+"").setValue(bookTest4);*/

        databaseReference.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i=0;
                for (DataSnapshot child : children) {
                    Book book = child.getValue(Book.class);
                    bestBooks.add(i,book);
                    i++;
                    Log.d("my msh", "----------------------------------------title: "+book.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView = (ListView) findViewById(R.id.listview);
        for (int i = 0; i < bestBooks.size(); i++) {
            bookTitles.add(i,bestBooks.get(i).getTitle());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bookTitles);
        listView.setAdapter(adapter);
    }


}
