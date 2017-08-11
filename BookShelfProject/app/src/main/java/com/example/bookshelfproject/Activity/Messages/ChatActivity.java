package com.example.bookshelfproject.Activity.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Model.Conversation;
import com.example.bookshelfproject.Model.Message;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by filip on 8/10/2017.
 */

public class ChatActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private TextView textViewMessage;
    private String userId;
    private String secondUserId;
    private Conversation secondUserConversation;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        editTextMessage = (EditText) findViewById(R.id.enterChat);
        buttonSendMessage = (Button) findViewById(R.id.sendButton) ;
        textViewMessage = (TextView) findViewById(R.id.message);


       // Bundle bundle = getIntent().getBundleExtra("second_user");
        //secondUserConversation = bundle.getString("second_user");

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("second_user");
        final Conversation conversation = gson.fromJson(strObj, Conversation.class);
        secondUserId = conversation.getId();

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextMessage.getText().toString().equals("")){
                    return;
                }

                String messageId = databaseReference.push().getKey();
                Message message = new Message(messageId,editTextMessage.getText().toString(), userId, System.currentTimeMillis());

                databaseReference.child("messeges").child(userId).child(secondUserId).child(messageId).setValue(message);
                databaseReference.child("messeges").child(secondUserId).child(userId).child(messageId).setValue(message);

                editTextMessage.setText("");
            }

        });


        databaseReference.child("messeges").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot,conversation, userId, secondUserId);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot, conversation, userId, secondUserId);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_chat);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                startActivity(new Intent(ChatActivity.this, BookPopularActivity.class));
                                finish();
                            case R.id.navigation_chat:
                                startActivity(new Intent(ChatActivity.this, MessagesActivity.class));
                                finish();
                        }
                        return true;
                    }
                });


    }

    private void append_chat_conversation(DataSnapshot dataSnapshot, Conversation conversation, String userId, String secondUserId) {
        textViewMessage.setText("");
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for(DataSnapshot child : children){
            Message message = child.getValue(Message.class);
            if(message.getUserAuthor().equals(userId)){
                textViewMessage.append(conversation.getMyName() + ": "+ message.getText()+"\n");
            }else if(message.getUserAuthor().equals(secondUserId)){
                textViewMessage.append(conversation.getName() + ": "+ message.getText()+"\n");
            }
        }

    }


}
