package com.EsyDigi.esyDigi.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




public class BookDatum {

    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("epub_link")
    @Expose
    private String epubLink;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEpubLink() {
        return epubLink;
    }

    public void setEpubLink(String epubLink) {
        this.epubLink = epubLink;
    }

}

