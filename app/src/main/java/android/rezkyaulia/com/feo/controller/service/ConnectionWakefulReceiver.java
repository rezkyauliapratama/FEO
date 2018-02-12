package android.rezkyaulia.com.feo.controller.service;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.app.infideap.stylishwidget.Log;


/**
 * Created by Shiburagi on 4/7/15.
 */

public class ConnectionWakefulReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "ConnWakeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "android.net.conn.CONNECTIVITY_CHANGE":
            case "android.net.wifi.WIFI_STATE_CHANGED":
                executeConnectivityService(context, intent);
                break;
            case "android.location.PROVIDERS_CHANGED":
                break;
            case "com.google.android.c2dm.intent.RECEIVE":
                break;

            default:
                break;
        }

    }


    public void executeConnectivityService(Context context, Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.d(TAG, "Network Change");
        final android.net.NetworkInfo networkInfo = connMgr
                .getActiveNetworkInfo();
        try {
            if (networkInfo != null) {
                int type = networkInfo.getType();

                if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE) {

                }
            }
        } catch (Exception e) {

        } finally {
        }
    }


}
