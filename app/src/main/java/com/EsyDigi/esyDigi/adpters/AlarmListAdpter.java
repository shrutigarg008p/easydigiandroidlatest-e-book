package com.EsyDigi.esyDigi.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.EsyDigi.esyDigi.R;
import com.EsyDigi.esyDigi.TimerSettings;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;

import java.util.HashSet;
import java.util.List;

public class AlarmListAdpter extends RecyclerView.Adapter<AlarmListAdpter.MyViewHolder> {

    List<String> alarmlist;
    Context context;

    private ViewListener2 viewlistener;
    HashSet<String> alarmset;
    private List<String> serverData;



    public interface ViewListener2 {
         void onClick(int position, View view);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView alarmname;
        TextView delete;

        public MyViewHolder(View view) {
            super(view);

            delete = view.findViewById(R.id.delete);
            alarmname = view.findViewById(R.id.alarmtime);

        }
    }

    public AlarmListAdpter(Context context, List<String> alarmlist, TimerSettings listner) {
        this.alarmlist = alarmlist;
        this.alarmset = alarmset;
        this.context = context;
        this.viewlistener = listner;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String str = alarmlist.get(position);
        String[] splited = str.trim().split(" ");
        String day = splited[0];
        String time = splited[1];


        if (MySharedPreferenceClass.getLanguageName(context).equalsIgnoreCase("english")) {
            if (day.equals("1") || day.equalsIgnoreCase("Måndag") || day.equalsIgnoreCase("Monday")) {
                holder.alarmname.setText("Monday " + time);
            } else if (day.equals("2") || day.equalsIgnoreCase("Tuesday") || day.equalsIgnoreCase("Tisdag")) {
                holder.alarmname.setText("Tuesday " + time);


            } else if (day.equals("3") || day.equalsIgnoreCase("Wednesday") || day.equalsIgnoreCase("Onsdag")) {
                holder.alarmname.setText("Wednesday " + time);

            } else if (day.equals("4") || day.equalsIgnoreCase("Thursday") || day.equalsIgnoreCase("Torsdag")) {
                holder.alarmname.setText("Thursday " + time);

            } else if (day.equals("5") || day.equalsIgnoreCase("Friday") || day.equalsIgnoreCase("Fredag")) {
                holder.alarmname.setText("Friday " + time);

            } else if (day.equals("6") || day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Lördag")) {
                holder.alarmname.setText("Saturday " + time);

            } else if (day.equals("7") || day.equalsIgnoreCase("Sunday") || day.equalsIgnoreCase("Söndag")) {
                holder.alarmname.setText("Sunday " + time);

            }

        } else {

            if (day.equals("1") || day.equalsIgnoreCase("Måndag") || day.equalsIgnoreCase("Monday")) {
                holder.alarmname.setText("Måndag " + time);
            } else if (day.equals("2") || day.equalsIgnoreCase("Tuesday") || day.equalsIgnoreCase("Tisdag")) {
                holder.alarmname.setText("Tisdag " + time);

            } else if (day.equals("3") || day.equalsIgnoreCase("Wednesday") || day.equalsIgnoreCase("Onsdag")) {
                holder.alarmname.setText("Onsdag " + time);

            } else if (day.equals("4") || day.equalsIgnoreCase("Thursday") || day.equalsIgnoreCase("Torsdag")) {
                holder.alarmname.setText("Torsdag " + time);

            } else if (day.equals("5") || day.equalsIgnoreCase("Friday") || day.equalsIgnoreCase("Fredag")) {

                holder.alarmname.setText("Fredag " + time);

            } else if (day.equals("6") || day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Lördag")) {
                holder.alarmname.setText("Lördag " + time);

            } else if (day.equals("7") || day.equalsIgnoreCase("Sunday") || day.equalsIgnoreCase("Söndag")) {

                holder.alarmname.setText("Söndag " + time);
            }


        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                viewlistener.onClick(position, view);
             /*   try {
                    viewlistener.onClick(position, view);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ExceptionData..."+e);
                }*/

            }


        });


    }


    @Override
    public int getItemCount() {
        return alarmlist.size();
        //return  alarmset.size();
    }


}
