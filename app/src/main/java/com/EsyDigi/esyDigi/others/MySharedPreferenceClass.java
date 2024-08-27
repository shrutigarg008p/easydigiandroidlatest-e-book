package com.EsyDigi.esyDigi.others;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferenceClass {

    private static String MY_USERID = "UserID";
    private static String MY_USERNAME = "Name";
    private static String FIRST_NAME = "fname";
    private static String LAST_NAME = "lname";
    private static String STATUS = "status";
    private static String TYPE = "type";
    private static String MAIL_ID = "mailID";
    private static String CONFERENCE_ID = "conferenceID";
    private static String PATIENT_ID = "patientId";
    private static String ADDRESS = "address";
    private static String IMAGE = "image";
    private static String DASHBOARD = "dashboard";
    private static String PROVIDER = "provider";
    private static String NOTI_COUNT = "COUNT";
    private static String CHAT_FLAG = "chat_flag";
    private static String COOKIE = "cookie";

    private static String USERNAME = "username";
    private static String PASSWORD = "password";
    private static String EMAIL = "email";
    private static String DISPLAYNAME = "displayname";
    private static String LICENSENUMBER = "licensenumber";
    private static String VALIDITY = "validity";
    private static String LANGUAGE = "language";
    private static String PUSHTOKEN = "pushtoken";
    private static String DEVICEID = "deviceId";

    public static void setLanguageName(Context context, String value) {


        getPrefs(context).edit().putString(LANGUAGE, value).commit();


    }

    public static String getLanguageName(Context context) {


        return getPrefs(context).getString(LANGUAGE, "English");


    }

    public static boolean getEnglishbookDetail(Context context) {

        return getPrefs(context).getBoolean("isEnglishbookcompletedownloaded", false);


    }

    public static void setIsEnglishbookdetail(Context context, boolean value) {
        // perform validation etc..
        getPrefs(context).edit().putBoolean("isEnglishbookcompletedownloaded", value).commit();
    }


    public static boolean getSwedishbookDetail(Context context) {

        return getPrefs(context).getBoolean("isSwedishbookcompletedownloaded", false);


    }

    public static void setIsSwedishbookdetail(Context context, boolean value) {
        // perform validation etc..
        getPrefs(context).edit().putBoolean("isSwedishbookcompletedownloaded", value).commit();
    }


    public static String getMyUserName(Context context) {

        return getPrefs(context).getString(USERNAME, null);
    }

    public static void setMyUserName(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(USERNAME, value).commit();
    }


    public static String getPUSHTOKEN(Context context) {

        return getPrefs(context).getString(PUSHTOKEN, null);
    }

    public static void setPUSHTOKEN(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(PUSHTOKEN, value).commit();
    }


    public static String getDEviceID(Context context) {

        return getPrefs(context).getString(DEVICEID, null);
    }

    public static void setDEviceId(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(DEVICEID, value).commit();
    }


    public static String getPassword(Context context) {

        return getPrefs(context).getString(PASSWORD, null);
    }

    public static void setPassword(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(PASSWORD, value).commit();

    }


    public static String getDisplayname(Context context) {

        return getPrefs(context).getString(DISPLAYNAME, null);
    }

    public static void setDisplayname(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(DISPLAYNAME, value).commit();


    }

    public static String getLicenseNumber(Context context) {

        return getPrefs(context).getString(LICENSENUMBER, null);
    }

    public static void setLicenseNumber(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(LICENSENUMBER, value).commit();

    }

    public static String getValidity(Context context) {

        return getPrefs(context).getString(VALIDITY, null);
    }

    public static void setValidity(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(VALIDITY, value).commit();

    }


    public static String getEmail(Context context) {

        return getPrefs(context).getString(EMAIL, null);
    }

    public static void setEmail(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(EMAIL, value).commit();

    }

    public static String getNotiCount(Context context) {
        return getPrefs(context).getString(NOTI_COUNT, null);
    }

    public static void setNotiCount(Context context, String notiCount) {
        getPrefs(context).edit().putString(NOTI_COUNT, notiCount).commit();
    }


    @SuppressWarnings("static-access")
    public static SharedPreferences getPrefs(Context context) {


        return context.getSharedPreferences("UserDetails", context.MODE_PRIVATE);
    }

    ///////////////////////////////////////////////////////////
    //////////////For User ID/////////////////////////////////
    ///////////////////////////////////////////////////////////

    public static String getMyUserId(Context context) {

        return getPrefs(context).getString(MY_USERID, null);
    }

    public static void setMyUserId(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(MY_USERID, value).commit();
    }

    public static String getChatFlag(Context context) {
        return getPrefs(context).getString(CHAT_FLAG, null);
    }

    public static void setChatFlag(Context context, String chatFlag) {
        getPrefs(context).edit().putString(CHAT_FLAG, chatFlag).commit();
    }

    ///////////////////////////////////////////////////////////
    //////////////For User Name////////////////////////////////
    ///////////////////////////////////////////////////////////

    public static String getMyNamePref(Context context) {

        return getPrefs(context).getString(MY_USERNAME, null);
    }

    public static void setMyNamePref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(MY_USERNAME, value).commit();
    }

    public static void clear(Context context) {
        getPrefs(context).edit().clear().commit();
    }

    ///////////////////////////////////////////////////////////
    //////////////For User Mail////////////////////////////////
    ///////////////////////////////////////////////////////////

    public static String getMyFirstNamePref(Context context) {

        return getPrefs(context).getString(FIRST_NAME, null);
    }

    public static void setMyFirstNamePref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(FIRST_NAME, value).commit();
    }

    public static void setcookie(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(COOKIE, value).commit();
    }

    public static String getMyLastNamePref(Context context) {

        return getPrefs(context).getString(LAST_NAME, null);
    }

    public static void setMyLastNamePref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(LAST_NAME, value).commit();
    }

    public static String getStatus(Context context) {

        return getPrefs(context).getString(STATUS, null);
    }

    public static void setStatus(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(STATUS, value).commit();
    }

    public static String getMyUserType(Context context) {

        return getPrefs(context).getString(TYPE, null);
    }

    public static void setMyUserType(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(TYPE, value).commit();
    }

    public static String getMyMailId(Context context) {

        return getPrefs(context).getString(MAIL_ID, null);
    }

    public static void setMyMailId(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(MAIL_ID, value).commit();
    }

    public static String getMyConferenceId(Context context) {

        return getPrefs(context).getString(CONFERENCE_ID, null);
    }

    public static void setMyConferenceId(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(CONFERENCE_ID, value).commit();
    }

    public static String getMyPatientId(Context context) {

        return getPrefs(context).getString(PATIENT_ID, null);
    }

    public static void setMyPatientId(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(PATIENT_ID, value).commit();
    }


    public static String getCookie(Context context) {

        return getPrefs(context).getString(COOKIE, null);
    }

    public static String getMyAddress(Context context) {

        return getPrefs(context).getString(ADDRESS, null);
    }

    public static void setMyAddress(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(ADDRESS, value).commit();
    }

    public static String getMyImage(Context context) {

        return getPrefs(context).getString(IMAGE, null);
    }

    public static void setMyImage(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(IMAGE, value).commit();
    }

    public static String getMyDashBoardAvailability(Context context) {

        return getPrefs(context).getString(DASHBOARD, null);
    }

    public static void setMyDashBoardAvailability(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(DASHBOARD, value).commit();
    }

    public static String getProviderID(Context context) {

        return getPrefs(context).getString(PROVIDER, null);
    }

    public static void setProviderID(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(PROVIDER, value).commit();
    }

}