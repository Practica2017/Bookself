package com.example.bookshelfproject.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by filip on 8/12/2017.
 */

public class ConversationRepository {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public ConversationRepository(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void addConversation(final User userToChat, final String loggedInUserId){
        databaseReference.child("users").child(loggedInUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User loggedInUser = dataSnapshot.getValue(User.class);
                Conversation conversation = new Conversation(userToChat.getId(),"",userToChat.getName(),loggedInUser.getName());
                databaseReference.child("conversations").child(loggedInUser.getId()).child(conversation.getId()).setValue(conversation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
