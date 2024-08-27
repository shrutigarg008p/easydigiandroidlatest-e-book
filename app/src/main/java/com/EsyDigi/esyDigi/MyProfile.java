package com.EsyDigi.esyDigi;

import android.content.Intent;
import android.os.Bundle;

import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.GetProfileInfo;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

  public class MyProfile extends ParentActivity {
    EditText email, password, displayname, licensenumber, Validity;
    TextView usernameValue;
    Button updatedProfileSubmit,changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        initViews();
        getProfileInfo();

        updatedProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(MyProfile.this, R.string.pleasEnter, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    myProfileUpdateclick();
                }

            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepass =  new Intent(MyProfile.this,ChangePassword.class);
                startActivity(changepass);
            }
        });
    }

    public void initViews() {

        usernameValue = findViewById(R.id.usernameValue);
        email = (EditText) findViewById(R.id.signupemail);
        //password = (EditText)findViewById(R.id.displayname);
        displayname = findViewById(R.id.displayname);
        licensenumber = findViewById(R.id.licensenumber);
        Validity = findViewById(R.id.Validity);
        updatedProfileSubmit = findViewById(R.id.profilesubmit);
        changepassword = findViewById(R.id.changepassword);


    }

    public void setProfile(String name, String emailname, String display, String license, String validity) {

        usernameValue.setText(  name);
        email.setText(emailname);
        displayname.setText(display);
        licensenumber.setText(license);
        Validity.setText(validity);


    }

    public void getProfileInfo() {


        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));


        Call<GetProfileInfo> call = apiService.getProfileInfo(map);
        call.enqueue(new Callback<GetProfileInfo>() {
            @Override
            public void onResponse(Call<GetProfileInfo> call, Response<GetProfileInfo> response) {


                if (response.body() != null) {
                    Log.e("response", response.body().toString());
                    hideDialog();
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {
                        setProfile(response.body().username, response.body().email, response.body().displayname, response.body().license, response.body().validity);


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
            public void onFailure(Call<GetProfileInfo> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void myProfileUpdateclick() {
        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));
        map.put("user_email",email.getText().toString());
        map.put("display_name",displayname.getText().toString());
        map.put("licensenumber","");
        map.put("validity","");



        Call<Message> call = apiService.updateProfileInfo(map);
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
                Log.e(TAG, t.toString());

                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void changePasswordClick() {


    }


}
