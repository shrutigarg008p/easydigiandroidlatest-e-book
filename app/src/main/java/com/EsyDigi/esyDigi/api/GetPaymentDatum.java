package com.EsyDigi.esyDigi.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPaymentDatum {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
