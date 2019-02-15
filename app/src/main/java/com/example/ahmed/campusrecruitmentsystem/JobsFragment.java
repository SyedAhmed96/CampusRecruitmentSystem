package com.example.ahmed.campusrecruitmentsystem;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Intent.getIntent;
/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends StudentBaseFragment {
    private ArrayList<String> jobsNamesList;
    public ArrayList<Job> jobsList;

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

        //Custom Adapter
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mCompaniesDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Jobs");

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
                Job job = jobsList.get(position);
                Intent i = new Intent(getActivity(),JobProfile.class);
                //Passing Company Object As Parceable
                i.putExtra("job", job);

                //Here checking if company then displaying CompanyJobProfile.java ??no
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

                //Running Foreach loop
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    //Getting Value from 1 to 10 in ArrayList(tasks)
                    Job job = d.getValue(Job.class);
                    jobsList.add(job);
                    jobsNamesList.add(job.getJobTitle());
                    customAdapter.notifyDataSetChanged();
                }

//                Job job = dataSnapshot.getValue(Job.class);
//                jobsList.add(job);
//                jobsNamesList.add(jobsList.get(0).getJobTitle());
//                jobsNamesList.add(job.getjID());


                //     Toast.makeText(LoginActivity.this,""+companiesNamesList.get(i),Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onChildAdded: dataSnapshot.getChildren(Jobs Fragment): "+dataSnapshot.getChildren().toString());
                Log.d(TAG, "onChildAdded(Jobs Fragment): KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
                Log.d(TAG, "jobsList(Jobs Fragment): "+ jobsList);
                Log.d(TAG, "JobsNamesList(Jobs Fragment): "+ jobsNamesList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: dataSnapshot.getChildren(Jobs Fragment): "+dataSnapshot.getChildren().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: dataSnapshot.getChildren(Jobs Fragment): "+dataSnapshot.getChildren().toString());
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
