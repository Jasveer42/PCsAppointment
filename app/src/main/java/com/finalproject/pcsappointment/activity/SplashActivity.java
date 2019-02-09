package com.finalproject.pcsappointment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.finalproject.pcsappointment.activity.student.StudentMainActivity;
import com.finalproject.pcsappointment.Utility.PrefManager;
import com.finalproject.pcsappointment.activity.consultant.ConsultantMainActivity;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userType = PrefManager.readPreference(SplashActivity.this, PrefManager.USER_TYPE);
                boolean loginStatus = PrefManager.readLoginStatus(SplashActivity.this);

                if (loginStatus) {
                    if (userType.equalsIgnoreCase("Student"))
                        startActivity(new Intent(SplashActivity.this, StudentMainActivity.class));
                    else if (userType.equalsIgnoreCase("Pedagogical Counsellor"))
                        startActivity(new Intent(SplashActivity.this, ConsultantMainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
