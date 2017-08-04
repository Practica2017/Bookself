    package com.example.bookshelfproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureBestBooks();
    }

    private void configureBestBooks() {
        Button bestBooksButton = (Button) findViewById(R.id.bestBooks);
       // bestBooksButton.setOnClickListener();
    }


}
