package com.EsyDigi.esyDigi;

import android.content.Intent;
import android.os.Bundle;

import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.Message;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;
import com.EsyDigi.esyDigi.utility.Util;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class ChangePassword extends ParentActivity {

    Button submitchange;
    boolean isAllConditionFulfilled;
    EditText current_password, new_password, repeat_new_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        intiViews();
        submitchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllConditionFulfilled = Util.checkTextViewValidation(current_password,
                        getString(R.string.CurrentPasswrd))
                        && Util.checkTextViewValidation(new_password, getString(R.string.NewPasswrd))
                        && Util.checkTextViewValidation(repeat_new_password, getString(R.string.Confirm));


                if (!isAllConditionFulfilled) {
                    if (Util.view_final != null) {

                        Util.view_final.requestFocus();
                    }


                    return;
                }


                if (new_password.getText().toString().equals(repeat_new_password.getText().toString())) {

                    submitnewpassword();
                } else {
                    Toast.makeText(v.getContext(), getString(R.string.NewConfirm), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void intiViews() {


        submitchange = findViewById(R.id.submitPasswordChange);
        current_password = findViewById(R.id.currentpass);
        new_password = findViewById(R.id.newpass);
        repeat_new_password = findViewById(R.id.confirmpass);


    }

    public void submitnewpassword() {
        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", MySharedPreferenceClass.getMyUserId(this));
        map.put("current_password", current_password.getText().toString());
        map.put("new_password", new_password.getText().toString());
        map.put("repeat_new_password", repeat_new_password.getText().toString());

        Call<Message> call = apiService.changepassword(map);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {


                if (response.body() != null) {
                    Log.e("response", response.body().toString());
                    hideDialog();
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {
                        MySharedPreferenceClass.clear(getApplicationContext());
                        Intent in = new Intent(ChangePassword.this,LoginActivity.class);
                        startActivity(in);
                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();


                    } else {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();






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

}
