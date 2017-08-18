package com.example.bookshelfproject.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookshelfproject.Activity.Messages.ChatActivity;
import com.example.bookshelfproject.Model.Conversation;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

/**
 * Created by Iulia on 8/17/2017.
 */

public class User_Profile extends AppCompatActivity {
    private TextView textViewUsername;
    private TextView textViewEmail;
    private ImageView imageViewPictureProfile;
    private Button sendMessageButton;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button readBooksButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        readBooksButton = (Button) findViewById(R.id.viewReadBooksButton);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("userToChat");
        String strObj1 = getIntent().getStringExtra("user");
        final User userToChat = gson.fromJson(strObj, User.class);
        final User loggedInUser = gson.fromJson(strObj1, User.class);

        textViewUsername.setText(userToChat.getName());
        textViewEmail.setText(userToChat.getEmail());

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conversation conversation = new Conversation(userToChat.getId(), "", userToChat.getName(), loggedInUser.getName());
                databaseReference.child("conversations").child(loggedInUser.getId()).child(conversation.getId()).setValue(conversation);
                Conversation conversationForSecondUser = new Conversation(loggedInUser.getId(), "", loggedInUser.getName(), userToChat.getName());
                databaseReference.child("conversations").child(conversation.getId()).child(loggedInUser.getId()).setValue(conversationForSecondUser);

                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                Gson gson = new Gson();
                intent.putExtra("second_user", gson.toJson(conversation));
                startActivity(intent);
            }
        });


        readBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Profile.this,ReadBooksActivity.class);
                Gson gson = new Gson();
                intent.putExtra("userToChat", gson.toJson(userToChat));
                startActivity(intent);
            }
        });
    }
}
