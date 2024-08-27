package com.EsyDigi.esyDigi.api;

/**
 * Created by unyscape on 29/05/19.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "message"
})
public class Forgotpassword {

    @JsonProperty("status")
    public String status;
    @JsonProperty("message")
    public String message;

}