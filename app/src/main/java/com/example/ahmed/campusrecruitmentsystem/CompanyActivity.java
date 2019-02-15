package com.example.ahmed.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Ahmed on 3/10/2018.
 */

public class CompanyActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Campus System");
        setContentView(R.layout.activity_company);

        firebaseAuth = FirebaseAuth.getInstance();

        //For Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new StudentsFragment()).commit();
        }

        //For floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CompanyActivity.this,"Post A Job",Toast.LENGTH_SHORT).show();
                //Opening Companyjobpost class & activity_job_post
                Intent intent = new Intent(CompanyActivity.this, CompanyJobPost.class);
                startActivity(intent);

            }
        });

    }

    //For Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            fab.hide();
                            selectedFragment = new StudentsFragment();
                            break;
                        case R.id.nav_jobs:
                            fab.show();
                            selectedFragment = new CompanyJobsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    //For Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 0, 0, "History").setIcon(R.drawable.company_appbar_menu)
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
                startActivity(new Intent(CompanyActivity.this, LoginActivity.class));
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
