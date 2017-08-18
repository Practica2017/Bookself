package com.example.bookshelfproject.Activity.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 8/18/2017.
 */

public class ReadBooksActivity extends AppCompatActivity {
    private ListView listViewReadBooks;
    private TextView textView;
    private ArrayList <Book> readBooks = new ArrayList<>();
    private ArrayList<String> readBooksTitles = new ArrayList<>();
    private LinkedHashMap<String, String> listItems = new LinkedHashMap<>();
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_books);

        listViewReadBooks = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.textViewName);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("userToChat");
        final User userToChat = gson.fromJson(strObj, User.class);

        textView.setText(userToChat.getName());

        databaseReference.child("user-read-books").child(userToChat.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot child : children) {
                    Book book = child.getValue(Book.class);
                    readBooks.add(i, book);
                    i++;
                }

                for (i = 0; i < readBooks.size(); i++) {
                    Book book = readBooks.get(i);
                    listItems.put(book.getTitle() + "\n  by " + book.getAuthor(), "Avg Rating: " + book.getScore() + " - " + book.getVotes() + " ratings");
                }
                List<HashMap<String, String>> listListItems = new ArrayList<HashMap<String, String>>();
                SimpleAdapter simpleAdapter = new SimpleAdapter(ReadBooksActivity.this, listListItems, R.layout.list_item,
                        new String[]{"First Line", "Second Line"},
                        new int[]{R.id.textItem1, R.id.textItem2});

                Iterator iterator = listItems.entrySet().iterator();
                while (iterator.hasNext()) {
                    HashMap<String, String> resultsMap = new HashMap<String, String>();
                    Map.Entry pair = (Map.Entry) iterator.next();
                    resultsMap.put("First Line", pair.getKey().toString());
                    resultsMap.put("Second Line", pair.getValue().toString());
                    listListItems.add(resultsMap);

                }
                listViewReadBooks.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
