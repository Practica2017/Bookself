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
import android.widget.Button;
import android.widget.TextView;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Activity.Book.CategoriesActivity;
import com.example.bookshelfproject.Activity.Messages.ConversationsActivity;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by filip on 8/18/2017.
 */

public class MyProfileActivity  extends AppCompatActivity{
    private TextView textViewUsername;
    private TextView textViewEmail;
    private Button editProfileButton;
    private Button readBooksButton;
    private Button logoutButton;
    private BottomNavigationView bottomNavigationView;
    private String currentUserId;
    private User user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        setCurrentUser();




        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUsername.setText("test");
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewEmail.setText("test");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home: {
                                startActivity(new Intent(MyProfileActivity.this, BookPopularActivity.class));
                                break;
                            }
                            case R.id.navigation_chat: {
                                startActivity(new Intent(MyProfileActivity.this, ConversationsActivity.class));
                                break;
                            }
                            case R.id.navigation_users:
                                startActivity(new Intent(MyProfileActivity.this, UsersActivity.class));
                                finish();
                                break;
                            case R.id.navigation_categories:
                                startActivity(new Intent(MyProfileActivity.this, CategoriesActivity.class));
                                break;
                        }
                        return true;
                    }
                });


        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        readBooksButton = (Button) findViewById(R.id.viewReadBooksButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(MyProfileActivity.this, LoginActivity.class));
                finish();
            }
        });


    }
    public void setCurrentUser(){
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
