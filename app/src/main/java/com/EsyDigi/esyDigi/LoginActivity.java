package com.EsyDigi.esyDigi;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;


import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.Interface.ApiResponse;
import com.EsyDigi.esyDigi.api.API;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.LoginData;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.api.UserDatum;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;
import com.EsyDigi.esyDigi.utility.ConnectionDetector;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Response;

import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUESTLOGIN;

public class LoginActivity extends ParentActivity implements ApiResponse {

    EditText edtuserName, edtuserPassword;
    Button btnSubmit;
    TextView tvlostPassword, tvsignUp;
    String username, password;
    String deviceId, pushtoken;
    private static int TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activtiy);
        initviews();
        setonclick();

        String serial = Build.SERIAL;
        String android_id = android.provider.Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        deviceId = serial + android_id;
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
                        //Toast.makeText(LoginActivity.this, pushtoken, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void initviews() {
        edtuserName = findViewById(R.id.useremail);
        edtuserPassword = findViewById(R.id.userpassword);
        btnSubmit = findViewById(R.id.submit);
        tvlostPassword = findViewById(R.id.lostPassword);
        tvsignUp = findViewById(R.id.signUp);

    }

    public void setonclick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();  // used for user Login  10/07/2018
            }
        });


        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(in);
      }
        });

        tvlostPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(in);

            }
        });


    }

    public void Login() {
        username = edtuserName.getText().toString();
        password = edtuserPassword.getText().toString();
        if (username.length() > 0 && password.length() > 0) {
            //checkInternetConnection();
            login(username, password);

        } else if (username.length() == 0 && password.length() == 0) {
            Toast.makeText(this, "Ange användarnamn. / Please enter username.", Toast.LENGTH_LONG).show();
            //Snackbar.make(coordinatorLayout,"Please Enter username and password", Snackbar.LENGTH_LONG).show();


        } else if (username.length() == 0) {
            Toast.makeText(this, "Ange användarnamn. / Please enter username.", Toast.LENGTH_LONG).show();
            //Snackbar.make(coordinatorLayout,"Please Enter username and password", Snackbar.LENGTH_LONG).show();
        } else if (password.length() == 0) {
            Toast.makeText(this, "Vänligen skriv in ett lösenord. / Please enter password.", Toast.LENGTH_LONG).show();
            //Snackbar.make(coordinatorLayout,"Please Enter username and password", Snackbar.LENGTH_LONG).show();
        }

    }

    void login(String username, final String password) {


        showDialog();
        BasicRequest apiService = BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("deviceId", deviceId);
        map.put("pushtoken", pushtoken);
        map.put("devicetype", "Android");
        System.out.println("pushtoken...."+pushtoken);
        System.out.println("deviceId...."+deviceId);
        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();


                API.LOGIN(map, this,REQUESTLOGIN);
            } else {
                Toast.makeText(this, "Connection TimeOut", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.out.println("Exception data:" + e);
        }



       /* Call<LoginData> call = apiService.login(map);
        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {


                if (response.body() != null) {
                    Log.e("response",response.body().toString());
                    hideDialog();
                    System.out.println("Response..."+response.body().message);
                    Log.d( TAG, "Login " + response.body());
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {


                        MySharedPreferenceClass.setcookie(getApplicationContext(), response.body().cookie);
                        MySharedPreferenceClass.setMyUserName(getApplicationContext(), response.body().user.username);
                        MySharedPreferenceClass.setEmail(getApplicationContext(), response.body().user.email);
                        MySharedPreferenceClass.setDisplayname(getApplicationContext(), response.body().user.displayname);
                        MySharedPreferenceClass.setMyUserId(getApplicationContext(),response.body().user.id.toString());
                        MySharedPreferenceClass.setLanguageName(getApplicationContext(),response.body().user.prefferedlanguage.toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        hideDialog();

                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();


                    }
                } else {
                    hideDialog();

                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public void onSuccess(Response res,int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.message(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Throwable res,int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessJson(Object res,int requestCode) {
        hideDialog();
        if (requestCode == REQUESTLOGIN ) {
            final LoginData body = (LoginData) res;
            if (body != null) {
                Log.e("response", body.toString());
                hideDialog();
//            System.out.println("Response..."+body.message);
//            Log.d( TAG, "Login " + response.body());
                //userId=response.body().id+"";
                if (body.getStatus().equalsIgnoreCase("ok")) {
//                MySharedPreferenceClass.setcookie(getApplicationContext(), body.get);
                    final UserDatum user = body.getUser();

                    MySharedPreferenceClass.setMyUserName(getApplicationContext(), user.getUsername());
                    MySharedPreferenceClass.setEmail(getApplicationContext(), user.getEmail());
                    MySharedPreferenceClass.setDisplayname(getApplicationContext(), user.getDisplayname());
                    MySharedPreferenceClass.setMyUserId(getApplicationContext(), user.getId().toString());
                    MySharedPreferenceClass.setLanguageName(getApplicationContext(), user.getPrefferedlanguage().toString());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            changeLangu();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();


                        }
                    }, TIME_OUT);

              /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();*/


                } else {
                    hideDialog();
                    Toast.makeText(LoginActivity.this, body.getMessage(), Toast.LENGTH_LONG).show();


                }
            } else {
                hideDialog();
                Toast.makeText(LoginActivity.this, body.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
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

