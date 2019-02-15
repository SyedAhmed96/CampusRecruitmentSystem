package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class StudentsFragment extends StudentBaseFragment {
    private ArrayList<String> studentsNamesList;
    public ArrayList<Student> studentsList;

    private ChildEventListener mStudentChildEventListener;

    private DatabaseReference mStudentsDatabaseReference;

    private FirebaseDatabase mFirebasedatabase;

    ListView listView;
    StudentCustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_fragments, container, false);

        mFirebasedatabase = FirebaseDatabase.getInstance();

        mStudentsDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Students");

        //Custom Adapter
        studentsNamesList = new ArrayList<>();
        studentsList = new ArrayList<>();

        attachStudentDatabaseReadListener();

        listView = v.findViewById(R.id.customList);
        customAdapter = new StudentCustomAdapter(getActivity().getBaseContext(), studentsNamesList);
        listView.setAdapter(customAdapter);

        //Click Listener..
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
//                Toast.makeText(getActivity().getBaseContext(),"Item Clicked:"+ studentsNamesList.get(position),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity().getBaseContext(),"Item Clicked:"+ studentsList.get(position).getEmail(),Toast.LENGTH_LONG).show();

                Student student = studentsList.get(position);
                Intent i = new Intent(getActivity(),StudentProfile.class);
                //Passing Company Object As Parceable
                i.putExtra("student",student);
                startActivity(i);
                // String selectedFromList = (journalNames.getItemAtPosition(position).getString());
            }});

        customAdapter.notifyDataSetChanged();
        return v;
    }

    //Reading data from firebase
    void attachStudentDatabaseReadListener() {

        mStudentChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Student student = dataSnapshot.getValue(Student.class);
                studentsList.add(student);
                studentsNamesList.add(student.getName().trim());

                //     Toast.makeText(LoginActivity.this,""+studentsNamesList.get(i),Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onChildAdded: dataSnapshot.getChildren(Companies Fragment): "+dataSnapshot.getChildren().toString());
                Log.d(TAG, "onChildAdded(Companies Fragment): KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
                Log.d(TAG, "CompaniesList(Companies Fragment): "+ studentsList);
                Log.d(TAG, "CompaniesNamesList(Companies Fragment): "+ studentsNamesList);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Updating list view and adapter,arrayLists
                studentsList = new ArrayList<>();
                studentsNamesList = new ArrayList<>();

                Student student = dataSnapshot.getValue(Student.class);
                studentsList.add(student);
                studentsNamesList.add(student.getName().trim());

                //  customAdapter = new StudentCustomAdapter(getActivity().getBaseContext(), studentsNamesList);
                listView.setAdapter(customAdapter);

                customAdapter.notifyDataSetChanged();
                Log.d(TAG, "CompaniesNamesList(Companies Fragment): "+ studentsNamesList);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Not Working..
                //Updating list view and adapter,arrayLists
                studentsList = new ArrayList<>();
                studentsNamesList = new ArrayList<>();

                Student student = dataSnapshot.getValue(Student.class);
                studentsList.add(student);
                studentsNamesList.add(student.getName().trim());

              //  customAdapter = new StudentCustomAdapter(getActivity().getBaseContext(), studentsNamesList);
                listView.setAdapter(customAdapter);

                customAdapter.notifyDataSetChanged();
                Log.d(TAG, "CompaniesNamesList(Companies Fragment): "+ studentsNamesList);
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
