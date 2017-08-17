package com.example.bookshelfproject.Activity.Book.BookProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.Model.Message;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

/**
 * Created by filip on 8/16/2017.
 */

public class BookDiscussionFragment extends Fragment {
    private static final String TAG = "BookDiscussionFragment";
    private EditText editTextMessage;
    private Button sendMessageButton;
    private ListView listView;
    private String currentUserId;
    private User user;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_discussion_fragment,container,false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        currentUserId = firebaseAuth.getCurrentUser().getUid();
        setCurrentUser(databaseReference);

        Gson gson = new Gson();
        String strObj = getActivity().getIntent().getStringExtra("selected_book");
        final Book book = gson.fromJson(strObj, Book.class);

        editTextMessage = (EditText) view.findViewById(R.id.enterChat);
        sendMessageButton = (Button) view.findViewById(R.id.sendButton);
        listView = (ListView) view.findViewById(R.id.messagesContainer);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextMessage.getText().toString().equals("")) {
                    return;
                }
                String messageId = databaseReference.push().getKey();
                Message message = new Message(messageId, editTextMessage.getText().toString(), user.getId(), System.currentTimeMillis());
                databaseReference.child("book-discussion").child(book.getId() + "").child(messageId).setValue(message);
            }
        });

        return view;
    }

    public void setCurrentUser(DatabaseReference databaseReference){
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
