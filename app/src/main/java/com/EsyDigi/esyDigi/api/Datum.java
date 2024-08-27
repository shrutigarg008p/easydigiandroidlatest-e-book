package com.EsyDigi.esyDigi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "epub_link",
        "project_id",
        "project_name",
        "status"
})
public class Datum {

    @JsonProperty("id")
    public String id;
    @JsonProperty("epub_link")
    public String epubLink;
    @JsonProperty("project_id")
    public String projectId;
    @JsonProperty("project_name")
    public String projectName;
    @JsonProperty("status")
    public String status;

}