package com.finalproject.pcsappointment.adapter.student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finalproject.pcsappointment.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StudentRequestResponseListAdapter extends RecyclerView.Adapter<StudentRequestResponseListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<JSONObject> mArrData;
    //private String imgUrl;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvConsultantName, tvDate, tvStartTime, tvEndTime, tvRequestDate, tvRequestStatus;

        public ItemViewHolder(View view) {
            super(view);
            tvConsultantName = (TextView) view.findViewById(R.id.tvConsultantName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
            tvRequestDate = (TextView) view.findViewById(R.id.tvRequestDate);
            tvRequestStatus = (TextView) view.findViewById(R.id.tvRequestStatus);
        }
    }

    public StudentRequestResponseListAdapter(Context context, List<JSONObject> arrData) {
        this.mContext = context;
        this.mArrData = arrData;
    }

    @Override
    public StudentRequestResponseListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_response, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentRequestResponseListAdapter.ItemViewHolder holder, final int position) {
        final JSONObject rData = mArrData.get(position);

        try {
            holder.tvConsultantName.setText("Consultant Name: " + rData.getString("ConsultantUserName"));
            holder.tvDate.setText("Appointment Date: " + rData.getString("ApDate"));
            holder.tvStartTime.setText("Start Time: " + rData.getString("ApStartTime"));
            holder.tvEndTime.setText("End Time: " + rData.getString("ApEndTime"));
            holder.tvRequestDate.setText("Request Date: " + rData.getString("RequestDate"));
            holder.tvRequestStatus.setText("Request Status: " + rData.getString("RequestStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mArrData.size();
    }

}