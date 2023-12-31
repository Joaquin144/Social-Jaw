package com.devcommop.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class CommonUtils {
    private CommonUtils() {//aim: disallow instance creation
    }

    public static String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        return currentDateTime;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }

    //todo: Handle => what if there are billion users and any 2 computers generate same id ?
    public static String getAutoId() {
        String uniqueId = UUID.randomUUID().toString();
        String uniqueId_20_Digits = uniqueId.substring(uniqueId.length() - 20);
        return uniqueId_20_Digits;
    }
}
