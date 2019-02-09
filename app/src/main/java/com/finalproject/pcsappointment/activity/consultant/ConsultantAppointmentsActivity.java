package com.finalproject.pcsappointment.activity.consultant;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.finalproject.pcsappointment.adapter.consultant.ConsultantAppointmentsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultantAppointmentsActivity extends AppCompatActivity {
    private RecyclerView rvConsultantApSlots;
    private ConsultantAppointmentsAdapter adapter;
    private List<JSONObject> arrData = new ArrayList<>();
    private String TAG = ConsultantAppointmentsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_appointment_slots);

        rvConsultantApSlots = (RecyclerView) findViewById(R.id.rvConsultantApSlots);

        rvConsultantApSlots.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        rvConsultantApSlots.setLayoutManager(layoutManager);
        adapter = new ConsultantAppointmentsAdapter(this, arrData);
        rvConsultantApSlots.setAdapter(adapter);

        String userId = PrefManager.readPreference(ConsultantAppointmentsActivity.this, PrefManager.LOGIN_ID);
        if (PrefManager.isNetworkAvailable(ConsultantAppointmentsActivity.this))
            ConsultantAppointmentSlots(userId);
        else
            Toast.makeText(ConsultantAppointmentsActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
    }

    private void ConsultantAppointmentSlots(String userId) {
        String url = API.CONSULTANT_APPOINTMENT_SLOTS_URL + userId;

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
                                arrData.clear();
                                JSONArray jArray = response.getJSONArray("Detail");
                                for (int i = 0; i < jArray.length(); i++) {
                                    arrData.add(jArray.getJSONObject(i));
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ConsultantAppointmentsActivity.this, "Unable to Fetch Data",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ConsultantAppointmentsActivity.this, "Error Reading Data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
                Toast.makeText(ConsultantAppointmentsActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //RequestQueue.addToRequestQueue(jsonObjReq, tag_json_obj);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

}