package com.finalproject.pcsappointment.adapter.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.finalproject.pcsappointment.R;
import com.finalproject.pcsappointment.Utility.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StundentAppointmentsListAdapter extends RecyclerView.Adapter<StundentAppointmentsListAdapter.ItemViewHolder> {

    private Context mContext;
    private String userId;
    private List<JSONObject> mArrData;
    //private String imgUrl;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvDate, tvStartTime, tvEndTime, tvRequestDate, tvRequestStatus;
        Button btnRequest;

        public ItemViewHolder(View view) {
            super(view);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
            tvRequestDate = (TextView) view.findViewById(R.id.tvRequestDate);
            tvRequestStatus = (TextView) view.findViewById(R.id.tvRequestStatus);
            btnRequest = (Button) view.findViewById(R.id.btnRequest);
        }
    }

    public StundentAppointmentsListAdapter(Context context, String userId, List<JSONObject> arrData) {
        this.mContext = context;
        this.userId = userId;
        this.mArrData = arrData;
    }

    @Override
    public StundentAppointmentsListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_appointment_slot, parent,
                false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StundentAppointmentsListAdapter.ItemViewHolder holder, final int position) {
        final JSONObject rData = mArrData.get(position);

        try {
            holder.tvUserName.setText("UserName: " + rData.getString("UserName"));
            holder.tvDate.setText("Date: " + rData.getString("Date"));
            holder.tvStartTime.setText("Start Time: " + rData.getString("StartTime"));
            holder.tvEndTime.setText("End Time: " + rData.getString("EndTime"));
            String requestStatus = rData.getString("RequestStatus");
            holder.tvRequestDate.setText("Request Date: " + rData.getString("RequestDate"));
            holder.tvRequestStatus.setText("Request Status: " + requestStatus);

            if (requestStatus == null || requestStatus.isEmpty()) {
                holder.btnRequest.setVisibility(View.VISIBLE);
                holder.tvRequestDate.setVisibility(View.GONE);
                holder.tvRequestStatus.setVisibility(View.GONE);
            } else {
                holder.btnRequest.setVisibility(View.GONE);
                holder.tvRequestDate.setVisibility(View.VISIBLE);
                holder.tvRequestStatus.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String apId = rData.getString("Id");
                    requestAppointment(userId, apId);
                } catch (Exception e) {
                    Toast.makeText(mContext, "Error Reading Appointment Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mArrData.size();
    }


    private void requestAppointment(String userId, String apId) {
        String url = API.REQUEST_APPOINTMENT_URL + userId + "&" + apId;

        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        try {
                            if (response.getString("Status").equalsIgnoreCase(API.STATUS_OK)) {
                                Toast.makeText(mContext, response.getString("Message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, response.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Error Reading Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                Toast.makeText(mContext, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mContext).add(jsonObjReq);
    }

}