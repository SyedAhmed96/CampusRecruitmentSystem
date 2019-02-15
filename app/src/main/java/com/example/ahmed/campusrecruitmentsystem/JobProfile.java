package com.example.ahmed.campusrecruitmentsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

/**
 * Created by Ahmed on 3/15/2018.
 */

public class JobProfile extends AppCompatActivity {
    FirebaseDatabase mFirebasedatabase;
    DatabaseReference mJobDatabaseReference;
    FirebaseAuth mfirebaseAuth;

    FirebaseDatabase mfirebaseDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_job_profile);
         setContentView(R.layout.activity_job_profile);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();

        //For Delete Button & Apply Button
        Button Button_delete = findViewById(R.id.Button_delete);
        String user = mfirebaseAuth.getCurrentUser().getEmail();

        Button applyButton = findViewById(R.id.Button_apply);

        //If user is admin active delete button
        if(user.equals("admin@admin.com")){
            Button_delete.setVisibility(View.VISIBLE);
            applyButton.setVisibility(View.INVISIBLE);
        }

        TextView jobTitle_textView = findViewById(R.id.text_view_job_title);
        TextView jobPostedBy_textView = findViewById(R.id.text_view_job_postedBy);
        TextView jobSkill_textView = findViewById(R.id.text_view_job_skills);
        TextView jobID_textView = findViewById(R.id.text_view_job_id);
        TextView companyID_textView = findViewById(R.id.text_view_company_id);

        final Job job = getIntent().getParcelableExtra("job");
        if (job != null) {
            jobTitle_textView.setText(""+job.getJobTitle());
            jobPostedBy_textView.setText("Posted By: "+job.getPostedBy());
            jobSkill_textView.setText("Skills Required: "+job.getSkills());
            jobID_textView.setText("Job ID: "+job.getjID());
            companyID_textView.setText("Company ID: "+job.getcID());
        }

        //Now Apply Button
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cID = ""+job.getcID();
                String jobID = ""+job.getjID();
                String currentUser = ""+mfirebaseAuth.getCurrentUser().getUid();

                mJobDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(cID).child(jobID).child("Apply").child(""+currentUser);
 //              myFBDatabase database = new myFBDatabase();
//                database.applyForJob(cID,jobID);


         //       final String pushID = mJobDatabaseReference.push().getKey();
       //         mJobDatabaseReference.child(pushID).setValue(currentUser);

                StudentApply apply = new StudentApply(""+currentUser,""+mfirebaseAuth.getCurrentUser().getEmail(),"Applied");

                //Applying (Putting User id as key and Applied as data)
                mJobDatabaseReference.setValue(apply);

                Log.d(TAG, "JOB Profile cID: "+cID + "jobID: "+jobID);
                Log.d(TAG, "currentUser: "+currentUser);
                Toast.makeText(getApplicationContext(),"Applied..",Toast.LENGTH_SHORT).show();
            }
        });

        //For Delete Button
        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling Delete Method //Must update db reference to job id then set text null
                String cID = ""+job.getcID();
                String jobID = ""+job.getjID();

                mJobDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(cID).child(jobID);
                mJobDatabaseReference.setValue(null);

                finish();

                Toast.makeText(JobProfile.this,"Job Deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }
 }

