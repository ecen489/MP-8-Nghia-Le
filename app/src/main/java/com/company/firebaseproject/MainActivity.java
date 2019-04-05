package com.company.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {
    Button createBtn;
    Button loginBtn;
    EditText emailText;
    EditText passwordText;

    FirebaseDatabase firedb;
    DatabaseReference dbref;

    FirebaseAuth mAuth;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firedb = FirebaseDatabase.getInstance();
        dbref = firedb.getReference();

        createBtn = findViewById(R.id.create);
        loginBtn = findViewById(R.id.login);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passText);

        mAuth = FirebaseAuth.getInstance();
    }

    public void CreateClick(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"An account has been created.",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The newly created user is already signed in
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Unable to create an account, please try again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void LoginClick(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser(); //The user is signed in
                            Intent intent = new Intent(getBaseContext(), PullActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
