package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ahmed on 3/10/2018.
 */

public class StudentActivity extends AppCompatActivity {
    //defining firebaseauth reference
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Campus System");
        setContentView(R.layout.activity_student);

        //Initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //For View Pager
        ViewPager viewPager = findViewById(R.id.pager);
        ArrayList<StudentBaseFragment> fragments = new ArrayList<>();

        CompaniesFragment fragment = new CompaniesFragment();
        fragment.setTitle("Companies");
        fragments.add(fragment);

        JobsFragment secondFragment = new JobsFragment();
        secondFragment.setTitle("Jobs");
        fragments.add(secondFragment);

        ProfileFragment thirdFragment = new ProfileFragment();
        thirdFragment.setTitle("Profile");
        fragments.add(thirdFragment);

        StudentPagerAdapter pagerAdapter = new StudentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    //For Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 0, 0, "History").setIcon(R.drawable.student_appbar_menu)
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
                startActivity(new Intent(StudentActivity.this, LoginActivity.class));
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

    protected void onPause() {
        super.onPause();
        //  mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        //mMessageAdapter.clear();
    }


    protected void onResume() {
        super.onResume();
        //  mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


}
