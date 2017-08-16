package com.example.bookshelfproject.Activity.Book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookshelfproject.Activity.Messages.ChatActivity;
import com.example.bookshelfproject.Activity.Messages.ConversationsActivity;
import com.example.bookshelfproject.Activity.User.LoginActivity;
import com.example.bookshelfproject.Activity.User.UsersActivity;
import com.example.bookshelfproject.Model.Book;
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

/**
 * Created by filip on 8/15/2017.
 */

public class CategoriesActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayAdapter adapter;
    ArrayList<String> categories = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categories);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_users:
                                startActivity(new Intent(CategoriesActivity.this, UsersActivity.class));
                                break;

                            case R.id.navigation_chat:
                                startActivity(new Intent(CategoriesActivity.this, ConversationsActivity.class));
                                break;

                            case R.id.navigation_logout:
                                firebaseAuth.signOut();
                                startActivity(new Intent(CategoriesActivity.this, LoginActivity.class));
                                break;
                            case R.id.navigation_home:
                                startActivity(new Intent(CategoriesActivity.this, BookPopularActivity.class));
                                break;
                        }
                        return true;
                    }
                });

        listView =(ListView) findViewById(R.id.listview);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        databaseReference.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children){
                     String category = child.getKey();
                    categories.add(category);
                }
                adapter = new ArrayAdapter(CategoriesActivity.this,android.R.layout.simple_list_item_1, categories);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CategoryViewActivity.class);
                Gson gson = new Gson();
                intent.putExtra("category", gson.toJson(categories.get(position)));
                startActivity(intent);
            }
        });
    }
}
