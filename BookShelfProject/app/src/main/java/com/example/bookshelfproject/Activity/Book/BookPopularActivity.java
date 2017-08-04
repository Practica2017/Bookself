package com.example.bookshelfproject.Activity.Book;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookshelfproject.R;

import java.util.ArrayList;

/**
 * Created by filip on 8/3/2017.
 */

public class BookPopularActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> bestBooks = new ArrayList<>();
    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_books);

        listView = (ListView) findViewById(R.id.listview);

        for (int i=0;i<10;i++){
            bestBooks.add(i,"book nr "+i);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,bestBooks);
        listView.setAdapter(adapter);


    }
}
