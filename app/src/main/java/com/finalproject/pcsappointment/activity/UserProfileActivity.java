package com.finalproject.pcsappointment.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
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

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvUserId, tvFirstName, tvLastName, tvUserType, tvPhone;
    private String TAG = UserProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvUserType = (TextView) findViewById(R.id.tvUserType);
        tvPhone = (TextView) findViewById(R.id.tvPhone);

        String userId = PrefManager.readPreference(this, PrefManager.LOGIN_ID);

        if (PrefManager.isNetworkAvailable(UserProfileActivity.this))
            UserProfileRequest(userId);
        else
            Toast.makeText(UserProfileActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
    }

    private void UserProfileRequest(String userId) {
        String url = API.USER_PROFILE_URL + userId;

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
                                Toast.makeText(UserProfileActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                                JSONObject detail = response.getJSONObject("Detail");
                                tvUserId.setText(detail.getString("userId"));
                                tvFirstName.setText(detail.getString("fname"));
                                tvLastName.setText(detail.getString("lname"));
                                tvUserType.setText(detail.getString("userType"));
                                tvPhone.setText(detail.getString("phone"));
                            } else {
                                Toast.makeText(UserProfileActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserProfileActivity.this, "Error Reading Data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
                Toast.makeText(UserProfileActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //RequestQueue.addToRequestQueue(jsonObjReq, tag_json_obj);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

}
