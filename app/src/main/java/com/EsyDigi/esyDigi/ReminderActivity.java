package com.EsyDigi.esyDigi;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.EsyDigi.esyDigi.Interface.ApiResponse;
import com.EsyDigi.esyDigi.adpters.UserNotificationAdapter;
import com.EsyDigi.esyDigi.api.API;
import com.EsyDigi.esyDigi.api.DatumUserNotification;
import com.EsyDigi.esyDigi.api.GetUserNotification;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;
import com.EsyDigi.esyDigi.utility.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_REMINDER;

public class ReminderActivity extends ParentActivity implements ApiResponse {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserNotificationAdapter userNotificationAdapter;
    private List<DatumUserNotification> datumNotification = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        initUi();
    }

    private void initUi() {
        recyclerView = findViewById(R.id.recyclerViewReminder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        callgetNotificationApi();


    }

    private void callgetNotificationApi() {

        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
              showDialog();
                API.register(MySharedPreferenceClass.getMyUserId(this),this,REQUEST_REMINDER);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(Response res,int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Throwable res,int requestCode) {
        hideDialog();
        if (res != null) {
            Toast.makeText(this, res.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessJson(Object res,int requestCode) {
        hideDialog();
        if (requestCode == REQUEST_REMINDER) {
            final GetUserNotification body = (GetUserNotification) res;


            if (body != null) {
                if (body.getStatus().equalsIgnoreCase("ok")) {
                    List<DatumUserNotification> data = body.getData();
                    if (data.size() > 0) {

                        datumNotification = body.getData();
                        userNotificationAdapter = new UserNotificationAdapter(this, datumNotification);
                        recyclerView.setAdapter(userNotificationAdapter);
                        userNotificationAdapter.notifyDataSetChanged();

                    } else {

//                        Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                    }

                } else {


//                    SnackBar.shortDuration(getActivity(),((AllBookingResponseModel) res).getMessage());
                }
            } else {

            }
        }
    }
}
