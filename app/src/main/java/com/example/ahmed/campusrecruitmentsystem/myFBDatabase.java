package com.example.ahmed.campusrecruitmentsystem;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Ahmed on 3/14/2018.
 */

public class myFBDatabase {
    private static final String TAG = "check";

    FirebaseAuth firebaseAuth;

    private ChildEventListener mCompanyChildEventListener,mStudentChildEventListener;

    private DatabaseReference mCompaniesDatabaseReference,mStudentsDatabaseReference;

    private DatabaseReference mStudentDatabaseReference;

    private DatabaseReference mStudentsAppliedDatabaseReference;
    private ChildEventListener mStudentsAppliedChildEventListener;

    private FirebaseDatabase mFirebasedatabase;
    private ChildEventListener mStudentProfileChildEventListener;

    //Assistant //Storage
    private StorageReference mChatPhotosStorageReference;
    private FirebaseStorage mFirebaseStorage;

    ArrayList<Company> companiesList;
    ArrayList<Student> studentsList;
    ArrayList<Student> profileList;

    ArrayList<String> appliedStudentsKey;

    public myFBDatabase() {
        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        mFirebasedatabase = FirebaseDatabase.getInstance();

        mCompaniesDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Companies");
        mStudentsDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students");

        companiesList = new ArrayList<>();
        studentsList = new ArrayList<>();

        profileList = new ArrayList<>();

        attachCompanyDatabaseReadListener();
        attachStudentDatabaseReadListener();

        attachStudentProfileDatabaseReadListener();
    }


    Student checkStudent(String email){
        Student student=null;
        for(int i=0; i<studentsList.size(); i++){
            if(studentsList.get(i).getEmail().equals(email)){
                student = studentsList.get(i);
            }
        }
        Log.d(TAG, "Student Object: "+student);
        return student;
    }


//    Student getCurrentStudent(String id){
//        Student student=null;
//        for(int i=0; i<studentsList.size(); i++){
//            if(studentsList.get(i).getsID().equals(id)){
//                student = studentsList.get(i);
//            }
//        }
//        Log.d(TAG, "Current Student Object: "+student);
//        return student;
//    }

    Company checkCompany(String email){
        Company company=null;
        for(int i=0; i<companiesList.size(); i++){
            if(companiesList.get(i).getEmail().equals(email)){
                company = companiesList.get(i);
            }
        }
        Log.d(TAG, "Company Object: "+company);
        return company;
    }


        //Updating Student Profile
     void updateStudentProfile(String id,Student student){
         String sID = id ;

         mFirebaseStorage = FirebaseStorage.getInstance();
         mChatPhotosStorageReference = mFirebaseStorage.getReference().child("profile_photos");

         //Initialize Firebase database components
         mFirebasedatabase = FirebaseDatabase.getInstance();
         mStudentDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students").child(""+sID);

         mStudentDatabaseReference.setValue(student);
     }


     Student getCurrentStudent(){
         Student student=null;
         attachStudentProfileDatabaseReadListener();
         for(int i=0; i<profileList.size(); i++){
             if(profileList.get(i).getsID().equals(""+firebaseAuth.getCurrentUser().getUid())){
                 student = profileList.get(i);
             }
         }
         Log.d(TAG, "Student Object(Profile): "+student);
         return student;
     }

//
//     //Not Working has to check later
//     //Here we have to get the applied students list
//     ArrayList<String> getAppliedStudentList(String cID, String jobID){
//
//         //Update fb reference.. //Has to read apply node and comes applied(for each logic)
//         mStudentsAppliedDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(cID).child(jobID).child("Apply");
//         //attaching db read listener..
//          appliedStudentsKey = new ArrayList<String>();
//
//         if (mStudentsAppliedDatabaseReference == null) {
//
//
//             mStudentsAppliedChildEventListener = new ChildEventListener() {
//                 @Override
//                 public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                     for(DataSnapshot d : dataSnapshot.getChildren()){
//                         appliedStudentsKey.add(""+d.getKey());
//                     }
//
//                     Log.d(TAG, "onChildAdded: dataSnapshot.getChildren: " + dataSnapshot.getChildren().toString());
//                     Log.d(TAG, "onChildAdded: KEY" + dataSnapshot.getKey() + " value " + dataSnapshot.getValue().toString());
//                     Log.d(TAG, "appliedStudentsKey : "+appliedStudentsKey);
//
//                 }
//
//                 @Override
//                 public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                 }
//
//                 @Override
//                 public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                 }
//
//                 @Override
//                 public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                 }
//
//                 @Override
//                 public void onCancelled(DatabaseError databaseError) {
//
//                 }
//             };
//             mStudentsAppliedDatabaseReference.addChildEventListener(mStudentsAppliedChildEventListener);
//         }
//
//         return appliedStudentsKey;
//     }



    //Attaching Student Read Listener
    //Reading Student data from firebase
    void attachStudentProfileDatabaseReadListener() {

        if (mStudentProfileChildEventListener == null) {


            mStudentProfileChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                    String student = dataSnapshot.getValue().toString();

                    Student student;

                    student = dataSnapshot.getValue(Student.class);

                    profileList.add(student);

//                    if(dataSnapshot.getKey().equals(""+firebaseAuth.getCurrentUser().getUid())){
//                         student = dataSnapshot.getValue(Student.class);
//                        Log.d(TAG, "Current Student Object : "+student.getName());
//                    }


//                    studentsList.add(student.getEmail().trim());

//                    Toast.makeText(LoginActivity.this,""+studentsList.get(i1),Toast.LENGTH_SHORT).show();
//                    i1++;

                    Log.d(TAG, "onChildAdded: dataSnapshot.getChildren: " + dataSnapshot.getChildren().toString());
                    Log.d(TAG, "onChildAdded: KEY" + dataSnapshot.getKey() + " value " + dataSnapshot.getValue().toString());
                    Log.d(TAG, "Profile list : "+profileList);

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
            mStudentsDatabaseReference.addChildEventListener(mStudentProfileChildEventListener);
        }

    }



    //Reading data from firebase
    void attachCompanyDatabaseReadListener() {

            mCompanyChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Company company = dataSnapshot.getValue(Company.class);
                    companiesList.add(company);

                    //     Toast.makeText(LoginActivity.this,""+studentsList.get(i),Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onChildAdded: dataSnapshot.getChildren: "+dataSnapshot.getChildren().toString());
                    Log.d(TAG, "onChildAdded: KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
                    Log.d(TAG, "CompaniesList: "+companiesList);
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
            mCompaniesDatabaseReference.addChildEventListener(mCompanyChildEventListener);
    }



    //Reading Student data from firebase
    void attachStudentDatabaseReadListener() {

        if (mStudentChildEventListener == null) {


            mStudentChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Student student = dataSnapshot.getValue(Student.class);
                    studentsList.add(student);

//                    Toast.makeText(LoginActivity.this,""+studentsList.get(i1),Toast.LENGTH_SHORT).show();
//                    i1++;

                    Log.d(TAG, "onChildAdded: dataSnapshot.getChildren: "+dataSnapshot.getChildren().toString());
                    Log.d(TAG, "onChildAdded: KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
                    Log.d(TAG, "StudentsList: "+studentsList);
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
            mStudentsDatabaseReference.addChildEventListener(mStudentChildEventListener);

        }

    }



}
