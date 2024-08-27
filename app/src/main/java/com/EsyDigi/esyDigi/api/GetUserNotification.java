package com.EsyDigi.esyDigi.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserNotification {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DatumUserNotification> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DatumUserNotification> getData() {
        return data;
    }

    public void setData(List<DatumUserNotification> data) {
        this.data = data;
    }
}
