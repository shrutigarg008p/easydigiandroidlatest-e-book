package com.EsyDigi.esyDigi.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;


import com.EsyDigi.esyDigi.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Response;


/**
 * FileName : HelperClass
 * Description : use in all project
 * Dependencies : server,internet
 */

public class HelperClass {


    /**
     * Get the network info
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    /**
     * set progress dialog before get api response
     * @param context-
     * @return progressDialog
     */
    public static ProgressDialog setProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    /**
     * check blank and empty string
     * @param string-string
     * @return string
     */
    public static String getString(String string) {
        String newString;
        if (TextUtils.isEmpty(string)) {
            newString = "";
        } else {
            newString = string;
        }
        return newString;
    }

    /**
     * convert image bitmap to base 64
     * @param bitmap-
     * @return Base64
     */
    public static String convertImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }


    // RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources(), mBitmap);
//        circularBitmapDrawable.setCircular(true);
//        mEditProfileBinding.imageViewUser.setImageDrawable(circularBitmapDrawable);


    /**
     * Method to getErrorMessage from server
     * @param response-get from server
     * @return -error message
     */
    public static String getErrorMessage(Response response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            if (jObjError.has("message")) {
                return jObjError.getString("message");
            } else if (jObjError.has("messages")) {
                return jObjError.getString("messages");
            } else if (jObjError.has("detail")) {
                return jObjError.getString("detail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "some thing went wrong, please try again";
        //  return getResources().getString(R.string.some_thing_went_wrong);
    }

    /**
     * get FCM token,android id
     * @param context-
     */
//    public static void getDeviceToken(Context context) {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Constant.FCM_TOKEN = refreshedToken;
//        SharedPreferencesUtil.getInstance(context).savePushID(refreshedToken);
//        Constant.ANDROID_ID = DeviceInfo.getAndroidID(context);
//    }

    /**
     * get access token
     * @param context-
     */
    /*public static void getAuthorizationId(Context context) {
        Constant.SESSION_ID = SharedPreferencesUtil.getInstance(context).getSessionID();
    }*/
}