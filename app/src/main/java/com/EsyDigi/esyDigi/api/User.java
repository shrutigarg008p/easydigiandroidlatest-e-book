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
        "id",
        "username",
        "nicename",
        "email",
        "url",
        "registered",
        "displayname",
        "firstname",
        "lastname",
        "nickname",
        "description",
        "capabilities",
        "avatar",
        "prefferedlanguage"
})
public class User {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("nicename")
    public String nicename;
    @JsonProperty("email")
    public String email;
    @JsonProperty("url")
    public String url;
    @JsonProperty("registered")
    public String registered;
    @JsonProperty("displayname")
    public String displayname;
    @JsonProperty("firstname")
    public String firstname;
    @JsonProperty("lastname")
    public String lastname;
    @JsonProperty("nickname")
    public String nickname;
    @JsonProperty("description")
    public String description;
    @JsonProperty("capabilities")
    public String capabilities;
    @JsonProperty("avatar")
    public Object avatar;
    @JsonProperty("prefferedlanguage")
    public String prefferedlanguage;

}
