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
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.api.Register;
import com.EsyDigi.esyDigi.utility.Util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends ParentActivity {
    EditText username, password, email, displayname, confirmPassword;
    Button btnSubmit;
    TextView signin;
    boolean isAllConditionFulfilled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_actiivity);

        initviews();
    }

    public void initviews() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.signuppassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        email = findViewById(R.id.signupemail);
        displayname = findViewById(R.id.displayname);
        btnSubmit = findViewById(R.id.signupsubmit);
        signin = findViewById(R.id.reg_signIn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                register();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), LoginActivity.class);
                startActivity(in);


            }
        });

    }


    public void register() {


        isAllConditionFulfilled = Util.checkTextViewValidation(username, "Ange användarnamn. / Please enter username.")
                && Util.checkTextViewValidation(password, "Vänligen skriv in ett lösenord. / Please enter password.")
                && Util.checkTextViewValidation(email, "Ange giltigt e-postmeddelande. / Please enter valid email.")
                && Util.isEmailValid(email)
                && Util.checkTextViewValidation(confirmPassword,"Ange bekräfta lösenord. / Please enter confirm password.")

        ;


        if (!isAllConditionFulfilled) {
            if (Util.view_final != null) {

                Util.view_final.requestFocus();
            }


            return;
        }

        if (password.getText().toString().equals(confirmPassword.getText().toString())) {

            proceed(username.getText().toString(), password.getText().toString(), email.getText().toString(), displayname.getText().toString());
        } else {
            Toast.makeText(this, "Lösenordet stämmer inte med det bekräftade lösenordet. / Password doesn't match with confirm password", Toast.LENGTH_LONG).show();
            return;
        }


    }

    public void proceed(String username, String password, String email, String displayname) {

        showDialog();
        BasicRequest apiService =
                BasicBuilder.getClient().create(BasicRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("email", email);
//        map.put("display_name", username);
        map.put("language", "swedish");
        //map.put("licensenumber", " ");
        //map.put("validity", " ");


        Call<Register> call = apiService.registration(map);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {


                if (response.isSuccessful()) {

                    String status = response.body().status;
                    if (status.equalsIgnoreCase("ok")) {

                        hideDialog();
                        Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(in);
                        finish();


//                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();

                    }


                } else {
                    try {
                        hideDialog();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

                Log.e(TAG, t.toString());
                hideDialog();
                showMessageDialog(null, t, null);

            }
        });


    }

}
