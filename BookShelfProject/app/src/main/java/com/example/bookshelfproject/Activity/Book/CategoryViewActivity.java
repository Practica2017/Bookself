package com.example.bookshelfproject.Activity.Book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.bookshelfproject.Activity.Book.BookProfile.BookProfileActivity;
import com.example.bookshelfproject.Activity.Messages.ConversationsActivity;
import com.example.bookshelfproject.Activity.User.LoginActivity;
import com.example.bookshelfproject.Activity.User.MyProfileActivity;
import com.example.bookshelfproject.Activity.User.UsersActivity;
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
 * Created by filip on 8/15/2017.
 */

public class CategoryViewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayAdapter adapter;
    private ArrayList<String> bookTitles = new ArrayList<>();
    private ArrayList<Book> bestBooks = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        currentUserId = firebaseAuth.getCurrentUser().getUid();
        setCurrentUser(databaseReference);

        setContentView(R.layout.categories_activity);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categories);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_users:
                                startActivity(new Intent(CategoryViewActivity.this, UsersActivity.class));
                                break;

                            case R.id.navigation_chat:
                                startActivity(new Intent(CategoryViewActivity.this, ConversationsActivity.class));
                                break;

                            case R.id.navigation_profile:
                                startActivity(new Intent(CategoryViewActivity.this, MyProfileActivity.class));
                                finish();
                                break;
                            case R.id.navigation_home:
                                startActivity(new Intent(CategoryViewActivity.this, BookPopularActivity.class));
                                break;

                            case R.id.navigation_categories:
                                startActivity(new Intent(CategoryViewActivity.this, CategoriesActivity.class));
                                break;
                        }
                        return true;
                    }
                });

        listView = (ListView) findViewById(R.id.listview);




        addBestBooks(databaseReference);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BookProfileActivity.class);
                Gson gson = new Gson();
                intent.putExtra("selected_book", gson.toJson(bestBooks.get(position)));
                intent.putExtra("user", gson.toJson(user));
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        addBestBooks(databaseReference);
    }

    private void addBestBooks(DatabaseReference databaseReference) {
        bestBooks.clear();
        bookTitles.clear();
        Gson gson = new Gson();
        String category = getIntent().getStringExtra("category");

        category = category.substring(1, category.length() - 1);
        databaseReference.child("category").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot child : children) {
                    Book book = child.getValue(Book.class);
                    bestBooks.add(i, book);
                    i++;
                }
                LinkedHashMap<String, String> listItems = new LinkedHashMap<>();
                for (i = 0; i < bestBooks.size(); i++) {
                    Book book = bestBooks.get(i);
                    listItems.put(book.getTitle() + "\n  by " + book.getAuthor(), "Avg Rating: " + book.getScore() + " - " + book.getVotes() + " ratings");
                }
                List<HashMap<String, String>> listListItems = new ArrayList<HashMap<String, String>>();
                SimpleAdapter simpleAdapter = new SimpleAdapter(CategoryViewActivity.this, listListItems, R.layout.list_item,
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
                listView.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setCurrentUser(DatabaseReference databaseReference){
        databaseReference.child("users").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
