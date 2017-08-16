package com.example.bookshelfproject.Activity.Book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.bookshelfproject.Activity.Book.BookProfile.BookProfileActivity;
import com.example.bookshelfproject.Activity.Messages.ConversationsActivity;
import com.example.bookshelfproject.Activity.User.LoginActivity;
import com.example.bookshelfproject.Activity.User.UsersActivity;
import com.example.bookshelfproject.Model.Book;

import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 8/3/2017.
 */

public class BookPopularActivity extends Activity  {
    private ListView listView;
    private ArrayList<String> bookTitles = new ArrayList<>();
    private static List<Book> bestBooks = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_books);


        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() == null ){
            startActivity(new Intent(BookPopularActivity.this, LoginActivity.class));
        }
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_users:
                                startActivity(new Intent(BookPopularActivity.this, UsersActivity.class));
                                break;

                            case R.id.navigation_chat:
                                startActivity(new Intent(BookPopularActivity.this, ConversationsActivity.class));
                                break;

                            case R.id.navigation_logout:
                                firebaseAuth.signOut();
                                startActivity(new Intent(BookPopularActivity.this, LoginActivity.class));
                                finish();
                                break;
                            case R.id.navigation_categories:
                                startActivity(new Intent(BookPopularActivity.this, CategoriesActivity.class));
                                break;
                        }
                        return true;
                    }
                });


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
       //addBookstoDatabase();

        listView = (ListView)findViewById(R.id.listview);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BookProfileActivity.class);
                Gson gson = new Gson();
                intent.putExtra("selected_book", gson.toJson(bestBooks.get(position)));
                Log.d("mytag","---------------------------"+bestBooks.get(position).getTitle());
                //startActivity(intent);
            }
        });

        addBestBooks(databaseReference);
    }
    private void addBestBooks(DatabaseReference databaseReference) {
        bestBooks.clear();
        bookTitles.clear();
        databaseReference.child("books").orderByChild("score").startAt("4").endAt("5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int i = 0;
                for (DataSnapshot child : children) {
                    Book book = child.getValue(Book.class);
                    bestBooks.add(i, book);
                    i++;
                }
                HashMap<String, String> listItems = new HashMap<>();
                for (i = 0; i < bestBooks.size(); i++) {
                    Book book = bestBooks.get(i);
                    listItems.put(book.getTitle()+"\n  by " + book.getAuthor(),"Avg Rating: " + book.getScore()+" - " + book.getVotes() +" ratings");
                }
                List<HashMap<String, String>> listListItems = new ArrayList<HashMap<String, String>>();
                SimpleAdapter simpleAdapter = new SimpleAdapter(BookPopularActivity.this, listListItems,R.layout.list_item,
                        new String[]{"First Line", "Second Line"},
                        new int[]{R.id.textItem1, R.id.textItem2});

                Iterator iterator = listItems.entrySet().iterator();
                while(iterator.hasNext()){
                    HashMap<String,String> resultsMap = new HashMap<String, String>();
                    Map.Entry pair =(Map.Entry) iterator.next();
                    resultsMap.put("First Line", pair.getKey().toString());
                    resultsMap.put("Second Line", pair.getValue().toString());
                    listListItems.add(resultsMap);

                }
                listView.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addBookstoDatabase() {
        Book bookTest = new Book(1,"Moara cu Noroc","Ioan Slavici","3",10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest.getId()+"").setValue(bookTest);
        databaseReference.child("category").child(bookTest.getCategory()).child(bookTest.getId()+"").setValue(bookTest);

        Book bookTest1 = new Book(2,"Adolescentul","Mircea Eliade","4",10,"Best book ever","Romance");
        databaseReference.child("books").child(bookTest1.getId()+"").setValue(bookTest1);
        databaseReference.child("category").child(bookTest1.getCategory()).child(bookTest1.getId()+"").setValue(bookTest1);

        Book bookTest2 = new Book(3,"Fram ursul polar","Ioan Slavici","5",10,"Best book ever","Fantasy");
        databaseReference.child("books").child(bookTest2.getId()+"").setValue(bookTest2);
        databaseReference.child("category").child(bookTest2.getCategory()).child(bookTest2.getId()+"").setValue(bookTest2);

        Book bookTest3 = new Book(4,"Amintiri din copilarie","Ioan Slavici","2",10,"Best book ever","Action");
        databaseReference.child("books").child(bookTest3.getId()+"").setValue(bookTest3);
        databaseReference.child("category").child(bookTest3.getCategory()).child(bookTest3.getId()+"").setValue(bookTest3);

        Book bookTest4 = new Book(5,"Ion","Ioan Slavici","5",10,"Best book ever","Crime");
        databaseReference.child("books").child(bookTest4.getId()+"").setValue(bookTest4);
        databaseReference.child("category").child(bookTest4.getCategory()).child(bookTest4.getId()+"").setValue(bookTest4);

        Book bookTest5 = new Book(6,"Morometii","Marin Preda","5",3,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest5.getId()+"").setValue(bookTest5);
        databaseReference.child("category").child(bookTest5.getCategory()).child(bookTest5.getId()+"").setValue(bookTest5);

        Book bookTest6 = new Book(7,"Vol Poezii 2","Mihai Eminescu","5",3,"Best book ever","Poetry");
        databaseReference.child("books").child(bookTest6.getId()+"").setValue(bookTest6);
        databaseReference.child("category").child(bookTest6.getCategory()).child(bookTest6.getId()+"").setValue(bookTest6);

        Book bookTest7 = new Book(8,"Start with why","Ioan Slavici","3",10,"Best book ever","Drama");
        databaseReference.child("books").child(bookTest7.getId()+"").setValue(bookTest7);
        databaseReference.child("category").child(bookTest7.getCategory()).child(bookTest7.getId()+"").setValue(bookTest7);

    }
}
