package com.EsyDigi.esyDigi.adpters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.EsyDigi.esyDigi.BookCheckListActivity;
import com.EsyDigi.esyDigi.R;
import com.EsyDigi.esyDigi.api.DatumGetTopic;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.MyViewHolder> {
    private Activity mactivity;
    List<DatumGetTopic> coverlist;
    private ViewListener3 viewlistener;
    private int Id;

    public interface ViewListener3 {
        void onClick(int position, View view,boolean status);

    }
    public CheckListAdapter(BookCheckListActivity bookCheckListActivity, List<DatumGetTopic> chapter, ViewListener3 listener) {
        this.mactivity = bookCheckListActivity;
        this.coverlist = chapter;
        this.viewlistener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_checklist, parent, false);
        return new CheckListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        holder.section_title.setText(coverlist.get(position));
        holder.section_title.setText(coverlist.get(position).getPostTitle());
        if (coverlist.get(position).getCheckStatus().equalsIgnoreCase("1")){
            holder.children.setChecked(true);
        }
        else {
            holder.children.setChecked(false);
        }
holder.children.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (holder.children.isChecked() == true){



            viewlistener.onClick(position, view,true);

        }
        else {
            viewlistener.onClick(position, view,false);
        }

    }

});

    }

    @Override
    public int getItemCount() {
        return coverlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView section_title;
        private AppCompatCheckBox children;
        public MyViewHolder(@NonNull View itemView) {


            super(itemView);
            section_title = itemView.findViewById(R.id.section_title);
            children = itemView.findViewById(R.id.children);

        }
    }
}
