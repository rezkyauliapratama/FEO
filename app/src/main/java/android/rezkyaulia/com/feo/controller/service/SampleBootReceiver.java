package android.rezkyaulia.com.feo.controller.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by TVA INTERN 02 on 18/4/2017.
 */

public class SampleBootReceiver extends BroadcastReceiver {
    ReminderEventReceiver alarm = new ReminderEventReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            if (!alarm.isStarted(context))
                alarm.setupAlarm(context);
        }
    }
}