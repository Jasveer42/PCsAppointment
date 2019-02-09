package com.finalproject.pcsappointment.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.finalproject.pcsappointment.R;
import com.finalproject.pcsappointment.activity.student.StudentMainActivity;
import com.finalproject.pcsappointment.Utility.API;
import com.finalproject.pcsappointment.Utility.PrefManager;
import com.finalproject.pcsappointment.activity.consultant.ConsultantMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private EditText etUserId, etPassword;
    private Spinner spinnerUserType;
    private Button btnLogin, btnSignUp;
    private String TAG = SignInActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if (PrefManager.readLoginStatus(this)) {
            startActivity(new Intent(this, StudentMainActivity.class));
            finish();
        }

        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);
        etUserId = (EditText) findViewById(R.id.etUserId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = spinnerUserType.getSelectedItem().toString();
                String userId = etUserId.getText().toString();
                String password = etPassword.getText().toString();

                if (PrefManager.isNetworkAvailable(SignInActivity.this))
                    LoginRequest(userType, userId, password);
                else
                    Toast.makeText(SignInActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }

    private void LoginRequest(String userType, String userId, String password) {
        String url = API.LOGIN_URL + userType + "&" + userId + "&" + password;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                        try {
                            if (response.getString("Status").equalsIgnoreCase(API.STATUS_OK)) {
                                Toast.makeText(SignInActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                                PrefManager.saveLoginStatus(SignInActivity.this, true);
                                PrefManager.savePreference(SignInActivity.this, PrefManager.LOGIN_ID,
                                        response.getString("UserID"));
                                PrefManager.savePreference(SignInActivity.this, PrefManager.USER_NAME,
                                        response.getString("FirstName") + " " + response.getString("LastName"));
                                PrefManager.savePreference(SignInActivity.this, PrefManager.USER_TYPE,
                                        response.getString("UserType"));

                                if (response.getString("UserType").equalsIgnoreCase("Student"))
                                    startActivity(new Intent(SignInActivity.this, StudentMainActivity.class));
                                else if (response.getString("UserType").equalsIgnoreCase
                                        ("Pedagogical Counsellor"))
                                    startActivity(new Intent(SignInActivity.this, ConsultantMainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignInActivity.this, "Error Reading Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
                Toast.makeText(SignInActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //RequestQueue.addToRequestQueue(jsonObjReq, tag_json_obj);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}
