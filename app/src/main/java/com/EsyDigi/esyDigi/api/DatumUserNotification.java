package com.EsyDigi.esyDigi.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumUserNotification {
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("notification_time")
    @Expose
    private String notificationTime;
    @SerializedName("notification_day")
    @Expose
    private String notificationDay;
    @SerializedName("language_id")
    @Expose
    private String languageId;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationDay() {
        return notificationDay;
    }

    public void setNotificationDay(String notificationDay) {
        this.notificationDay = notificationDay;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
