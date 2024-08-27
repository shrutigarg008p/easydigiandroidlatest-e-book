package com.EsyDigi.esyDigi;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.EsyDigi.esyDigi.BasicRequests.BasicRequest;
import com.EsyDigi.esyDigi.api.BasicBuilder;
import com.EsyDigi.esyDigi.api.Forgotpassword;
import com.EsyDigi.esyDigi.api.ParentActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ForgotPasswordActivity extends ParentActivity {
    Button btnSubmit;
    EditText edtuserEmail;
    TextView forgotsignin, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initviews();
        setonclick();
    }

    public void initviews() {
        edtuserEmail = findViewById(R.id.forgotemail);

        btnSubmit = findViewById(R.id.submit);
        forgotsignin = findViewById(R.id.logIn);
        signUp = findViewById(R.id.signUp);


    }

    public void setonclick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtuserEmail.getText().toString().equalsIgnoreCase("")) {

                    forgotPassword();
//                    edtuserEmail.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Ange användarnamn. / Please enter username.", Toast.LENGTH_LONG).show();
                }


            }
        });
        forgotsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), LoginActivity.class);  // used for user Login  10/07/2018
                startActivity(in);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), SignUpActivity.class);  // used for user Login  10/07/2018
                startActivity(in);
            }
        });
    }

    public void forgotPassword() {
        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("username", edtuserEmail.getText().toString().trim());


        Call<Forgotpassword> call = apiService.forgotpassword(map);
        call.enqueue(new Callback<Forgotpassword>() {
            @Override
            public void onResponse(Call<Forgotpassword> call, Response<Forgotpassword> response) {


                if (response.body() != null) {
                    hideDialog();
                    //Log.d( TAG, "Login " + response.body().id);
                    //userId=response.body().id+"";
                    if (response.body().status.equalsIgnoreCase("ok")) {

                        Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_LONG).show();
                        Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(in);

                    } else {
                        hideDialog();

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                } else {
                    hideDialog();


                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }


            }


            @Override
            public void onFailure(Call<Forgotpassword> call, Throwable t) {
                hideDialog();
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Connection Timeout", Toast.LENGTH_LONG).show();
            }
        });
    }


}