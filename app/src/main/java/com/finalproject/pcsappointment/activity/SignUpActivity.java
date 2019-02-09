package com.finalproject.pcsappointment.activity;

import android.app.ProgressDialog;
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
import com.finalproject.pcsappointment.Utility.API;
import com.finalproject.pcsappointment.Utility.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;



public class SignUpActivity extends AppCompatActivity {
    private String TAG = SignUpActivity.class.getSimpleName();
    private EditText etUserId, etFirstName, etLastName, etPassword, etConfirmPassword, etPhone;
    private Spinner spinnerUserType;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUserId = (EditText) findViewById(R.id.etUserId);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etPhone = (EditText) findViewById(R.id.etPhone);

        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = etUserId.getText().toString();
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String phone = etPhone.getText().toString();
                String userType = spinnerUserType.getSelectedItem().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Password Mismatch!", Toast.LENGTH_SHORT).show();
                } else {
                    if (PrefManager.isNetworkAvailable(SignUpActivity.this))
                        RegisterUserRequest(userId, firstName, lastName, phone, userType, password);
                    else
                        Toast.makeText(SignUpActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void RegisterUserRequest(String userId, String firstName, String lastName, String phone, String userType,
                                     String password) {
        String url = API.REGISTRATION_URL + userId + "&" + firstName + "&" + lastName + "&" + password + "&"
                + phone + "&" + userType;

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
                                Toast.makeText(SignUpActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Error While Registering User",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                Toast.makeText(SignUpActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}
