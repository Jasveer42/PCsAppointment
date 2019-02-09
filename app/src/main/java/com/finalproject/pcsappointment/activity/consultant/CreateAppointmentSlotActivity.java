package com.finalproject.pcsappointment.activity.consultant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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

import java.util.Calendar;

public class CreateAppointmentSlotActivity extends AppCompatActivity {
    private EditText etDate, etStartTime, etEndTime;
    private Button btnCreateSlot;

    static final int DATE_DIALOG_ID = 1110;
    static final int START_TIME_DIALOG_ID = 1111;
    static final int END_TIME_DIALOG_ID = 1112;
    private int CURRENT_SELECTED_DIALOG = 0;

    private int mYear, mMonth, mDay;
    private int opening_hr, closing_hr;
    private int opening_min, closing_min;

    private String TAG = CreateAppointmentSlotActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_slot);

        etDate = (EditText) findViewById(R.id.etDate);
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        etEndTime = (EditText) findViewById(R.id.etEndTime);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        opening_hr = closing_hr = c.get(Calendar.HOUR_OF_DAY);
        opening_min = closing_min = c.get(Calendar.MINUTE);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(CreateAppointmentSlotActivity.this, view);
                createdDialog(DATE_DIALOG_ID).show();
            }
        });

        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_SELECTED_DIALOG = START_TIME_DIALOG_ID;
                hideSoftKeyboard();
                createdDialog(START_TIME_DIALOG_ID).show();
            }
        });

        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_SELECTED_DIALOG = END_TIME_DIALOG_ID;
                hideSoftKeyboard();
                createdDialog(END_TIME_DIALOG_ID).show();
            }
        });

        hideSoftKeyboard();

        btnCreateSlot = (Button) findViewById(R.id.btnCreateSlot);

        btnCreateSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = PrefManager.readPreference(CreateAppointmentSlotActivity.this, PrefManager.LOGIN_ID);

                String date = etDate.getText().toString().trim();
                String startTime = etStartTime.getText().toString().trim();
                String endTime = etEndTime.getText().toString().trim();

                if (PrefManager.isNetworkAvailable(CreateAppointmentSlotActivity.this))
                    CreateAppointmentSlot(userId, date, startTime, endTime);
                else
                    Toast.makeText(CreateAppointmentSlotActivity.this, "No Internet Available",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected Dialog createdDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, datePickerListener, mYear, mMonth, mDay);
            case START_TIME_DIALOG_ID:
                return new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, timePickerListener, opening_hr,
                        opening_min, true);
            case END_TIME_DIALOG_ID:
                return new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, timePickerListener, closing_hr,
                        closing_min, true);
        }
        return null;
    }

    private static String utilMonthDay(int value) {
        if (value < 10) return "0" + String.valueOf(value);
        else return String.valueOf(value);
    }

    private static String utilTime(int value) {
        if (value < 10) return "0" + String.valueOf(value);
        else return String.valueOf(value);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String aDate = new StringBuilder().append(year).append('-').append(utilMonthDay(month)).append('-').append(utilMonthDay(day))
                    .toString();

            mYear = year;
            mMonth = month;
            mDay = day;
            etDate.setText(aDate);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
           /* hr = hourOfDay;
            min = minutes;
            updateTime(hr, min);*/

            String aTime = new StringBuilder().append(utilTime(hourOfDay)).append(':').append(utilTime(minutes))
                    //.append(" ")
                    //.append(timeSet)
                    .toString();


            if (CURRENT_SELECTED_DIALOG == START_TIME_DIALOG_ID) {
                opening_hr = hourOfDay;
                opening_min = minutes;
                etStartTime.setText(aTime);
            } else if (CURRENT_SELECTED_DIALOG == END_TIME_DIALOG_ID) {
                closing_hr = hourOfDay;
                closing_min = minutes;
                etEndTime.setText(aTime);
            }
        }
    };

    public static void closeKeyboard(Context c, View view) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) c).getCurrentFocus();
        if (v == null)
            return;

        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void CreateAppointmentSlot(String userId, String date, String startTime, String endTime) {
        String url = API.CREATE_APPOINTMENT_SLOT_URL + userId + "&" + date + "&" + startTime + "&" + endTime;

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
                                Toast.makeText(CreateAppointmentSlotActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CreateAppointmentSlotActivity.this, response.getString("Message"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CreateAppointmentSlotActivity.this, "Error Reading Data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
                Toast.makeText(CreateAppointmentSlotActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

}
