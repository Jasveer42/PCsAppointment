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
import com.finalproject.pcsappointment.adapter.consultant.StudentRequestListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentRequestListActivity extends AppCompatActivity {
    private RecyclerView rvStudentRequest;
    private StudentRequestListAdapter adapter;
    private List<JSONObject> arrData = new ArrayList<>();
    private String TAG = StudentRequestListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_request_list);

    }

}
