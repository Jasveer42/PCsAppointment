package com.finalproject.pcsappointment.adapter.consultant;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class StudentRequestListAdapter extends RecyclerView.Adapter<StudentRequestListAdapter.ItemViewHolder> {

    private Context mContext;
    private String userId;
    private List<JSONObject> mArrData;
    //private String imgUrl;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvDate, tvStartTime, tvEndTime, tvRequestDate, tvRequestStatus;
        Button btnAccept, btnDecline;
        LinearLayout layoutButtons;

        public ItemViewHolder(View view) {
            super(view);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
            tvRequestDate = (TextView) view.findViewById(R.id.tvRequestDate);
            tvRequestStatus = (TextView) view.findViewById(R.id.tvRequestStatus);
            btnAccept = (Button) view.findViewById(R.id.btnAccept);
            btnDecline = (Button) view.findViewById(R.id.btnDecline);
            layoutButtons = (LinearLayout) view.findViewById(R.id.layoutButtons);
        }
    }

    public StudentRequestListAdapter(Context context, String userId, List<JSONObject> arrData) {
        this.mContext = context;
        this.userId = userId;
        this.mArrData = arrData;
    }

    @Override
    public StudentRequestListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_request, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentRequestListAdapter.ItemViewHolder holder, final int position) {
        final JSONObject rData = mArrData.get(position);

        try {
            holder.tvUserName.setText("UserName: " + rData.getString("UserName"));
            holder.tvDate.setText("Date: " + rData.getString("ApDate"));
            holder.tvStartTime.setText("Start Time: " + rData.getString("ApStartTime"));
            holder.tvEndTime.setText("End Time: " + rData.getString("ApEndTime"));
            holder.tvRequestDate.setText("Request Date: " + rData.getString("RequestDate"));

            String requestStatus = rData.getString("RequestStatus");
            if (requestStatus.equalsIgnoreCase("ACCEPTED")
                    || requestStatus.equalsIgnoreCase("DECLINED")) {
                holder.tvRequestStatus.setVisibility(View.VISIBLE);
                holder.layoutButtons.setVisibility(View.GONE);
                holder.tvRequestStatus.setText("Request Status: " + requestStatus);
            } else {
                holder.tvRequestStatus.setVisibility(View.GONE);
                holder.layoutButtons.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String apId = rData.getString("ApId");
                    String requestId = rData.getString("RequestId");
                    String studentId = rData.getString("UserId");
                    repondToRequest(apId, requestId, studentId, "ACCEPTED");
                } catch (Exception e) {
                    Toast.makeText(mContext, "Error Reading Appointment Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String apId = rData.getString("ApId");
                    String requestId = rData.getString("RequestId");
                    String studentId = rData.getString("UserId");
                    repondToRequest(apId, requestId, studentId, "DECLINED");
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


    private void repondToRequest(String apId, String requestId, String studentId, String status) {
        String url = API.RESPOND_TO_REQUEST_URL + userId + "&" + apId + "&" + requestId + "&" + studentId + "&" + status;

        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
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
                //  VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
                Toast.makeText(mContext, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        //RequestQueue.addToRequestQueue(jsonObjReq, tag_json_obj);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mContext).add(jsonObjReq);
    }

}