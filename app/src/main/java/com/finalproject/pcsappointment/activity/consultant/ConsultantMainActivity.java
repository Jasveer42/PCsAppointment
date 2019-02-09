package com.finalproject.pcsappointment.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.finalproject.pcsappointment.R;
import com.finalproject.pcsappointment.Utility.PrefManager;
import com.finalproject.pcsappointment.activity.SignInActivity;
import com.finalproject.pcsappointment.activity.UserProfileActivity;

public class ConsultantMainActivity extends AppCompatActivity {
    private Button btnUserProfile, btnCreateAppointmentSlot, btnViewAppointmentSlots, btnViewRequests, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_main);
        btnUserProfile = (Button) findViewById(R.id.btnUserProfile);
        btnCreateAppointmentSlot = (Button) findViewById(R.id.btnCreateAppointmentSlot);
        btnViewAppointmentSlots = (Button) findViewById(R.id.btnViewAppointmentSlots);
        btnViewRequests = (Button) findViewById(R.id.btnViewRequests);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConsultantMainActivity.this, UserProfileActivity.class));
            }
        });

        btnCreateAppointmentSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConsultantMainActivity.this, CreateAppointmentSlotActivity.class));
            }
        });

        btnViewAppointmentSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConsultantMainActivity.this, ConsultantAppointmentsActivity.class));
            }
        });

        btnViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConsultantMainActivity.this, StudentRequestListActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.clearPreference(ConsultantMainActivity.this);
                startActivity(new Intent(ConsultantMainActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

}
