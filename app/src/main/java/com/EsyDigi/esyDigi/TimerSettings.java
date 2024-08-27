package com.EsyDigi.esyDigi;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.Interface.ViewListener;
import com.EsyDigi.esyDigi.adpters.AlarmListAdpter;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.GetNotiDetail;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class TimerSettings extends ParentActivity implements AlarmListAdpter.ViewListener2 {
    TextView choosedataandtimme, add, savenotification;
    CardView spinnerview;
    RecyclerView alarmlist_recycler;
    TextView done;
    String alarmday, alarmtime;
    AlarmListAdpter mAdapter;
    private ViewListener someInterface;

    ArrayList<String> alarmlist = new ArrayList<>();
    ArrayList<String> day = new ArrayList<>();
    ArrayList<String> serverday = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        initViews();
        initializeSpinner();
        getNotificationDetail();

        savenotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                savealarm( time);


            }
        });

        choosedataandtimme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerview.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.top_from_bottom);
                spinnerview.startAnimation(animation);
                choosedataandtimme.setClickable(false);


            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosedataandtimme.setText(alarmday + " " + alarmtime);

                spinnerview.setVisibility(View.GONE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.top_to_bottom);
                spinnerview.startAnimation(animation);
                choosedataandtimme.setClickable(true);


            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alarmlist.contains(choosedataandtimme.getText().toString()) && !choosedataandtimme.getText().toString().equals("Choose Day and Time") ) {
                    alarmlist.add(choosedataandtimme.getText().toString());
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(TimerSettings.this, "Already selected", Toast.LENGTH_LONG).show();

                   /* serverday.clear();
                    day.clear();
                    time.clear();


                    for (int i = 0; i < alarmlist.size(); i++) {

                        String dayandtime[] = alarmlist.get(i).split(" ");
                        day.add(dayandtime[0]);
                        time.add(dayandtime[1]);
                    }
                    if (day != null || day.size() > 0) {


                        for (int j = 0; j < day.size(); j++) {

                            if (day.get(j).equalsIgnoreCase("Monday") || day.get(j).equalsIgnoreCase("Måndag") || day.get(j).equals("1")) {
                                serverday.add(String.valueOf(1));
                            } else if (day.get(j).equalsIgnoreCase("Tuesday") || day.get(j).equalsIgnoreCase("Tisdag") || day.get(j).equals("2")) {
                                serverday.add(String.valueOf(2));
                            } else if (day.get(j).equalsIgnoreCase("Wednesday") || day.get(j).equalsIgnoreCase("Onsdag") || day.get(j).equals("3")) {
                                serverday.add(String.valueOf(3));
                            } else if (day.get(j).equalsIgnoreCase("Thursday") || day.get(j).equalsIgnoreCase("Torsdag") || day.get(j).equals("4")) {
                                serverday.add(String.valueOf(4));
                            } else if (day.get(j).equalsIgnoreCase("Friday") || day.get(j).equalsIgnoreCase("Fredag") || day.get(j).equals("5")) {
                                serverday.add(String.valueOf(5));
                            } else if (day.get(j).equalsIgnoreCase("Saturday") || day.get(j).equalsIgnoreCase("Lördag") || day.get(j).equals("6")
                            ) {
                                serverday.add(String.valueOf(6));
                            } else if (day.get(j).equalsIgnoreCase("Sunday") || day.get(j).equalsIgnoreCase("Söndag") || day.get(j).equals("7")) {
                                serverday.add(String.valueOf(7));
                            }
                        }

                    } else {

                    }
*/

                }

                mAdapter.notifyDataSetChanged();


            }
        });

    }

    public void initViews() {

        choosedataandtimme = findViewById(R.id.opencalender);
        spinnerview = findViewById(R.id.spinnerview);
        done = findViewById(R.id.done);
        add = findViewById(R.id.Add);
        savenotification = findViewById(R.id.savenotification);

        alarmlist_recycler = (RecyclerView) findViewById(R.id.alrmlist_recycler);

        mAdapter = new AlarmListAdpter(getApplicationContext(), alarmlist, this


                   /* new ViewListener() {
                        @Override
                        public void onClick(int position, View view) {

                            int id = view.getId();
                            alarmlist.remove(position);
                            if (day.size() > 0) {
                                day.remove(position);
                                time.remove(position);

                            }
                            mAdapter.notifyDataSetChanged();


                        }
                    }*/


        );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        alarmlist_recycler.setLayoutManager(mLayoutManager);
        alarmlist_recycler.setItemAnimator(new DefaultItemAnimator());
        alarmlist_recycler.setAdapter(mAdapter);

    }

    public void savealarm( ArrayList<String> time) {
        {
//            alarmlist.add(choosedataandtimme.getText().toString());
            serverday.clear();
            day.clear();
            time.clear();


            for (int i = 0; i < alarmlist.size(); i++) {

                String dayandtime[] = alarmlist.get(i).split(" ");
                day.add(dayandtime[0]);
                time.add(dayandtime[1]);
            }
            if (day != null || day.size() > 0) {


                for (int j = 0; j < day.size(); j++) {

                    if (day.get(j).equalsIgnoreCase("Monday") || day.get(j).equalsIgnoreCase("Måndag") || day.get(j).equals("1")) {
                        serverday.add(String.valueOf(1));
                    } else if (day.get(j).equalsIgnoreCase("Tuesday") || day.get(j).equalsIgnoreCase("Tisdag") || day.get(j).equals("2")) {
                        serverday.add(String.valueOf(2));
                    } else if (day.get(j).equalsIgnoreCase("Wednesday") || day.get(j).equalsIgnoreCase("Onsdag") || day.get(j).equals("3")) {
                        serverday.add(String.valueOf(3));
                    } else if (day.get(j).equalsIgnoreCase("Thursday") || day.get(j).equalsIgnoreCase("Torsdag") || day.get(j).equals("4")) {
                        serverday.add(String.valueOf(4));
                    } else if (day.get(j).equalsIgnoreCase("Friday") || day.get(j).equalsIgnoreCase("Fredag") || day.get(j).equals("5")) {
                        serverday.add(String.valueOf(5));
                    } else if (day.get(j).equalsIgnoreCase("Saturday") || day.get(j).equalsIgnoreCase("Lördag") || day.get(j).equals("6")
                    ) {
                        serverday.add(String.valueOf(6));
                    } else if (day.get(j).equalsIgnoreCase("Sunday") || day.get(j).equalsIgnoreCase("Söndag") || day.get(j).equals("7")) {
                        serverday.add(String.valueOf(7));
                    }
                }

            } else {

            }


        }

        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Call<Message> call = apiService.setalarm(MySharedPreferenceClass.getMyUserId(this), serverday, time);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {


                if (response.body() != null) {
                    Log.e("response", response.body().toString());
                    hideDialog();
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {

                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();


                    } else {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    hideDialog();

                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                hideDialog();
                Log.v(TAG, t.toString());
                System.out.println("Errormessage..." + t);

                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void initializeSpinner() {
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("00:00");
        list.add("00:30");
        list.add("01:00");
        list.add("01:30");
        list.add("02:00");
        list.add("02:30");
        list.add("03:00");
        list.add("03:30");
        list.add("04:00");
        list.add("04:30");
        list.add("05:00");
        list.add("05:30");
        list.add("06:00");
        list.add("06:30");
        list.add("07:00");
        list.add("07:30");
        list.add("08:00");
        list.add("08:30");
        list.add("09:00");
        list.add("09:30");
        list.add("10:00");
        list.add("10:30");
        list.add("11:00");
        list.add("11:30");
        list.add("12:00");
        list.add("12:30");
        list.add("13:00");
        list.add("13:30");
        list.add("14:00");
        list.add("14:30");
        list.add("15:00");
        list.add("15:30");
        list.add("16:00");
        list.add("16:30");
        list.add("17:00");
        list.add("17:30");
        list.add("18:00");
        list.add("18:30");
        list.add("19:00");
        list.add("19:30");
        list.add("20:00");
        list.add("20:30");
        list.add("21:00");
        list.add("21:30");
        list.add("22:00");
        list.add("22:30");
        list.add("23:00");
        list.add("23:30");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinnertextview2, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Toast.makeText(getApplicationContext(),"Position"+ position, Toast.LENGTH_SHORT).show();

                alarmtime = spinner2.getSelectedItem().toString();

                //alarmlist.add(alarmname);
                //mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });


        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list1 = new ArrayList<String>();

        list1.add(getResources().getString(R.string.monday));
        list1.add(getResources().getString(R.string.tuesday));
        list1.add(getResources().getString(R.string.wednesday));
        list1.add(getResources().getString(R.string.thursday));
        list1.add(getResources().getString(R.string.friday));
        list1.add(getResources().getString(R.string.saturday));
        list1.add(getResources().getString(R.string.sunday));

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                R.layout.spinnertextview2, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner1.setAdapter(dataAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Toast.makeText(getApplicationContext(),"Position"+ position, Toast.LENGTH_SHORT).show();

                alarmday = spinner1.getSelectedItem().toString();

                //alarmlist.add(alarmname);
                //mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }


    public void getNotificationDetail() {


        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));


        Call<GetNotiDetail> call = apiService.getNotificationDetail(map);
        call.enqueue(new Callback<GetNotiDetail>() {
            @Override
            public void onResponse(Call<GetNotiDetail> call, Response<GetNotiDetail> response) {


                if (response.body() != null) {
                    hideDialog();
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {
                        if (response.body().day != null || response.body().day.size() ==0) {

//                            Toast.makeText(TimerSettings.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < response.body().day.size(); i++) {
                                alarmlist.add(response.body().day.get(i) + " " + response.body().time.get(i));
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        else {

                        }


                    } else {
                        hideDialog();


                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();


                    }
                } else {
                    hideDialog();

                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<GetNotiDetail> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());
                System.out.println("ErrorDat.."+t.toString());

                Toast.makeText(getApplicationContext(), "Connection Time Out", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(int position, View view) {
        int id = view.getId();

        alarmlist.remove(position);
       /* day.remove(position);
        serverday.remove(position);
        time.remove(position);*/
     /*   if (day.size() > 0) {
            serverday.remove(position);
            time.remove(position);

        }*/
        mAdapter.notifyDataSetChanged();
    }
}


