package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Ahmed on 3/16/2018.
 */

public class CompanyJobsFragment extends StudentBaseFragment {
    private ArrayList<String> jobsNamesList;
    public ArrayList<Job> jobsList;

    private FirebaseAuth firebaseAuth;

    private ChildEventListener mCompanyChildEventListener;

    private DatabaseReference mCompaniesDatabaseReference;

    private FirebaseDatabase mFirebasedatabase;

    ListView listView;
    StudentCustomAdapter customAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment  //There'll be fragment_student_fragments
        View v = inflater.inflate(R.layout.fragment_student_fragments, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        String companyID = firebaseAuth.getCurrentUser().getUid();

        //Custom Adapter
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mCompaniesDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs").child(companyID);

        //Custom Adapter
        jobsNamesList = new ArrayList<>();
        jobsList = new ArrayList<Job>();

        attachJobsDatabaseReadListener();
//        LoginActivity loginActivity = new LoginActivity();
//
//        studentsList = loginActivity.getCompaniesList();

        ListView listView = v.findViewById(R.id.customList);
        customAdapter = new StudentCustomAdapter(getActivity().getBaseContext(),jobsNamesList);
        listView.setAdapter(customAdapter);


        //Click Listener..
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                //Here has to Check if company is logegd in and display job view accordingly
                //Getting value from intent and checking
                //Already Known Clicked by company(CompanyJobFragment)

                //Checking Value from intent
                //Toast.makeText(getContext(),"CompanyJobProfile",Toast.LENGTH_SHORT).show();

                Job job = jobsList.get(position);
                Intent i = new Intent(getActivity(),CompanyJobProfile.class);
                //Passing Company Object As Parceable
                i.putExtra("job", job);

                //Here checking if company then displaying CompanyJobProfile.java
                startActivity(i);
                // String selectedFromList = (journalNames.getItemAtPosition(position).getString());
            }});

        customAdapter.notifyDataSetChanged();
        return v;
    }

    //Reading data from firebase
    void attachJobsDatabaseReadListener() {

        mCompanyChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                //Running Foreach loop
//                for(DataSnapshot d : dataSnapshot.getChildren()) {
//                    //Getting Value from 1 to 10 in ArrayList(tasks)
//                    Job job = d.getValue(Job.class);
//                    jobsList.add(job);
//                    jobsNamesList.add(job.getJobTitle());
//                }

//                Job job = dataSnapshot.getValue(Job.class);
//                jobsList.add(job);
//                jobsNamesList.add(jobsList.get(0).getJobTitle());
//                jobsNamesList.add(job.getjID());

                   //Now on Child Added will be called multiple times
                    Job job = dataSnapshot.getValue(Job.class);
                    jobsList.add(job);
                    jobsNamesList.add(job.getJobTitle());


                //     Toast.makeText(LoginActivity.this,""+companiesNamesList.get(i),Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onChildAdded: dataSnapshot.getChildren(Jobs Fragment): "+dataSnapshot.getChildren().toString());
                Log.d(TAG, "onChildAdded(Jobs Fragment): KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
                Log.d(TAG, "jobsList(Jobs Fragment): "+ jobsList);
                Log.d(TAG, "JobsNamesList(Jobs Fragment): "+ jobsNamesList);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                customAdapter.notifyDataSetChanged();
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

}
