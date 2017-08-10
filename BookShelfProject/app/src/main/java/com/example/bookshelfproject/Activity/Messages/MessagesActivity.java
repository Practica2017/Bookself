package com.example.bookshelfproject.Activity.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Model.Conversation;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by filip on 8/10/2017.
 */

public class MessagesActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ListView listViewUsers;
    private ArrayAdapter adapter;
    private ArrayList<String> usersName = new ArrayList<>();


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_chat);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                startActivity(new Intent(MessagesActivity.this, BookPopularActivity.class));
                            case R.id.navigation_chat:
                                //

                        }
                        return true;
                    }
                });


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        // Getting the information from loged in user
        String uid = firebaseAuth.getCurrentUser().getUid();
        addNewConversatons(uid);
        databaseReference.child("conversations").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i =0;
                for (DataSnapshot child : children) {
                    Conversation conversation = child.getValue(Conversation.class);
                    usersName.add(conversation.getName());
                    i++;
                }


                listViewUsers = (ListView) findViewById(R.id.listview);
                adapter = new ArrayAdapter(MessagesActivity.this, android.R.layout.simple_list_item_1, usersName);
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        
    }

    private void addNewConversatons(String uid) {
        Conversation conv = new Conversation("Hi", "sebi");
        databaseReference.child("conversations").child(uid).child("tlobOHcZmDUDjfApe8ezIbWzkZR2").setValue(conv);
        Conversation conv1 = new Conversation("Hi", "sebastian");
        databaseReference.child("conversations").child(uid).child("bl8aRxBHExPRBL9yXTvMESYDwL62").setValue(conv1);
        Conversation conv2 = new Conversation("Hi", "pusthiulica");
        databaseReference.child("conversations").child(uid).child("oiB4sNa7aXUfb6PxzDxMHmq0Qq32").setValue(conv2);
    }
}
