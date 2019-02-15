package com.example.ahmed.campusrecruitmentsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

/**
 * Created by Ahmed on 3/16/2018.
 */

public class StudentProfile extends AppCompatActivity {
    FirebaseDatabase mFirebasedatabase;
    DatabaseReference mStudentDatabaseReference;
    FirebaseAuth mfirebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Student Profile");

        setContentView(R.layout.activity_student_profile);


        mfirebaseAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();


        //For Delete Button
        Button Button_delete = findViewById(R.id.Button_delete);
        String admin = mfirebaseAuth.getCurrentUser().getEmail();

        //IF user is admin active delete button
        if(admin.equals("admin@admin.com")){
            Button_delete.setVisibility(View.VISIBLE);
        }

        //Initializing Views
        ImageView student_ImageView = findViewById(R.id.student_image_view);
        TextView studentName_textView = findViewById(R.id.studentName_text_view);
        TextView studentEmail_textView = findViewById(R.id.studentEmail_text_view);
        TextView studentGPA_textView = findViewById(R.id.studentGPA_text_view);
        TextView studentID_textView = findViewById(R.id.studentID_text_view);

        final Student student = getIntent().getParcelableExtra("student");
        if (student != null) {
            //if Photo is updated //Glide and download/show
                if(student.getPhotoURL()!=null){
                    //Updating download URL(null)
                    String photoUrl = student.getPhotoURL();

                    //Photo URL for Image..
                    Glide.with(student_ImageView.getContext())
                            .load(photoUrl)
                            .into(student_ImageView);
                }

            studentName_textView.setText(""+student.getName());
            studentEmail_textView.setText("Email Address : "+student.getEmail());
            studentGPA_textView.setText("CGPA : "+student.getGPA());
            studentID_textView.setText("Student ID : "+student.getsID());
        }

        //For Delete Button
        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling Delete Method //Must update db reference to company id then set text null
                String sID = ""+student.getsID();

                mStudentDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students").child(sID);
                mStudentDatabaseReference.setValue(null);

                finish();

                Toast.makeText(StudentProfile.this,"Student Deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
