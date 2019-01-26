package com.finalproject.pcsappointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.finalproject.pcsappointment.R;
import com.finalproject.pcsappointment.Utility.PrefManager;

public class MainActivity extends AppCompatActivity {
    Button signBtn;
    Button createBtn;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signBtn = (Button) findViewById(R.id.signInBtn);
        createBtn = (Button) findViewById(R.id.createBtn);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.clearPreference(MainActivity.this);
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

}
