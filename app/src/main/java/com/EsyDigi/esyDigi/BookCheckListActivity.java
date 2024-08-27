package com.EsyDigi.esyDigi;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.EsyDigi.esyDigi.Interface.ApiResponse;
import com.EsyDigi.esyDigi.adpters.CheckListAdapter;
import com.EsyDigi.esyDigi.api.API;
import com.EsyDigi.esyDigi.api.DatumGetTopic;
import com.EsyDigi.esyDigi.api.GetTopicDetails;
import com.EsyDigi.esyDigi.api.ParentActivity;
import com.EsyDigi.esyDigi.api.SetTopicModel;
import com.EsyDigi.esyDigi.others.MySharedPreferenceClass;
import com.EsyDigi.esyDigi.utility.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_GETTOPIC;
import static com.EsyDigi.esyDigi.api.ApiRequestCode.REQUEST_SETTOPIC;

public class BookCheckListActivity extends ParentActivity implements ApiResponse,CheckListAdapter.ViewListener3 {
    private RecyclerView recyclerCheckList;
    private CheckListAdapter checkListAdapter;
    List<DatumGetTopic> chapter = new ArrayList<DatumGetTopic>();
    private String projectId = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_check_list);
        recyclerCheckList = findViewById(R.id.recyclerCheckList);
        callgetTopic();

        recyclerCheckList.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCheckList.setLayoutManager(mLayoutManager);
        recyclerCheckList.setItemAnimator(new DefaultItemAnimator());


    }

    private void callgetTopic() {

        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();
                if (MySharedPreferenceClass.getLanguageName(BookCheckListActivity.this).equalsIgnoreCase("swidish") || MySharedPreferenceClass.getLanguageName(BookCheckListActivity.this).equalsIgnoreCase("swedish")

                ) {

                    projectId = String.valueOf(83);
                } else {
                    projectId = String.valueOf(28255);
                }
                API.getTopicDetails(projectId, MySharedPreferenceClass.getMyUserId(BookCheckListActivity.this),BookCheckListActivity.this,REQUEST_GETTOPIC);
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
        if (requestCode == REQUEST_GETTOPIC) {
            final GetTopicDetails body = (GetTopicDetails) res;


            if (body != null) {
                if (body.getStatus().equalsIgnoreCase("ok")) {
                    List<DatumGetTopic> data = body.getData();
                    if (data.size() > 0) {

                        chapter = body.getData();
                        checkListAdapter = new CheckListAdapter(BookCheckListActivity.this, chapter, this);
                        recyclerCheckList.setAdapter(checkListAdapter);
                        checkListAdapter.notifyDataSetChanged();

                    } else {

//                        Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                    }

                } else {


//                    SnackBar.shortDuration(getActivity(),((AllBookingResponseModel) res).getMessage());
                }

            }/*
            }*/

        }
        else if (requestCode == REQUEST_SETTOPIC) {
            final SetTopicModel body2 = (SetTopicModel) res;
            if (body2 != null) {
                if (body2.getStatus().equalsIgnoreCase("ok")) {
//                    Toast.makeText(this, body2.getData(), Toast.LENGTH_SHORT).show();
                    callgetTopic();

                }
            }
        }
    }

    @Override
    public void onClick(int position, View view, boolean status) {
        callsetTopic(position,status);



    }

    private void callsetTopic(int topicID, boolean check) {

        try {
            if (ConnectionDetector.isNetworkAvailable(this)) {
                showDialog();
                if (MySharedPreferenceClass.getLanguageName(BookCheckListActivity.this).equalsIgnoreCase("swidish")

                ) {

                    projectId = String.valueOf(83);
                }
                else {
                    projectId = String.valueOf(28255);
                }
                if (check == true){
                    API.setTopicDetails(Integer.parseInt(chapter.get(topicID).getID()),projectId, String.valueOf(1),MySharedPreferenceClass.getMyUserId(BookCheckListActivity.this),BookCheckListActivity.this,REQUEST_SETTOPIC);
                }
                else {
                    API.setTopicDetails(Integer.parseInt(chapter.get(topicID).getID()),projectId, String.valueOf(0),MySharedPreferenceClass.getMyUserId(BookCheckListActivity.this),BookCheckListActivity.this,REQUEST_SETTOPIC);
                }


            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
