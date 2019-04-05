package com.company.firebaseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class PushActivity extends Activity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase fbdb;
    private DatabaseReference dbrf;

    private int radioID = R.id.rad_ralph;
    private int dbID = 404;

    private EditText editCID;
    private EditText editCN;
    private EditText editG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser(); //The user is signed in

        if(user == null) {
            Intent unauthorizedIntent = new Intent(this, MainActivity.class);
            startActivity(unauthorizedIntent);
        }

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        editCID = findViewById(R.id.editCourseID);
        editCN = findViewById(R.id.editCourseName);
        editG = findViewById(R.id.editGrade);
    }

    public void PushClick(View view) {
        int courseID = Integer.parseInt(editCID.getText().toString());
        String courseName = editCN.getText().toString();
        String courseG = editG.getText().toString();

        // Check for correctly formatted grade
        boolean regex = Pattern.matches("[A-D][+-]?|F[+-]?", courseG);
        if(regex) {
            editCID.getText().clear();
            editCN.getText().clear();
            editG.getText().clear();

            Grade grade = new Grade(courseID, courseName, courseG, dbID);
            DatabaseReference insLoc = dbrf.child("simpsons/grades/");
            DatabaseReference ranKey = insLoc.push();
            ranKey.setValue(grade);
            Intent goBackToPushIntent = new Intent(this, PullActivity.class);
            startActivity(goBackToPushIntent);
        } else {
            editG.getText().clear();
            Toast.makeText(this,"Bad grade, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.rad_bart){dbID = 123;}
        if(radioID==R.id.rad_ralph){dbID = 404;}
        if(radioID==R.id.rad_milhouse){dbID = 456;}
        if(radioID==R.id.rad_lisa){dbID = 888;}
    }
}
