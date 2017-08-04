    package com.example.bookshelfproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;

    public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureBestBooks();
    }

    private void configureBestBooks() {
        Button bestBooksButton = (Button) findViewById(R.id.bestBooks);
        bestBooksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookPopularActivity.class));
                //finish();
            }
        });
    }


}
