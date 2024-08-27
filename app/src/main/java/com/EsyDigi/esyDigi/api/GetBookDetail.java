package com.EsyDigi.esyDigi.api;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetBookDetail {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private List<BookDatum> data = null;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<BookDatum> getData() {
            return data;
        }

        public void setData(List<BookDatum> data) {
            this.data = data;
        }

    }






