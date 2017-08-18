package com.example.bookshelfproject.Activity.Book.BookProfile;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.Model.Rating;
import com.example.bookshelfproject.Model.User;
import com.example.bookshelfproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by filip on 8/16/2017.
 */

public class BookProfileFragment extends Fragment {
    private static final String TAG = "BookProfileFragment";
    private TextView titleTextView;
    private TextView authorTextView;
    private RatingBar ratingBar;
    private TextView numberOfVotes;
    private CheckBox checkBox;
    private ImageView image;
    private Button addReviewButton;
    private boolean hasReview = false;
    private User user;
    private String currentUserId;
    private boolean read = false;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_details_fragmnet, container, false);

        Gson gson = new Gson();
        String strObj = getActivity().getIntent().getStringExtra("selected_book");
        final Book book = gson.fromJson(strObj, Book.class);
        String strObj1 = getActivity().getIntent().getStringExtra("user");
        user = gson.fromJson(strObj1, User.class);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        image = (ImageView) view.findViewById(R.id.imageView1);
        String downloadURI = "https://firebasestorage.googleapis.com/v0/b/bookshelfproject-54d57.appspot.com/o/books.jpg?alt=media&token=5933f37c-227d-4aef-abdb-497a0c316797";
        Picasso.with(getActivity()).load(downloadURI).fit().centerCrop().into(image);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        titleTextView.setText(book.getTitle());
        authorTextView = (TextView) view.findViewById(R.id.authorTextView);
        authorTextView.setText(book.getAuthor());
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setRating(book.getScore());
        numberOfVotes = (TextView) view.findViewById(R.id.numberOfVotesTextView);
        numberOfVotes.setText(book.getVotes() + " Votes");
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(read == false){
                    read = true;
                    databaseReference.child("user-read-books").child(currentUserId).child(book.getId()+"").setValue(book);
                    checkBox.setChecked(read);
                    return;
                }
                else {
                    read = false;
                    databaseReference.child("user-read-books").child(currentUserId).child(book.getId()+"").removeValue();
                    checkBox.setChecked(read);
                    return;
                }


            }
        });

        databaseReference.child("user-read-books").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(book.getId()+"")){
                    read = true;
                    checkBox.setChecked(read);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addReviewButton = (Button) view.findViewById(R.id.addReview);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.review_dialog, null);
                final RatingBar ratingBarDialog = (RatingBar) dialogView.findViewById(R.id.ratingBarDialog);
                final EditText editTextReviewDialog = (EditText) dialogView.findViewById(R.id.editTextDialog);
                final Button submitButtonDialog = (Button) dialogView.findViewById(R.id.buttonDialog);

                mBuilder.setView(dialogView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                databaseReference.child("book-reviews").child(book.getId() + "").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(currentUserId)) {
                            Rating rating = dataSnapshot.child(currentUserId).getValue(Rating.class);
                            ratingBarDialog.setRating(rating.getRating());
                            editTextReviewDialog.setText(rating.getReview());
                            hasReview = true;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                submitButtonDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ratingBarDialog.getRating() == 0) {
                            Toast.makeText(getActivity(), "Please select a rating", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (editTextReviewDialog.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Please enter a review", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        Rating rating = new Rating(ratingBarDialog.getRating(), editTextReviewDialog.getText().toString(),user.getName() );
                        databaseReference.child("book-reviews").child(book.getId() + "").child(currentUserId).setValue(rating);
                        float newScore = (book.getScore() + rating.getRating()) / 2;
                        book.setScore(newScore);
                        if(!hasReview){

                            int newVotes = book.getVotes() + 1;
                            book.setVotes(newVotes);
                        }

                        databaseReference.child("books").child(book.getId() + "").setValue(book);
                        databaseReference.child("category").child(book.getCategory()).child(book.getId() + "").setValue(book);
                        databaseReference.child("user-read-books").child(currentUserId).child(book.getId()+"").setValue(book);

                        ratingBar.setRating(book.getScore());
                        numberOfVotes.setText(book.getVotes()+" Votes");

                        dialog.dismiss();
                    }
                });
            }
        });


        return view;
    }
}
