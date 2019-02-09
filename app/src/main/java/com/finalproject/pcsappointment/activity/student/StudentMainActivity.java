package com.finalproject.pcsappointment.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.finalproject.pcsappointment.R;
import com.finalproject.pcsappointment.Utility.PrefManager;
import com.finalproject.pcsappointment.activity.SignInActivity;
import com.finalproject.pcsappointment.activity.UserProfileActivity;

public class StudentMainActivity extends AppCompatActivity {
    private Button btnUserProfile, btnViewAppointments, btnViewRequestResponse, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);
        btnViewAppointments = (Button) findViewById(R.id.btnViewAppointments);
        btnViewRequestResponse = (Button) findViewById(R.id.btnViewRequestResponse);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMainActivity.this, UserProfileActivity.class));
            }
        });

        btnViewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMainActivity.this, StudentAppointmentsListActivity.class));
            }
        });

        btnViewRequestResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMainActivity.this, StudentRequestResponseListActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.clearPreference(StudentMainActivity.this);
                startActivity(new Intent(StudentMainActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

}
