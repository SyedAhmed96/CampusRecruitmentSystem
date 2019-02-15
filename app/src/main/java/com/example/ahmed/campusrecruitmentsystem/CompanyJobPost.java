package com.example.ahmed.campusrecruitmentsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ahmed on 3/19/2018.
 */

public class CompanyJobPost extends AppCompatActivity {
    private static final String TAG = "check";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create a Job");
        setContentView(R.layout.activity_job_post);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        final EditText editTextJobTitle = findViewById(R.id.edittext_jobTitle);
        final EditText editTextPostedBy = findViewById(R.id.edittext_postedBy);
        final EditText editTextSkills = findViewById(R.id.edittext_skills);

        Button publishButton = findViewById(R.id.publish_button);

        //When Button is clicked post the job and close activity
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cID = mFirebaseAuth.getCurrentUser().getUid();
                final String jobTitle = editTextJobTitle.getText().toString();
                final String postedBy = editTextPostedBy.getText().toString();
                final String skills = editTextSkills.getText().toString();

                mDatabaseReference = mFirebaseDatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(""+cID);

                final String jobID = mDatabaseReference.push().getKey();

                Toast.makeText(CompanyJobPost.this,"Job Posted",Toast.LENGTH_SHORT).show();

                Job job = new Job(jobTitle,postedBy,skills,jobID,cID);
                mDatabaseReference.child(""+jobID).setValue(job);

                finish();
            }
        });

    }
}
