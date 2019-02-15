package com.example.ahmed.campusrecruitmentsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity {
    private static final String TAG = "Test";
    boolean firstRun;
    FirebaseAuth mfirebaseauth;
    myFBDatabase database;

    // Splash screen timer
      private static int SPLASH_TIME_OUT = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            mfirebaseauth = FirebaseAuth.getInstance();
            database = new myFBDatabase();

            SharedPreferences settings=getSharedPreferences("prefs",0);
            firstRun=settings.getBoolean("firstRun",false);

            //Splash will load for first time
            if(firstRun!=false) {
             //if not running for first time
                //Running For Second Time

                //finishing Current Activity
                 finish();

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
            //if running for the first time
            else{
                SharedPreferences.Editor editor=settings.edit();
                editor.putBoolean("firstRun",true);
                editor.commit();

                new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        Intent i = new Intent(SplashActivity.this, SignUpActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }

        }
    
}

