package com.EsyDigi.esyDigi.api;








import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "status",
            "day",
            "time",
            "message"
    })
    public class GetNotiDetail {

        @JsonProperty("status")
        public String status;
        @JsonProperty("day")
        public List<String> day = null;
        @JsonProperty("time")
        public List<String> time = null;
        @JsonProperty("message")
        public String message;

    }

