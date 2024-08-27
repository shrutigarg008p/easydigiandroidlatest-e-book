package com.EsyDigi.esyDigi.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "id",
        "username",
        "email",
        "displayname",
        "license",
        "validity"
})
public class GetProfileInfo {

    @JsonProperty("status")
    public String status;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("email")
    public String email;
    @JsonProperty("displayname")
    public String displayname;
    @JsonProperty("license")
    public String license;
    @JsonProperty("validity")
    public String validity;



}
