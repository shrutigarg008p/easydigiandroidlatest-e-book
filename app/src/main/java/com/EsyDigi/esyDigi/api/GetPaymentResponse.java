package com.EsyDigi.esyDigi.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPaymentResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private GetPaymentDatum data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetPaymentDatum getData() {
        return data;
    }

    public void setData(GetPaymentDatum data) {
        this.data = data;
    }
}
