package com.finalproject.pcsappointment.adapter.consultant;

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

public class ConsultantAppointmentsAdapter extends RecyclerView.Adapter<ConsultantAppointmentsAdapter.ItemViewHolder> {

    private Context mContext;
    private List<JSONObject> mArrData;
    //private String imgUrl;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvStartTime, tvEndTime;

        public ItemViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
            tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
        }
    }

    public ConsultantAppointmentsAdapter(Context context, List<JSONObject> arrData) {
        this.mContext = context;
        this.mArrData = arrData;
    }

    @Override
    public ConsultantAppointmentsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cons_appointment_slot, parent,
                false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ConsultantAppointmentsAdapter.ItemViewHolder holder, final int position) {
        final JSONObject rData = mArrData.get(position);

        try {
            holder.tvDate.setText("Date: " + rData.getString("Date"));
            holder.tvStartTime.setText("Start Time: " + rData.getString("StartTime"));
            holder.tvEndTime.setText("End Time: " + rData.getString("EndTime"));
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