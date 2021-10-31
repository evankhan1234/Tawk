package com.tawk.framework.mvvm.utils;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public  class Util {
    public static int pxToDp(int px) {
        return (int)(px / Resources.getSystem().getDisplayMetrics().density);
    }
    public static boolean isNetworkConnected(Context activity) {
        Boolean data=false;
        ConnectivityManager manager = (ConnectivityManager)activity. getSystemService(CONNECTIVITY_SERVICE);

        //For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        if (!is3g && !isWifi)
        {
            data = false;
        }
        else
        {
            data = true;

        }
        return data;
    }

}
