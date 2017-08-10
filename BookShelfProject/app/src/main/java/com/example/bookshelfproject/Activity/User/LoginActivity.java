package com.example.bookshelfproject.Activity.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button siginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        siginButton = (Button) findViewById(R.id.buttonSignin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewRegister = (TextView) findViewById(R.id.texViewRegister);
        progressDialog = new ProgressDialog(this);

        siginButton.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == siginButton){
            userLogin();
        }if (v == textViewRegister){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, BookPopularActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Not registered. Please register first.", Toast.LENGTH_LONG).show();
                }
            }
        });
        progressDialog.hide();
    }
}

