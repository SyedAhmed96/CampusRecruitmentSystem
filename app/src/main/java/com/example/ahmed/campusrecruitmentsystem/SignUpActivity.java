package com.example.ahmed.campusrecruitmentsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Ahmed on 3/8/2018.
 */

public class SignUpActivity extends AppCompatActivity implements OnCheckedChangeListener {

    private static final String TAG = "CheckUser";
    RadioGroup radioGroup;
    EditText emailStudentEditText,passwordStudentEditText,studentNameEditText;
    EditText emailCompanyEditText,passwordCompanyEditText,companyNameEditText,companyAddressEditText;
    Button studentSignUpButton,companySignUpButton;

    ProgressDialog progressDialog;

    LinearLayout companyLinearLayout,studentLinearLayout;

    //defining firebaseauth reference
    private FirebaseAuth firebaseAuth;

    //Firebase storage references
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //Initialize Firebase database components
        mFirebasedatabase = FirebaseDatabase.getInstance();
      //  mDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem");

        //My database Reff.
        myFBDatabase database = new myFBDatabase();
        database.attachCompanyDatabaseReadListener();
        database.attachStudentDatabaseReadListener();

//        //Commented For Testing Purpose.
//        //Here have to Check What Current User is And Display Screen Accordingly.
//        //if getCurrentUser does not returns null
            if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();
            //and open profile activity //Openning Login Activity
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //Initializing Views //Initializing Student Views..
        emailStudentEditText = findViewById(R.id.emailStudentEditText);
        passwordStudentEditText = findViewById(R.id.passwordStudentEditText);
        studentNameEditText = findViewById(R.id.studentNameEditText);

        studentSignUpButton = findViewById(R.id.studentSignUp);
        studentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerStudent();
            }
        });

        progressDialog = new ProgressDialog(this);

        //Initializing Views //Initializing Company Views..
        emailCompanyEditText = findViewById(R.id.emailCompanyEditText);
        passwordCompanyEditText = findViewById(R.id.passwordCompanyEditText);
        companyNameEditText = findViewById(R.id.companyNameEditText);
        companyAddressEditText = findViewById(R.id.companyAddressEditText);

        companySignUpButton = findViewById(R.id.companySignUp);
        companySignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCompany();
            }
        });

        //Initializing Layouts
        studentLinearLayout = findViewById(R.id.studentLinearLayout);
        companyLinearLayout = findViewById(R.id.companyLinearLayout);

        //DeActivating Views(Layouts)
        deActivateViews();

        radioGroup = findViewById(R.id.rg1);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void registerStudent() {
        //getting name,emailEditText and passwordEditText from edit texts
        final String email = emailStudentEditText.getText().toString().trim();
        final String password  = passwordStudentEditText.getText().toString().trim();
        final String name  = studentNameEditText.getText().toString().trim();

        //checking if emailEditText and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the emailEditText and passwordEditText are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            //If Success, Register Student in Firebase Database.
                                //Updating DB Reference..
                                String studentID = ""+firebaseAuth.getCurrentUser().getUid();

                                mDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students").child(""+studentID);

                                Student student = new Student(email,name,password,studentID);

                                mDatabaseReference.setValue(student);

                                finish();

                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                        }else{
                            //display some message here
                            Toast.makeText(SignUpActivity.this,"Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    private void registerCompany() {
        //getting name,emailEditText and passwordEditText from edit texts
        final String email = emailCompanyEditText.getText().toString().trim();
        final String password  = passwordCompanyEditText.getText().toString().trim();
        final String name  = companyNameEditText.getText().toString().trim();
        final String address  = companyAddressEditText.getText().toString().trim();

        //checking if emailEditText and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter emailEditText", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter passwordEditText", Toast.LENGTH_LONG).show();
            return;
        }

        //if the emailEditText and passwordEditText are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();

                            //If Success, Register Company in Firebase Database.
                            //Updating DB Reference..
                            String companyID = ""+firebaseAuth.getCurrentUser().getUid();
                            mDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Companies").child(""+companyID);

                            Company company = new Company(name,password,email,address,companyID);

                            mDatabaseReference.setValue(company);

                            finish();

                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                        }else{
                            //display some message here
                            Toast.makeText(SignUpActivity.this,"Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            case R.id.rbStudent:
                deActivateViews();
                activateStudentViews();
                break;
            case R.id.rbCompany:
                deActivateViews();
                activateCompanyViews();
                break;
            case R.id.rbAdmin:
                  finish();
//                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);  //For Testing Purposes
//                i.putExtra("check","admin");
                  startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
    }

    private void deActivateViews(){
        studentLinearLayout.setVisibility(View.INVISIBLE);
        companyLinearLayout.setVisibility(View.INVISIBLE);
    }

    void activateStudentViews(){
        studentLinearLayout.setVisibility(View.VISIBLE);
    }


    void activateCompanyViews(){
        companyLinearLayout.setVisibility(View.VISIBLE);
    }


//    private void deActivateViews() {
//        emailEditText.setVisibility(View.INVISIBLE);
//        passwordEditText.setVisibility(View.INVISIBLE);
//        studentEditText.setVisibility(View.INVISIBLE);
//        companyEditText.setVisibility(View.INVISIBLE);
//        SignUpButton.setVisibility(View.INVISIBLE);
//    }
//
//    void activateStudentViews(){
//        emailEditText.setVisibility(View.VISIBLE);
//        passwordEditText.setVisibility(View.VISIBLE);
//        studentEditText.setVisibility(View.VISIBLE);
//        SignUpButton.setVisibility(View.VISIBLE);
//    }
//
//    void activateCompanyViews(){
//        emailEditText.setVisibility(View.VISIBLE);
//        passwordEditText.setVisibility(View.VISIBLE);
//        companyEditText.setVisibility(View.VISIBLE);
//        SignUpButton.setVisibility(View.VISIBLE);
//    }

}