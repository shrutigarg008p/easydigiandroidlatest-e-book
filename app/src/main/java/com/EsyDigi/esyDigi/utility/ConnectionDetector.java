package com.EsyDigi.esyDigi.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/*
 * FileName : ConnectionDetector.java
 * Dependencies : Internet
 * Description : Check Internet Connection.
 * Classes : ConnectionDetector.java
 */
public class ConnectionDetector {


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = null;
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isInternet = connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnectedOrConnecting();
        if (isInternet) {
            int wifilevel = getWifiLevel(context);
            if (wifilevel < -70) {
                //   Toast.makeText(context, "Network Signal is Weak", Toast.LENGTH_LONG).show();
            }
        }
        return isInternet;
    }



    public static int getWifiLevel(Context context) {
        int level = 0;
        if (HelperClass.getNetworkInfo(context).getType() == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            level = wifiInfo.getRssi();
        } else if (HelperClass.getNetworkInfo(context).getType() == ConnectivityManager.TYPE_MOBILE) {
            level = -50;


        }
        return level;
    }


    /**
     * @param ctx context of calling Activity
     * @return If internet connection is available then return true other wise false
     */
    public static boolean isConnectingToInternet(Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
