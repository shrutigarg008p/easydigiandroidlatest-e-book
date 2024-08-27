package com.EsyDigi.esyDigi;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private String deviceId,pushtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        String serial = Build.SERIAL;
        String android_id = android.provider.Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        deviceId = serial + android_id;
        MySharedPreferenceClass.setDEviceId(getApplicationContext(),deviceId);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            Log.w("Tokenn", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        pushtoken = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.save_note, pushtoken);
                        Log.d("Token---------->", pushtoken);
                        MySharedPreferenceClass.setPUSHTOKEN(getApplicationContext(),pushtoken);
                        System.out.println("pushtoken...."+pushtoken);
                        System.out.println("deviceId...."+deviceId);

                        //Toast.makeText(LoginActivity.this, pushtoken, Toast.LENGTH_SHORT).show();
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (MySharedPreferenceClass.getMyUserName(getApplication()) == null) {

                    Intent i=new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    finish();
                }else{
                    Intent i=new Intent(SplashScreen.this,MainActivity.class);

                    startActivity(i);
                    changeLangu();
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                    finish();
                }






            }
        }, SPLASH_TIME_OUT);


    }
    private void changeLangu() {

        String language = "en";
        if(MySharedPreferenceClass.getLanguageName(getApplicationContext()).equalsIgnoreCase("english")){
            language = "en";
        } else if (MySharedPreferenceClass.getLanguageName(getApplicationContext()).equalsIgnoreCase("swidish") ||MySharedPreferenceClass.getLanguageName(getApplicationContext()).equalsIgnoreCase("swedish") ) {
            language = "sv";
        }


        String languageToLoad = language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        //finish();


        }
    }


