package com.EsyDigi.esyDigi;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MainSetting extends ParentActivity {
    SeekBar brightbar;
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;
    private int brightness;
    RadioGroup radioGroup;
    RadioButton langeng, langswid;
    RadioButton languageradioButton;
    Locale myLocale;
    String savedLanguage = "english";
    String currentLanguage = "en", currentLang;
    private String select_lang = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);
        brightbar = findViewById(R.id.brightnessbar);
        radioGroup = findViewById(R.id.radioGroup);
        langeng = findViewById(R.id.radioEnglish);
        langswid = findViewById(R.id.radioSwidish);
        cResolver = getContentResolver();
        LinearLayout notificationsetting = findViewById(R.id.notisetting);
        //getLanguage();

        notificationsetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent in = new Intent(v.getContext(), TimerSettings.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });

        final String savedLanguage = MySharedPreferenceClass.getLanguageName(this);


        if (savedLanguage.equalsIgnoreCase("english")) {

            radioGroup.check(R.id.radioEnglish);


        } else if (savedLanguage.equalsIgnoreCase("swedish") || savedLanguage.equalsIgnoreCase("Swidish")) {


            radioGroup.check(R.id.radioSwidish);
//            changeLangu();
        }

        setBrightness();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                onclickbuttonMethod();
                if (languageradioButton.getText().toString().equalsIgnoreCase("english")) {
                    setLanguage("english");
                    changeLangu();


                } else if (languageradioButton.getText().toString().equals("Swedish") || languageradioButton.getText().toString().equals("Swidish")) {

                    setLanguage("swedish");
                    changeLangu();
//                    setLocale("swidish");


                }
            }
        });
    }

    private void changeLangu() {
        String languageToLoad = select_lang; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        finish();
//        SharedPreference_Main preference = SharedPreference_Main.getInstance(UserDataActivity.this);
//        if (preference.get_session_login()) {
//            startActivity(new Intent(this, MainActivity.class));
//        } else {
//        startService(new Intent(this, Intentservice_Insert_Posts.class));
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);

//        startActivity(new Intent(this, MainSetting.class));

//            Toast.makeText(UserDataActivity.this,"change language", Toast.LENGTH_SHORT).show();

//        }
    }


    public void onclickbuttonMethod() {
        try {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            languageradioButton = (RadioButton) findViewById(selectedId);
            if (selectedId == -1) {
                Toast.makeText(MainSetting.this, "Nothing selected", Toast.LENGTH_SHORT).show();
            } else {
                if (languageradioButton.getText().toString().equals("English")) {
                    setLanguage("english");
                    select_lang = "en";
                    MySharedPreferenceClass.setLanguageName(this, "english");
                    changeLangu();

                } else if (languageradioButton.getText().toString().equals("Swedish")) {
                    setLanguage("swidish");
                    select_lang = "sv";
                    MySharedPreferenceClass.setLanguageName(this, "swidish");
                    changeLangu();

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainSetting.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setBrightness() {

        brightbar.setMax(255);
        float curBrightnessValue = 0;

        try {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            curBrightnessValue = android.provider.Settings.System.getInt(
                    getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        int screen_brightness = (int) curBrightnessValue;
        brightbar.setProgress(screen_brightness);

        brightbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue,
                                          boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do something here,
                // if you want to do anything at the start of
                // touching the seekbar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        progress);
            }
        });

    }


    public void setLanguage(String chooselang) {

        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("prefferedlanguage", chooselang);
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));


        Call<Message> call = apiService.selectlanguage(map);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {


                if (response.body() != null) {

                    Log.e("response", response.body().toString());
                    try {
                        hideDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";

                    if (response.body().status.equalsIgnoreCase("Ok")) {

                        Toast.makeText(MainSetting.this, response.body().message, Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            hideDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();


                    }
                } else {
                    try {
                        hideDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });
    }


}
