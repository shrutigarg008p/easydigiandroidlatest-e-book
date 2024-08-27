package com.EsyDigi.esyDigi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FormsNew extends AppCompatActivity {
   private TextView permit,courselink,driverlink;
    EditText learnervalid,learnervalid1,learnervalid2,learnervalid3;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            /*  SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");*/
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String selectedDate = df.format(myCalendar.getTime());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forms);

        permit = findViewById(R.id.permitlink);
        courselink = findViewById(R.id.course_link);
        driverlink = findViewById(R.id.driver_link);
        learnervalid =findViewById(R.id.learnervalidity);
        learnervalid1 =findViewById(R.id.valid1);
        learnervalid2 =findViewById(R.id.valid2);
        learnervalid3 =findViewById(R.id.valid3);


     date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };
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
        new DatePickerDialog(FormsNew.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    });

        learnervalid1.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                hideSoftKeyboard(v);
                new DatePickerDialog(FormsNew.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                return false;
            }
        });

        learnervalid2.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                hideSoftKeyboard(v);
                new DatePickerDialog(FormsNew.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                return false;
            }
        });

        learnervalid3.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                hideSoftKeyboard(v);
                new DatePickerDialog(FormsNew.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                return false;
            }
        });



    }
    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
