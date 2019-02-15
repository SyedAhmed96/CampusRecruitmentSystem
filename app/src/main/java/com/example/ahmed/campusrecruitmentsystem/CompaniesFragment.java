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


/**
 * A simple {@link Fragment} subclass.
 */
public class CompaniesFragment extends StudentBaseFragment {
    private ArrayList<String> companiesNamesList;
    public ArrayList<Company> companiesList;

    private ChildEventListener mCompanyChildEventListener;

    private DatabaseReference mCompaniesDatabaseReference;

    private FirebaseDatabase mFirebasedatabase;

    ListView listView;
    StudentCustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_fragments, container, false);

        mFirebasedatabase = FirebaseDatabase.getInstance();

        mCompaniesDatabaseReference = mFirebasedatabase.getReference().child("CampusRecruitmentSystem").child("Companies");

        //Custom Adapter
        companiesNamesList = new ArrayList<>();
        companiesList = new ArrayList<>();

        attachCompanyDatabaseReadListener();

        listView = v.findViewById(R.id.customList);
        customAdapter = new StudentCustomAdapter(getActivity().getBaseContext(), companiesNamesList);
        listView.setAdapter(customAdapter);

        //Click Listener..
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
//                Toast.makeText(getActivity().getBaseContext(),"Item Clicked:"+ companiesNamesList.get(position),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity().getBaseContext(),"Item Clicked:"+ studentsList.get(position).getEmail(),Toast.LENGTH_LONG).show();

                    Company company = companiesList.get(position);
                    Intent i = new Intent(getActivity(),CompanyProfile.class);
                    //Passing Company Object As Parceable
                    i.putExtra("company",company);
                    startActivity(i);
                // String selectedFromList = (journalNames.getItemAtPosition(position).getString());
            }});

        customAdapter.notifyDataSetChanged();
        return v;
    }

    //Reading data from firebase
    void attachCompanyDatabaseReadListener() {

            mCompanyChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Company company = dataSnapshot.getValue(Company.class);
                    companiesList.add(company);
                    companiesNamesList.add(company.getName().trim());

                    //     Toast.makeText(LoginActivity.this,""+companiesNamesList.get(i),Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onChildAdded: dataSnapshot.getChildren(Companies Fragment): "+dataSnapshot.getChildren().toString());
                    Log.d(TAG, "onChildAdded(Companies Fragment): KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
                    Log.d(TAG, "CompaniesList(Companies Fragment): "+ companiesList);
                    Log.d(TAG, "CompaniesNamesList(Companies Fragment): "+ companiesNamesList);
                    customAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    //Here have to update arrayListNames and refresh adapter //In datasnapshop we have removed item
                    Company company = dataSnapshot.getValue(Company.class);
                    companiesList.remove(company);
                    companiesNamesList.remove(company.getName().trim());

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
