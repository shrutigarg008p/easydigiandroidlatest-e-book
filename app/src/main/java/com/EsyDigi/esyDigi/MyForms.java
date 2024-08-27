package com.EsyDigi.esyDigi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.GetDriverDetail;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;
import com.EsyDigi.esyDigi.utility.Util;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MyForms extends ParentActivity {
    private TextView permit, courselink, driverlink, savedetail;
    private EditText etdate, etInstructorComment, etInstructorComment2, etInstructorComment3, etInstructorComment4, etInstructorComment5, etVEhicleawareness, etVmanoevring, etenvironment, etroadtraffic, etscanning, etsafetymargins, etadapting, etspeed;
    private CheckBox checkBoxDriving, checkBoxSlippery, checkBoxSafetychecks, checkBoxParking, checkBoxHillStarts, checkBoxReversing, checkBoxButtonControl,
            checkBoxBraking, checkBoxResidential, checkBoxPedestrian, checkBoxChanging, checkBoxStreet, checkBoxSignal, checkBoxRoundabout, checkBoxPassing,
            checkBoxEntering, checkBoxDrivingNarrow, checkBoxTurning, checkBoxTurningRight, checkBoxOvertaking, checkBoxMotorways, checkBoxTurningAround,
            checkBoxRailway, checkBoxUnprotected, checkBoxFollowingSigns, checkBoxRoadworkAreas;


    String[][] totalinfo = new String[3][3];


    //private ArrayList<ArrayList<String>> totalinfo = new ArrayList<ArrayList<String>>();
    // private ArrayList<AArrayList<String>> totalinfo3 = new ArrayList<ArrayList<String>>();
    private ArrayList<String> info1 = new ArrayList<>();
    private ArrayList<String> info2 = new ArrayList<>();
    private ArrayList<String> info3 = new ArrayList<>();
    private ArrayList<String> special_condition = new ArrayList<>();
    private ArrayList<String> vehicle_handeling = new ArrayList<>();
    private ArrayList<String> country_roads = new ArrayList<>();
    private ArrayList<String> built_up_areas = new ArrayList<>();
    private ArrayList<String> the_instructor_overall_assesment = new ArrayList<>();
    private ArrayList<String> builtup_areas_and_country_roads = new ArrayList<>();

    String learnerDate;


    private ArrayList<String>[] totalinfo2 = new ArrayList[3];


    private EditText learnervalid, learnervalid1, learnervalid2, learnervalid3, accompanyvalidity, drivername1, drivername2,
            drivername3, approv1, approv2, approv3;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            learnerDate = df.format(myCalendar.getTime());
            learnervalid.setText(learnerDate);

        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            learnerDate = df.format(myCalendar.getTime());
            etdate.setText(learnerDate);

        }
    };
    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            learnerDate = df.format(myCalendar.getTime());
            learnervalid1.setText(learnerDate);

        }
    };
    DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            learnerDate = df.format(myCalendar.getTime());
            learnervalid2.setText(learnerDate);

        }
    };
    DatePickerDialog.OnDateSetListener d3 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            learnerDate = df.format(myCalendar.getTime());
            learnervalid3.setText(learnerDate);

        }
    };
    DatePickerDialog.OnDateSetListener accompanydate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            learnerDate = df.format(myCalendar.getTime());
            accompanyvalidity.setText(learnerDate);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_forms);

        permit = findViewById(R.id.permitlink);
        courselink = findViewById(R.id.course_link);
        driverlink = findViewById(R.id.driver_link);
        learnervalid = findViewById(R.id.learnervalidity);
        accompanyvalidity = findViewById(R.id.accompanyvalidity);
        learnervalid1 = findViewById(R.id.valid1);
        learnervalid2 = findViewById(R.id.valid2);
        learnervalid3 = findViewById(R.id.valid3);
        drivername1 = findViewById(R.id.name1);
        drivername2 = findViewById(R.id.name2);

        drivername3 = findViewById(R.id.name3);
        approv1 = findViewById(R.id.approve1);
        approv2 = findViewById(R.id.approve2);
        approv3 = findViewById(R.id.approve3);

        savedetail = findViewById(R.id.savedetail);
        etdate = findViewById(R.id.etdate);
        checkBoxSlippery = findViewById(R.id.checkBoxSlippery);
        checkBoxDriving = findViewById(R.id.checkBoxDriving);
        etInstructorComment = findViewById(R.id.etInstructorComment);

        etInstructorComment2 = findViewById(R.id.etInstructorComment2);
        checkBoxSafetychecks = findViewById(R.id.checkBoxSafetychecks);
        checkBoxParking = findViewById(R.id.checkBoxParking);
        checkBoxHillStarts = findViewById(R.id.checkBoxHillStarts);
        checkBoxReversing = findViewById(R.id.checkBoxReversing);
        checkBoxButtonControl = findViewById(R.id.checkBoxButtonControl);
        checkBoxBraking = findViewById(R.id.checkBoxBraking);
        checkBoxResidential = findViewById(R.id.checkBoxResidential);
        checkBoxPedestrian = findViewById(R.id.checkBoxPedestrian);
        checkBoxChanging = findViewById(R.id.checkBoxChanging);
        checkBoxStreet = findViewById(R.id.checkBoxStreet);
        checkBoxSignal = findViewById(R.id.checkBoxSignal);
        checkBoxRoundabout = findViewById(R.id.checkBoxRoundabout);
        checkBoxPassing = findViewById(R.id.checkBoxPassing);
        etInstructorComment3 = findViewById(R.id.etInstructorComment3);
        checkBoxDrivingNarrow = findViewById(R.id.checkBoxDrivingNarrow);
        checkBoxEntering = findViewById(R.id.checkBoxEntering);
        checkBoxTurning = findViewById(R.id.checkBoxTurning);
        checkBoxTurningRight = findViewById(R.id.checkBoxTurningRight);
        checkBoxOvertaking = findViewById(R.id.checkBoxOvertaking);
        checkBoxMotorways = findViewById(R.id.checkBoxMotorways);
        etInstructorComment4 = findViewById(R.id.etInstructorComment4);
        checkBoxTurningAround = findViewById(R.id.checkBoxTurningAround);
        checkBoxRailway = findViewById(R.id.checkBoxRailway);
        checkBoxUnprotected = findViewById(R.id.checkBoxUnprotected);
        checkBoxFollowingSigns = findViewById(R.id.checkBoxFollowingSigns);
        checkBoxRoadworkAreas = findViewById(R.id.checkBoxRoadworkAreas);
        etInstructorComment5 = findViewById(R.id.etInstructorComment5);


        etVEhicleawareness = findViewById(R.id.etVEhicleawareness);
        etVmanoevring = findViewById(R.id.etVmanoevring);
        etenvironment = findViewById(R.id.etenvironment);
        etroadtraffic = findViewById(R.id.etroadtraffic);
        etscanning = findViewById(R.id.etscanning);
        etsafetymargins = findViewById(R.id.etsafetymargins);
        etadapting = findViewById(R.id.etadapting);
        etspeed = findViewById(R.id.etspeed);


        getDriverDetail();


        savedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isAllConditionFulfilled = Util.checkTextViewValidation(learnervalid, String.valueOf(R.string.please_enetr_driver_date))
                        && Util.checkTextViewValidation(accompanyvalidity, String.valueOf(R.string.Accompnying_validity))
                        && Util.checkTextViewValidation(etdate, String.valueOf(R.string.please_review_date));
                if (learnervalid.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MyForms.this, R.string.please_enetr_driver_date, Toast.LENGTH_SHORT).show();
                }
                else if (accompanyvalidity.getText().toString().isEmpty()){
                    Toast.makeText(MyForms.this, R.string.Accompnying_validity, Toast.LENGTH_SHORT).show();
                }
                else if (etdate.getText().toString().isEmpty()){
                    Toast.makeText(MyForms.this, R.string.please_review_date, Toast.LENGTH_SHORT).show();

                }


                    else {
                    postdriverDetail();
                }

//                if (!isAllConditionFulfilled) {
//                    if (Util.view_final != null) {
//
//                        Util.view_final.requestFocus();
//                    }
//
//
//                    return;
//                }

            }

        });
        permit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.transportstyrelsen.se/sv/vagtrafik/Korkort/ta-korkort/"));
                startActivity(i);

            }
        });
        courselink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.transportstyrelsen.se/sv/vagtrafik/Korkort/handledare/introduktionsutbildning/"));
                startActivity(i);

            }
        });
        learnervalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                new DatePickerDialog(MyForms.this, d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                new DatePickerDialog(MyForms.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        accompanyvalidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                new DatePickerDialog(MyForms.this, accompanydate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        learnervalid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                new DatePickerDialog(MyForms.this, d1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        learnervalid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                new DatePickerDialog(MyForms.this, d2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        learnervalid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                new DatePickerDialog(MyForms.this, d3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    public void postdriverDetail() {
        info1.clear();
        info2.clear();
        info3.clear();
        special_condition.clear();
        vehicle_handeling.clear();
        country_roads.clear();
        built_up_areas.clear();
        the_instructor_overall_assesment.clear();
        builtup_areas_and_country_roads.clear();


        info1.add(0, drivername1.getText().toString());
        info1.add(1, learnervalid1.getText().toString());
        info1.add(2, approv1.getText().toString());


        info2.add(0, drivername2.getText().toString());
        info2.add(1, learnervalid2.getText().toString());
        info2.add(2, approv2.getText().toString());


        info3.add(0, drivername3.getText().toString());
        info3.add(1, learnervalid3.getText().toString());
        info3.add(2, approv3.getText().toString());

        if (checkBoxDriving.isChecked()) {
            special_condition.add(0, "1");

        } else {
            special_condition.add(0, "0");

        }
        if (checkBoxSlippery.isChecked() == true) {
            special_condition.add(1, "1");

        } else {
            special_condition.add(1, "0");

        }


        special_condition.add(2, etInstructorComment.getText().toString().trim());


        if (checkBoxSafetychecks.isChecked() == true) {
            vehicle_handeling.add(0, "1");

        } else {
            vehicle_handeling.add(0, "0");

        }
        if (checkBoxParking.isChecked() == true) {
            vehicle_handeling.add(1, "1");

        } else {
            vehicle_handeling.add(1, "0");

        }

        if (checkBoxReversing.isChecked() == true) {
            vehicle_handeling.add(2, "1");

        } else {
            vehicle_handeling.add(2, "0");

        }

        if (checkBoxHillStarts.isChecked() == true) {
            vehicle_handeling.add(3, "1");

        } else {
            vehicle_handeling.add(3, "0");

        }

        if (checkBoxButtonControl.isChecked() == true) {
            vehicle_handeling.add(4, "1");

        } else {
            vehicle_handeling.add(4, "0");

        }
        if (checkBoxBraking.isChecked() == true) {
            vehicle_handeling.add(5, "1");

        } else {
            vehicle_handeling.add(5, "0");

        }
        vehicle_handeling.add(6, etInstructorComment2.getText().toString().trim());

        if (checkBoxResidential.isChecked() == true) {
            built_up_areas.add(0, "1");

        } else {
            built_up_areas.add(0, "0");

        }
        if (checkBoxPedestrian.isChecked() == true) {
            built_up_areas.add(1, "1");

        } else {
            built_up_areas.add(1, "0");

        }
        if (checkBoxChanging.isChecked() == true) {
            built_up_areas.add(2, "1");

        } else {
            built_up_areas.add(2, "0");

        }

        if (checkBoxStreet.isChecked() == true) {
            built_up_areas.add(3, "1");

        } else {
            built_up_areas.add(3, "0");

        }
        if (checkBoxSignal.isChecked() == true) {
            built_up_areas.add(4, "1");

        } else {
            built_up_areas.add(4, "0");

        }
        if (checkBoxRoundabout.isChecked() == true) {
            built_up_areas.add(5, "1");

        } else {
            built_up_areas.add(5, "0");

        }
        if (checkBoxPassing.isChecked() == true) {
            built_up_areas.add(6, "1");

        } else {
            built_up_areas.add(6, "0");

        }

        built_up_areas.add(7, etInstructorComment3.getText().toString().trim());

        if (checkBoxDrivingNarrow.isChecked() == true) {
            country_roads.add(0, "1");

        } else {
            country_roads.add(0, "0");

        }
        if (checkBoxEntering.isChecked() == true) {
            country_roads.add(1, "1");

        } else {
            country_roads.add(1, "0");

        }
        if (checkBoxTurning.isChecked() == true) {
            country_roads.add(2, "1");

        } else {
            country_roads.add(2, "0");

        }

        if (checkBoxTurningRight.isChecked() == true) {
            country_roads.add(3, "1");

        } else {
            country_roads.add(3, "0");

        }
        if (checkBoxOvertaking.isChecked() == true) {
            country_roads.add(4, "1");

        } else {
            country_roads.add(4, "0");

        }
        if (checkBoxMotorways.isChecked() == true) {
            country_roads.add(5, "1");

        } else {
            country_roads.add(5, "0");

        }


        country_roads.add(6, etInstructorComment4.getText().toString().trim());

        if (checkBoxTurningAround.isChecked() == true) {
            builtup_areas_and_country_roads.add(0, "1");

        } else {
            builtup_areas_and_country_roads.add(0, "0");

        }
        if (checkBoxRailway.isChecked() == true) {
            builtup_areas_and_country_roads.add(1, "1");

        } else {
            builtup_areas_and_country_roads.add(1, "0");

        }
        if (checkBoxUnprotected.isChecked() == true) {
            builtup_areas_and_country_roads.add(2, "1");

        } else {
            builtup_areas_and_country_roads.add(2, "0");

        }

        if (checkBoxFollowingSigns.isChecked() == true) {
            builtup_areas_and_country_roads.add(3, "1");

        } else {
            builtup_areas_and_country_roads.add(3, "0");

        }
        if (checkBoxRoadworkAreas.isChecked() == true) {
            builtup_areas_and_country_roads.add(4, "1");

        } else {
            builtup_areas_and_country_roads.add(4, "0");

        }


        builtup_areas_and_country_roads.add(5, etInstructorComment5.getText().toString().trim());
        the_instructor_overall_assesment.add(0, etVEhicleawareness.getText().toString());
        the_instructor_overall_assesment.add(1, etVmanoevring.getText().toString());
        the_instructor_overall_assesment.add(2, etenvironment.getText().toString());
        the_instructor_overall_assesment.add(3, etroadtraffic.getText().toString());
        the_instructor_overall_assesment.add(4, etscanning.getText().toString());
        the_instructor_overall_assesment.add(5, etsafetymargins.getText().toString());
        the_instructor_overall_assesment.add(6, etadapting.getText().toString());
        the_instructor_overall_assesment.add(7, etspeed.getText().toString());


        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);


        Call<Message> call = apiService.postdriverdetail(MySharedPreferenceClass.getMyUserId(this), learnervalid.getText().toString(), accompanyvalidity.getText().toString(), info1, info2, info3, etdate.getText().toString(),
                special_condition, vehicle_handeling, country_roads, built_up_areas, the_instructor_overall_assesment, builtup_areas_and_country_roads);

        //  Call<Message> call = apiService.postdriverdetail(map);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {


                if (response.body() != null) {
                    Log.e("response", response.body().toString());
                    hideDialog();
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {
                        getDriverDetail();
                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();


                    } else {
                        hideDialog();


                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();


                    }
                } else {
                    hideDialog();

                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());

                // Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getDriverDetail() {
        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));


        Call<GetDriverDetail> call = apiService.getDriverDetail(map);
        call.enqueue(new Callback<GetDriverDetail>() {
            @Override
            public void onResponse(Call<GetDriverDetail> call, Response<GetDriverDetail> response) {


                if (response.body() != null) {
                    hideDialog();

                    Log.e("Response is--->", response.body().toString());
                    if (response.body().status.equalsIgnoreCase("ok")) {
                        initializeInfo(
                                response.body().learnerValidDate,
                                response.body().accompanyingValidDate,
                                response.body().alternateDriver1, response.body().alternateDriver2, response.body().alternateDriver3, response.body().special_condition,
                                response.body().vehicle_handeling, response.body().country_roads, response.body().built_up_areas, response.body().builtup_areas_and_country_roads,
                                response.body().the_instructor_overall_assesment, response.body().course_review_date

                        );

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
            public void onFailure(Call<GetDriverDetail> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());

                //Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();result = "com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "special_condition" (class com.unyscape.esydiginew.api.GetDriverDetail), not marked as ignorable (6 known properties: "status", "alternate_driver1", "alternate_driver2", "alternate_driver3", "accompanying_valid_date", "learner_valid_date"])\n at [Source: (okhttp3.ResponseBody$BomAwareReader); line: 1, column: 130] (through reference chain: com.unyscape.esydiginew.api.GetDriverDetail["special_condition"])"
            }
        });


    }

    public void initializeInfo(String learner_valid_date, String accompnaying_date, List<String> alternate1, List<String> alternate2, List<String> alternate3, List<String> special_condition, List<String> vehicle_handling,
                               List<String> country_roads, List<String> built_up_areas, List<String> builtup_areas_and_country_roads, List<String> the_instructor_overall_assesment, String course_review_date) {
        learnervalid.setText(learner_valid_date);
        accompanyvalidity.setText(accompnaying_date);

        if (alternate1.size() > 0) {
            if (alternate1.get(0).length() > 0) {
                if (alternate1.get(0).length() > 1) {
                    learnervalid1.setText(alternate1.get(1));
                }
                if (alternate1.get(0).length() > 0) {
                    drivername1.setText(alternate1.get(0));
                }
                if (alternate1.get(0).length() > 2) {
                    approv1.setText(alternate1.get(2));
                }
            }

            if (alternate2.get(1).length() > 0) {
                if (alternate2.get(1).length() > 1) {
                    learnervalid2.setText(alternate2.get(1));

                }
                if (alternate2.get(1).length() > 0) {
                    drivername2.setText(alternate2.get(0));
                }
                if (alternate2.get(1).length() > 2) {
                    approv2.setText(alternate2.get(2));
                }

            }

            if (alternate3.get(2).length() > 0) {
                if (alternate3.get(2).length() > 1) {
                    learnervalid3.setText(alternate3.get(1));
                }
                if (alternate3.get(2).length() > 0) {
                    drivername3.setText(alternate3.get(0));
                }
                if (alternate3.get(2).length() > 2) {
                    approv3.setText(alternate3.get(2));
                }
            }
            etdate.setText(course_review_date);
            if (special_condition != null && special_condition.size() > 0) {
                if (special_condition.get(0) != null) {
                    if (special_condition.get(0).equalsIgnoreCase("0")) {
                        checkBoxDriving.setChecked(false);
                    }
                    if (special_condition.get(0).equalsIgnoreCase("1")) {
                        checkBoxDriving.setChecked(true);
                    }
                }
                if (special_condition.get(1) != null) {
                    if (special_condition.get(1).equalsIgnoreCase("0")) {
                        checkBoxSlippery.setChecked(false);
                    }
                    if (special_condition.get(1).equalsIgnoreCase("1")) {
                        checkBoxSlippery.setChecked(true);
                    }
                }
                if (special_condition.get(2) != null) {
                    etInstructorComment.setText(special_condition.get(2));
                }
            }


            if (vehicle_handling != null && vehicle_handling.size() > 0) {
                if (vehicle_handling.get(0) != null) {
                    if (vehicle_handling.get(0).equalsIgnoreCase("0")) {
                        checkBoxSafetychecks.setChecked(false);
                    }
                    if (vehicle_handling.get(0).equalsIgnoreCase("1")) {
                        checkBoxSafetychecks.setChecked(true);
                    }
                }
                if (vehicle_handling.get(1) != null) {
                    if (vehicle_handling.get(1).equalsIgnoreCase("0")) {
                        checkBoxParking.setChecked(false);
                    }
                    if (vehicle_handling.get(1).equalsIgnoreCase("1")) {
                        checkBoxParking.setChecked(true);
                    }
                }
                if (vehicle_handling.get(2) != null) {
                    if (vehicle_handling.get(2).equalsIgnoreCase("0")) {
                        checkBoxReversing.setChecked(false);
                    }
                    if (vehicle_handling.get(2).equalsIgnoreCase("1")) {
                        checkBoxReversing.setChecked(true);

                    }
                }

                if (vehicle_handling.get(3) != null) {
                    if (vehicle_handling.get(3).equalsIgnoreCase("0")) {
                        checkBoxHillStarts.setChecked(false);
                    }
                    if (vehicle_handling.get(3).equalsIgnoreCase("1")) {
                        checkBoxHillStarts.setChecked(true);

                    }
                }

                if (vehicle_handling.get(4) != null) {
                    if (vehicle_handling.get(4).equalsIgnoreCase("0")) {
                        checkBoxButtonControl.setChecked(false);
                    }
                    if (vehicle_handling.get(4).equalsIgnoreCase("1")) {
                        checkBoxButtonControl.setChecked(true);

                    }
                }

                if (vehicle_handling.get(5) != null) {
                    if (vehicle_handling.get(5).equalsIgnoreCase("0")) {
                        checkBoxBraking.setChecked(false);
                    }
                    if (vehicle_handling.get(5).equalsIgnoreCase("1")) {
                        checkBoxBraking.setChecked(true);

                    }
                }
                if (vehicle_handling.get(6) != null) {
                    etInstructorComment2.setText(vehicle_handling.get(6));
                }
            }


            if (built_up_areas != null && built_up_areas.size() > 0) {
                if (built_up_areas.get(0) != null) {
                    if (built_up_areas.get(0).equalsIgnoreCase("0")) {
                        checkBoxResidential.setChecked(false);
                    }
                    if (built_up_areas.get(0).equalsIgnoreCase("1")) {
                        checkBoxResidential.setChecked(true);
                    }
                }
                if (built_up_areas.get(1) != null) {
                    if (built_up_areas.get(1).equalsIgnoreCase("0")) {
                        checkBoxPedestrian.setChecked(false);
                    }
                    if (built_up_areas.get(1).equalsIgnoreCase("1")) {
                        checkBoxPedestrian.setChecked(true);
                    }
                }
                if (built_up_areas.get(2) != null) {
                    if (built_up_areas.get(2).equalsIgnoreCase("0")) {
                        checkBoxChanging.setChecked(false);
                    }
                    if (built_up_areas.get(2).equalsIgnoreCase("1")) {
                        checkBoxChanging.setChecked(true);

                    }
                }

                if (built_up_areas.get(3) != null) {
                    if (built_up_areas.get(3).equalsIgnoreCase("0")) {
                        checkBoxStreet.setChecked(false);
                    }
                    if (built_up_areas.get(3).equalsIgnoreCase("1")) {
                        checkBoxStreet.setChecked(true);

                    }
                }

                if (built_up_areas.get(4) != null) {
                    if (built_up_areas.get(4).equalsIgnoreCase("0")) {
                        checkBoxSignal.setChecked(false);
                    }
                    if (built_up_areas.get(4).equalsIgnoreCase("1")) {
                        checkBoxSignal.setChecked(true);

                    }
                }

                if (built_up_areas.get(5) != null) {
                    if (built_up_areas.get(5).equalsIgnoreCase("0")) {
                        checkBoxRoundabout.setChecked(false);
                    }
                    if (built_up_areas.get(5).equalsIgnoreCase("1")) {
                        checkBoxRoundabout.setChecked(true);

                    }

                    if (built_up_areas.get(6) != null) {
                        if (built_up_areas.get(6).equalsIgnoreCase("0")) {
                            checkBoxPassing.setChecked(false);
                        }
                        if (built_up_areas.get(6).equalsIgnoreCase("1")) {
                            checkBoxPassing.setChecked(true);

                        }
                    }
                    if (built_up_areas.get(7) != null) {
                        etInstructorComment3.setText(built_up_areas.get(7));
                    }
                }

            }
        }


        if (country_roads != null && country_roads.size() > 0) {
            if (country_roads.get(0) != null) {
                if (country_roads.get(0).equalsIgnoreCase("0")) {
                    checkBoxDrivingNarrow.setChecked(false);
                }
                if (country_roads.get(0).equalsIgnoreCase("1")) {
                    checkBoxDrivingNarrow.setChecked(true);
                }
            }
            if (country_roads.get(1) != null) {
                if (country_roads.get(1).equalsIgnoreCase("0")) {
                    checkBoxEntering.setChecked(false);
                }
                if (country_roads.get(1).equalsIgnoreCase("1")) {
                    checkBoxEntering.setChecked(true);
                }
            }
            if (country_roads.get(2) != null) {
                if (country_roads.get(2).equalsIgnoreCase("0")) {
                    checkBoxTurning.setChecked(false);
                }
                if (country_roads.get(2).equalsIgnoreCase("1")) {
                    checkBoxTurning.setChecked(true);

                }
            }

            if (country_roads.get(3) != null) {
                if (country_roads.get(3).equalsIgnoreCase("0")) {
                    checkBoxTurningRight.setChecked(false);
                }
                if (country_roads.get(3).equalsIgnoreCase("1")) {
                    checkBoxTurningRight.setChecked(true);

                }
            }

            if (country_roads.get(4) != null) {
                if (country_roads.get(4).equalsIgnoreCase("0")) {
                    checkBoxOvertaking.setChecked(false);
                }
                if (country_roads.get(4).equalsIgnoreCase("1")) {
                    checkBoxOvertaking.setChecked(true);

                }
            }

            if (country_roads.get(5) != null) {
                if (country_roads.get(5).equalsIgnoreCase("0")) {
                    checkBoxMotorways.setChecked(false);
                }
                if (country_roads.get(5).equalsIgnoreCase("1")) {
                    checkBoxMotorways.setChecked(true);

                }


                if (country_roads.get(6) != null) {
                    etInstructorComment4.setText(country_roads.get(6));
                }
            }

        }

        if (builtup_areas_and_country_roads != null && builtup_areas_and_country_roads.size() > 0) {
            if (builtup_areas_and_country_roads.get(0) != null) {
                if (builtup_areas_and_country_roads.get(0).equalsIgnoreCase("0")) {
                    checkBoxTurningAround.setChecked(false);
                }
                if (builtup_areas_and_country_roads.get(0).equalsIgnoreCase("1")) {
                    checkBoxTurningAround.setChecked(true);
                }
            }
            if (builtup_areas_and_country_roads.get(1) != null) {
                if (builtup_areas_and_country_roads.get(1).equalsIgnoreCase("0")) {
                    checkBoxRailway.setChecked(false);
                }
                if (builtup_areas_and_country_roads.get(1).equalsIgnoreCase("1")) {
                    checkBoxRailway.setChecked(true);
                }
            }
            if (builtup_areas_and_country_roads.get(2) != null) {
                if (builtup_areas_and_country_roads.get(2).equalsIgnoreCase("0")) {
                    checkBoxUnprotected.setChecked(false);
                }
                if (builtup_areas_and_country_roads.get(2).equalsIgnoreCase("1")) {
                    checkBoxUnprotected.setChecked(true);


                }
            }

            if (builtup_areas_and_country_roads.get(3) != null) {
                if (builtup_areas_and_country_roads.get(3).equalsIgnoreCase("0")) {
                    checkBoxFollowingSigns.setChecked(false);
                }
                if (builtup_areas_and_country_roads.get(3).equalsIgnoreCase("1")) {
                    checkBoxFollowingSigns.setChecked(true);

                }
            }

            if (builtup_areas_and_country_roads.get(4) != null) {
                if (country_roads.get(4).equalsIgnoreCase("0")) {
                    checkBoxRoadworkAreas.setChecked(false);
                }
                if (builtup_areas_and_country_roads.get(4).equalsIgnoreCase("1")) {
                    checkBoxRoadworkAreas.setChecked(true);

                }
            }


            if (builtup_areas_and_country_roads.get(5) != null) {
                etInstructorComment5.setText(builtup_areas_and_country_roads.get(5));
            }
        }


        if (the_instructor_overall_assesment != null && the_instructor_overall_assesment.size() > 0) {
            if (the_instructor_overall_assesment.get(0) != null) {
                etVEhicleawareness.setText(the_instructor_overall_assesment.get(0));
            }

        }
        if (the_instructor_overall_assesment.get(1) != null) {
            etVmanoevring.setText(the_instructor_overall_assesment.get(1));
        }
        if (the_instructor_overall_assesment.get(2) != null) {
            etenvironment.setText(the_instructor_overall_assesment.get(2));
        }

        if (the_instructor_overall_assesment.get(3) != null) {
            etroadtraffic.setText(the_instructor_overall_assesment.get(3));
        }

        if (the_instructor_overall_assesment.get(4) != null) {
            etscanning.setText(the_instructor_overall_assesment.get(4));
        }


        if (the_instructor_overall_assesment.get(5) != null) {
            etsafetymargins.setText(the_instructor_overall_assesment.get(5));
        }

        if (the_instructor_overall_assesment.get(6) != null) {
            etadapting.setText(the_instructor_overall_assesment.get(6));
        }
        if (the_instructor_overall_assesment.get(7) != null) {
            etspeed.setText(the_instructor_overall_assesment.get(7));
        }

    }


       /* for (int i = 0; i < info.size(); i++) {

                if (i == 0) {
                    for (int j = 0; j < info.get(i).size(); j++) {
                    learnervalid1.setText(info.get(i).get(j));
                    drivername1.setText(info.get(i).get(j));
                    approv1.setText(info.get(i).get(j));
                    }

                } else if (i == 1) {
                    for (int j = 0; j < info.get(i).size(); j++) {
                        learnervalid2.setText(info.get(i).get(j));
                        drivername2.setText(info.get(i).get(j));
                        approv2.setText(info.get(i).get(j));
                    }

                } else if (i == 2) {
                    for (int j = 0; j < info.get(i).size(); j++) {

                        learnervalid3.setText(info.get(i).get(j));
                        drivername3.setText(info.get(i).get(j));
                        approv3.setText(info.get(i).get(j));
                    }

                }





        }
*/


    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
