package com.EsyDigi.esyDigi.adpters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.EsyDigi.esyDigi.AudioActivity;
import com.EsyDigi.esyDigi.R;
import com.EsyDigi.esyDigi.api.Item;
import com.EsyDigi.esyDigi.api.SongsModel;

import java.util.ArrayList;
import java.util.List;

public class AudioAdapter extends ArrayAdapter<Item> {
    ArrayList<Item> animalList = new ArrayList<>();
    private MediaPlayer mp;
    private Context context;
    private List<SongsModel> songsModelList;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public AudioAdapter(Context context, int textViewResourceId, ArrayList<Item> objects, List<SongsModel> songsList) {
        super(context, textViewResourceId, objects);
        animalList = objects;
        this.context = context;
        this.songsModelList = songsList;
    }

    @Override
    public int getCount() {
        return songsModelList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.audio_item_row, null);
        TextView textView = (TextView) v.findViewById(R.id.textviewTitleAudio);
        RelativeLayout relativeAudio = (RelativeLayout) v.findViewById(R.id.relativeAudio);

            textView.setText(songsModelList.get(position).getTitle());


        relativeAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songsModelList .get(position).getMp3Url().equalsIgnoreCase(" ")|| songsModelList.get(position).getMp3Url().isEmpty()){
                    Toast.makeText(context, R.string.No_Audio_, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ((AudioActivity) context).openDialog(position);
                }


            }
        });


        return v;

    }


}

