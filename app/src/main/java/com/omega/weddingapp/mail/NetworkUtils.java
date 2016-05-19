package com.omega.weddingapp.mail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

public class NetworkUtils {

    /**
     * @param context
     * @return
     */
    public static boolean isNetworkConnectionOn(Context context) {

        boolean mobileFlag = false, wifiFlag = false;
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        State mobile = connManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState();

        State wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();

        if (mobile == NetworkInfo.State.CONNECTED
                || mobile == NetworkInfo.State.CONNECTING) {
            mobileFlag = true;
        }
        if (wifi == NetworkInfo.State.CONNECTED
                || wifi == NetworkInfo.State.CONNECTING) {
            wifiFlag = true;
        }

        if (wifiFlag == true || mobileFlag == true) {
            return true;
        }

        return false;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
