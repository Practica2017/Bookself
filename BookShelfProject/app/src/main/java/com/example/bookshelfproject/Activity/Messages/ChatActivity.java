package com.example.bookshelfproject.Activity.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Activity.User.LoginActivity;
import com.example.bookshelfproject.R;

/**
 * Created by filip on 8/10/2017.
 */

public class ChatActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                startActivity(new Intent(ChatActivity.this, BookPopularActivity.class));
                            case R.id.navigation_chat:
                                //

                        }
                        return true;
                    }
                });
    }
}
