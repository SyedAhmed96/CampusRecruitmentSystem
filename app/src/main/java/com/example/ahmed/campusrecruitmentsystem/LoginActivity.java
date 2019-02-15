package com.example.ahmed.campusrecruitmentsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ahmed on 3/10/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "check";
    TextView textViewSignup;
    Button loginButton;
    EditText emailEditText, passwordEditText;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    private ChildEventListener mCompanyChildEventListener,mStudentChildEventListener;

    private DatabaseReference mCompaniesDatabaseReference,mStudentsDatabaseReference;

    private FirebaseDatabase mFirebasedatabase;

    ArrayList<String> companiesList,studentsList;

    myFBDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         database = new myFBDatabase();

        //Recent Commented
//        mFirebasedatabase = FirebaseDatabase.getInstance();
//
//        mCompaniesDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Companies");
//        mStudentsDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students");
//
//        studentsList = new ArrayList<>();
//        studentsList = new ArrayList<>();

        //Getting List of Students and Companies from firebase for login check purpose
//        attachStudentDatabaseReadListener();
//        attachStudentDatabaseReadListener();

//        //Intent value //Idea for getting from intent.
//        String newString;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                newString= null;
//            } else {
//                newString= extras.getString("check");
//            }
//        } else {
//            newString= (String) savedInstanceState.getSerializable("check");
//        }
//
//        Toast.makeText(LoginActivity.this,"Check="+newString,Toast.LENGTH_SHORT).show();


        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //Initializing views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.LoginButton);

        textViewSignup = findViewById(R.id.textViewSignUp);

        //Setting listener for SignUp TextView
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish this activity
                 finish();

               //Starting SignUp
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

        //Commented for testing Purpose
//        //if the objects getcurrentuser method is not null
//        //means user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            //close this activity
            finish();

                        //Thread for blocking for firebase. //Testing Part
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                String email = ""+firebaseAuth.getCurrentUser().getEmail();

                                checkingStudent(""+email);
                                checkingCompany(""+email);
                                checkingAdmin(""+email);
                                Log.d(TAG,"FB auth email:"+email);
                                //Break..
                            }
                        }, 800);


            //opening profile(main) activity
          //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEditText.getText().toString().trim();
                String password  = passwordEditText.getText().toString().trim();

                //checking if emailEditText and passwords are empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Please enter email Address", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }

                //if the emailEditText and passwordEditText are not empty
                //displaying a progress dialog
                progressDialog.setMessage("Logging in Please Wait...");
                progressDialog.show();

                //logging in the user
                Task<AuthResult> authResultTask = firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               // progressDialog.dismiss();
                                //if the task is successfull
                                if (task.isSuccessful()) {


                                    //Thread for blocking for firebase. //Testing Part
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This method will be executed once the timer is over
                                            // Start your app main activity
                                            //Checking User,Navigating Activity & Displaying Toast Message
                                            Student student = checkingStudent(email);
                                            Company company = checkingCompany(email);
                                            Boolean admin = checkingAdmin(email); //Either true or false

                                            //Here comes the login for User not found.. //null,null & admin==false
                                            if(student == null && company == null && !admin){
                                                Toast.makeText(LoginActivity.this,"Sorry User not Found", Toast.LENGTH_LONG).show();
                                            }
                                            progressDialog.dismiss();
                                            //Break..
                                        }

                                    }, 2000);

                                }
                                else {
                                    //display some message here
                                    Toast.makeText(LoginActivity.this,"Login Error", Toast.LENGTH_LONG).show();
                                }

                            }


                        });
            }
        });

    }

    private Boolean checkingAdmin(String email){
        if(email.equals("admin@admin.com")){
            //Closing login activity
            finish();
            //display welcome message here
            Toast.makeText(LoginActivity.this,"Wellcome Admin", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(LoginActivity.this,AdminActivity.class));
            return true;
        }
        return false;
    }

    private Student checkingStudent(String email) {
        Student student = database.checkStudent(email);
            if(student!=null){
                //Closing login activity
                finish();
                //display welcome message here
                Toast.makeText(LoginActivity.this,"Wellcome : "+student.getName(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,StudentActivity.class));
            }
        return student;
    }

    private Company checkingCompany(String email) {
        Company company = database.checkCompany(email);
            if(company!=null){
                Intent intent = new Intent(LoginActivity.this,CompanyActivity.class);
                //Closing login activity
                finish();
                //display welcome message here
                Toast.makeText(LoginActivity.this,"Wellcome : "+company.getName(), Toast.LENGTH_LONG).show();
                startActivity(intent);
           //     startActivity(new Intent(LoginActivity.this,CompanyActivity.class));
            }
        return company;
        }


    //Recent Commented
        //Checking if User is a student or a company
            //Then Redirecting to Relevent Screen
//                void checkStudent(String email){
//                    for(int i=0; i<studentsList.size(); i++){
//                        if(studentsList.get(i).equals(email)){
//                            Toast.makeText(LoginActivity.this,""+studentsList.get(i),Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(LoginActivity.this,StudentActivity.class));
//                        }
//                    }
//                }
//
//                void checkCompany(String email){
//                    for(int i=0; i<studentsList.size(); i++){
//                        if(studentsList.get(i).equals(email)){
//                            Toast.makeText(LoginActivity.this,""+studentsList.get(i),Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(LoginActivity.this,CompanyActivity.class));
//                        }
//                    }
//                }


}
