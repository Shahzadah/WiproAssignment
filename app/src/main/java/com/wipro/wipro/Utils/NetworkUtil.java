package com.wipro.wipro.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wipro.wipro.application.App;

/**
 * Utility class for Network status
 */
public class NetworkUtil {

    /**
     * This method will give you the Network status..
     *
     * @return : Network Status
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }
}