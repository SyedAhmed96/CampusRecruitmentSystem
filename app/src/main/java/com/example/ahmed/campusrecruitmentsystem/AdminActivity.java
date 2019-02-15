package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.ExpandableListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ahmed on 3/15/2018.
 */

public class AdminActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    private static final String TAG = "Check";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Admin Console");
        //activity_student contains view pager..
        setContentView(R.layout.activity_student);

        firebaseAuth = FirebaseAuth.getInstance();

        //For View Pager
        ViewPager viewPager = findViewById(R.id.pager);
        ArrayList<StudentBaseFragment> fragments = new ArrayList<>();

        StudentsFragment firstFragment = new StudentsFragment();
        firstFragment.setTitle("Students");
        fragments.add(firstFragment);

        CompaniesFragment secondFragment = new CompaniesFragment();
        secondFragment.setTitle("Companies");
        fragments.add(secondFragment);

        JobsFragment thirdFragment = new JobsFragment();
        thirdFragment.setTitle("Jobs");
        fragments.add(thirdFragment);

        StudentPagerAdapter pagerAdapter = new StudentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    //For Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 0, 0, "History").setIcon(R.drawable.admin_appbar_menu)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                break;

            case R.id.exit:
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                System.exit(0);
                break;
        }
        return true;
    }

}
