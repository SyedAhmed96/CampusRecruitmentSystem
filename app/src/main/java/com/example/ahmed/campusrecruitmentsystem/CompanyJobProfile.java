package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ahmed on 3/16/2018.
 */

public class CompanyJobProfile extends AppCompatActivity {
    private static final String TAG ="CheckStudentsKey" ;
    FirebaseDatabase mFirebasedatabase;
    DatabaseReference mJobDatabaseReference;
    FirebaseAuth mfirebaseAuth;

    DatabaseReference mStudentsAppliedDatabaseReference;
    ChildEventListener mStudentsAppliedChildEventListener;

    ArrayList<StudentApply> appliedStudentsKey;

    LinearLayout mainLayout;
    TextView textview_numberOfStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job_profile);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();

        appliedStudentsKey = new ArrayList<>();

        TextView jobTitle_textView = findViewById(R.id.text_view_job_title);
        TextView jobPostedBy_textView = findViewById(R.id.text_view_job_postedBy);
        TextView jobSkill_textView = findViewById(R.id.text_view_job_skills);
        TextView jobID_textView = findViewById(R.id.text_view_job_id);
        TextView companyID_textView = findViewById(R.id.text_view_company_id);

        final Job job = getIntent().getParcelableExtra("job");
        if (job != null) {
            //Now passing references and receiving list..
            String cID,jobID;
            cID = job.getcID();
            jobID = job.getjID();

            jobTitle_textView.setText(""+job.getJobTitle());
            jobPostedBy_textView.setText("Posted By: "+job.getPostedBy());
            jobSkill_textView.setText("Skills Required: "+job.getSkills());
            jobID_textView.setText("Job ID: "+job.getjID());

            //Here getting data from firebase.
            getAppliedStudentList(""+cID,""+jobID);
        }

        //Using thread to block and give time to firebase to get data
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                //Toast and check your list here..
                showApplicants();
            }
        }, 2000);

    }
        //Getting data and putting in dynamic text views..
        void showApplicants(){
            for(int i=0; i<appliedStudentsKey.size(); i++){
                DynamicTextView(""+appliedStudentsKey.get(i).getEmail(),i);
            }
        }

        //Adding dynamic textViews for Number of applied students
        void DynamicTextView(String key,int i){
            mainLayout = findViewById(R.id.LinearLayout);
            TextView textView = new TextView(CompanyJobProfile.this);
            textView.setText("Student "+(i+1) + ": "+key);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f);

            //For total number of applicants
            textview_numberOfStudent = findViewById(R.id.text_view_numberOfStudent);
            textview_numberOfStudent.setText(""+appliedStudentsKey.size());

            mainLayout.addView(textView);
        }


    //Not Working has to check later
     //Here we have to get the applied students list
     void getAppliedStudentList(String cID, String jobID){
         //Update fb reference.. //Has to read apply node and comes applied(for each logic)
         mStudentsAppliedDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(cID).child(jobID).child("Apply");
             //attaching db read listener..
             mStudentsAppliedChildEventListener = new ChildEventListener() {
                 @Override
                 public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                     for(DataSnapshot d : dataSnapshot.getChildren()){
//                         appliedStudentsKey.add(""+ d.getKey());
//                     }

                     StudentApply apply = dataSnapshot.getValue(StudentApply.class);
                     appliedStudentsKey.add(apply);

                     Log.d(TAG, "onChildAdded: dataSnapshot.getChildren: " + dataSnapshot.getChildren().toString());
                     Log.d(TAG, "onChildAdded: KEY" + dataSnapshot.getKey() + " value " + dataSnapshot.getValue().toString());
                     Log.d(TAG, "appliedStudentsKey : "+appliedStudentsKey);

                 }

                 @Override
                 public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                 }

                 @Override
                 public void onChildRemoved(DataSnapshot dataSnapshot) {

                 }

                 @Override
                 public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }

             };
             mStudentsAppliedDatabaseReference.addChildEventListener(mStudentsAppliedChildEventListener);
     }


}
