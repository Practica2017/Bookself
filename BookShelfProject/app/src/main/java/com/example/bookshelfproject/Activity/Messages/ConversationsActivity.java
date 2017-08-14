package com.example.bookshelfproject.Activity.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Activity.User.UsersActivity;
import com.example.bookshelfproject.Model.Conversation;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by filip on 8/10/2017.
 */

public class ConversationsActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ListView listViewUsers;
    private ArrayAdapter adapter;
    private ArrayList<String> usersName = new ArrayList<>();
    private ArrayList<Conversation> conversations = new ArrayList<>();


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_chat);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_users:
                                startActivity(new Intent(ConversationsActivity.this, UsersActivity.class));
                                break;

                            case R.id.navigation_home:
                                startActivity(new Intent(ConversationsActivity.this, BookPopularActivity.class));
                                break;


                        }
                        return true;
                    }
                });


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        listViewUsers = (ListView) findViewById(R.id.listview);
        final String uid = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("conversations").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot child : children) {
                    Conversation conversation = child.getValue(Conversation.class);
                    conversations.add(conversation);
                    usersName.add(conversation.getName());
                    i++;
                }
                adapter = new ArrayAdapter(ConversationsActivity.this, android.R.layout.simple_list_item_1, usersName);
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                Gson gson = new Gson();
                intent.putExtra("second_user", gson.toJson(conversations.get(position)));
                startActivity(intent);
            }
        });
    }
}