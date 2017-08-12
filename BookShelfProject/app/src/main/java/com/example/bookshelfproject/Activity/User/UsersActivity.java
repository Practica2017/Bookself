package com.example.bookshelfproject.Activity.User;

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

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Activity.Messages.ChatActivity;
import com.example.bookshelfproject.Activity.Messages.ConversationsActivity;
import com.example.bookshelfproject.Model.Conversation;
import com.example.bookshelfproject.Model.ConversationRepository;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by filip on 8/12/2017.
 */

public class UsersActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> usersName = new ArrayList<>();
    private ArrayAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private String loggedUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        listView = (ListView) findViewById(R.id.listview);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_users);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home: {
                                startActivity(new Intent(UsersActivity.this, BookPopularActivity.class));
                                break;
                            }
                            case R.id.navigation_chat: {
                                startActivity(new Intent(UsersActivity.this, ConversationsActivity.class));
                                break;
                            }
                        }
                        return true;
                    }
                });
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        loggedUserId = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    User user = child.getValue(User.class);

                    usersName.add(user.getName());
                    users.add(user);
                }
                //verification for not adding in the list the logedin user
                for (int i = 0; i < usersName.size(); i++) {
                    if (users.get(i).getId().equals(loggedUserId)) {
                        users.remove(i);
                        usersName.remove(i);
                    }
                }
                adapter = new ArrayAdapter(UsersActivity.this, android.R.layout.simple_list_item_1, usersName);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                databaseReference.child("users").child(loggedUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User loggedInUser = dataSnapshot.getValue(User.class);
                        User userToChat = users.get(position);
                        Conversation conversation = new Conversation(userToChat.getId(), "", userToChat.getName(), loggedInUser.getName());
                        databaseReference.child("conversations").child(loggedUserId).child(conversation.getId()).setValue(conversation);
                        Conversation conversationForSecondUser = new Conversation(loggedInUser.getId(), "", loggedInUser.getName(), userToChat.getName());
                        databaseReference.child("conversations").child(conversation.getId()).child(loggedUserId).setValue(conversationForSecondUser);

                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("second_user", gson.toJson(conversation));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
