package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ahmed on 3/15/2018.
 */

public class CompanyProfile extends AppCompatActivity {
    FirebaseAuth mfirebaseauth;
    FirebaseDatabase mFirebasedatabase;

    DatabaseReference mCompanyDatabaseReference;
    DatabaseReference mJobDatabaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

//        //For Getting String Value From Intent
//        Bundle extras = getIntent().getExtras();
//        if (extras == null) {
//            return;
//        }
//        // get data via the key //extras.getString(Intent.EXTRA_TEXT);
//        String value1 = extras.getString("companyName");
//        if (value1 != null) {
//            // do something with the data
//            companyName = value1;
//        }


        mFirebasedatabase = FirebaseDatabase.getInstance();
        mfirebaseauth = FirebaseAuth.getInstance();
        String admin = ""+mfirebaseauth.getCurrentUser().getEmail();

        //For delete Button
        Button Button_delete = findViewById(R.id.Button_delete);

        //IF user is admin active delete button
        if(admin.equals("admin@admin.com")){
            Button_delete.setVisibility(View.VISIBLE);
        }

        TextView companyName_textView = findViewById(R.id.text_view_company_name);
        TextView companyEmial_textView = findViewById(R.id.text_view_company_email);
        TextView companyAddress_textView = findViewById(R.id.text_view_company_address);
        TextView companyID_textView = findViewById(R.id.text_view_company_id);


        final Company company = getIntent().getParcelableExtra("company");
        if (company != null) {
            companyName_textView.setText(""+company.getName());
            companyEmial_textView.setText("Email Address: "+company.getEmail());
            companyAddress_textView.setText("Office Address: "+company.getAddress());
            companyID_textView.setText("Company ID: "+company.getcID());
        }

        //For Delete Button
        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calling Delete Method //Must update db reference to company id then set text null
                String cID = ""+company.getcID();

                mCompanyDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Companies").child(cID);
                mCompanyDatabaseReference.setValue(null);

                mJobDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(cID);
                mJobDatabaseReference.setValue(null);

                finish();

                Toast.makeText(CompanyProfile.this,"Company Deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
