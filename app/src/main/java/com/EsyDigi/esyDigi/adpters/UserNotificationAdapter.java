package com.EsyDigi.esyDigi.adpters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.EsyDigi.esyDigi.R;
import com.EsyDigi.esyDigi.api.DatumUserNotification;

import java.util.List;

public class UserNotificationAdapter extends RecyclerView.Adapter<UserNotificationAdapter.MyViewHolder> {
    private List<DatumUserNotification> dataList;
    private Context context;
    private Activity mActivity;

    public UserNotificationAdapter(Context applicationContext, List<DatumUserNotification> datumNotification) {
        this.context = applicationContext;
        this.dataList = datumNotification;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDayTime.setText(dataList.get(position).getNotificationDay()+" " +dataList.get(position).getNotificationTime());
        holder.tvNotification.setText(dataList.get(position).getNotification());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDayTime, tvNotification;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayTime = itemView.findViewById(R.id.tvDayTime);
            tvNotification = itemView.findViewById(R.id.tvNotification);
        }
    }
}
