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

import com.example.bookshelfproject.Activity.Messages.ChatActivity;
import com.example.bookshelfproject.Activity.Messages.ChatAdapter;
import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.Model.Conversation;
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

import java.util.ArrayList;

/**
 * Created by filip on 8/16/2017.
 */

public class BookDiscussionFragment extends Fragment {
    private static final String TAG = "BookDiscussionFragment";
    private EditText editTextMessage;
    private Button sendMessageButton;
    private ListView messagesContainer;
    private String currentUserId;
    private User user;
    private ChatAdapter adapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_discussion_fragment,container,false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        //setCurrentUser(databaseReference);
        firebaseAuth = FirebaseAuth.getInstance();

        currentUserId = firebaseAuth.getCurrentUser().getUid();


        Gson gson = new Gson();
        String strObj = getActivity().getIntent().getStringExtra("selected_book");
        final Book book = gson.fromJson(strObj, Book.class);
        String strObj1 = getActivity().getIntent().getStringExtra("user");
        user = gson.fromJson(strObj1, User.class);

        editTextMessage = (EditText) view.findViewById(R.id.enterChat);
        sendMessageButton = (Button) view.findViewById(R.id.sendButton);
        messagesContainer = (ListView) view.findViewById(R.id.messagesContainer);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextMessage.getText().toString().equals("")) {
                    return;
                }
                String messageId = databaseReference.push().getKey();
                Message message = new Message(messageId, editTextMessage.getText().toString(), user.getId(), System.currentTimeMillis(), user.getName());
                databaseReference.child("book-discussion").child(book.getId() + "").child(messageId).setValue(message);

                editTextMessage.setText("");
            }
        });

        databaseReference.child("book-discussion").child(book.getId() + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                append_chat_conversation(dataSnapshot, user.getId());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    private void append_chat_conversation(DataSnapshot dataSnapshot, String userId) {

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        ArrayList<Message> messages = new ArrayList<>();
        for (DataSnapshot child : children) {
            Message message = child.getValue(Message.class);
            messages.add(message);
        }
        adapter = new ChatAdapter(getActivity(), new ArrayList<Message>());
        messagesContainer.setAdapter(adapter);
        adapter.setMyId(userId);
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            displayMessage(message);
        }
    }

    public void displayMessage(Message message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
}
