package com.company.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PullActivity extends Activity {
    private FirebaseAuth mAuth;
    private String email;
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Grade> simpsonsList;
    private EditText IDText;

    DatabaseReference dbSimpsons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser(); //The user is signed in
        email = user.getEmail();

        IDText=findViewById(R.id.userID);

        if(user == null) {
            Intent unauthorizedIntent = new Intent(this, MainActivity.class);
            startActivity(unauthorizedIntent);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpsonsList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(this, simpsonsList);
        recyclerView.setAdapter(adapter);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            simpsonsList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    simpsonsList.add(grade);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void QueryOneClick(View view) {
        int studID = Integer.parseInt(IDText.getText().toString());
        Query query = FirebaseDatabase.getInstance().getReference("simpsons/grades/")
                .orderByChild("student_id")
                .equalTo(studID);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void SignOutClick(View view) {
        mAuth.signOut();
        user = null;

        Intent signOutIntent = new Intent(this, MainActivity.class);
        startActivity(signOutIntent);
    }

    public void PushClick(View view) {
        Intent pushIntent = new Intent(this, PushActivity.class);
        startActivity(pushIntent);
    }

    public void QueryTwoClick(View view) {
        int studID = Integer.parseInt(IDText.getText().toString());
        Query query = FirebaseDatabase.getInstance().getReference("simpsons/grades/")
                .orderByChild("student_id")
                .startAt(studID);

        query.addListenerForSingleValueEvent(valueEventListener);
    }
}
