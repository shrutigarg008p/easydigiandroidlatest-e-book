package com.EsyDigi.esyDigi.api;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "prefferedlanguage"
})
public class Slectedlanguageclass {

    @JsonProperty("status")
    public String status;
    @JsonProperty("prefferedlanguage")
    public Boolean prefferedlanguage;

}
