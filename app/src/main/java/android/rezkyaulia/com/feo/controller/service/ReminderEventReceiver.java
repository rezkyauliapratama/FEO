package android.rezkyaulia.com.feo.controller.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.rezkyaulia.com.feo.utility.Constant;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import timber.log.Timber;

/**
 * Created by Mutya Nayavashti on 09/03/2017.
 */

public class ReminderEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 7;

    public void setupAlarm(Context context) {

        Timber.e("ReminderEventReceiver !!!!!");
        PreferencesManager.getInstance().setReminder(true);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context, Constant.getInstance().FLAG_ALARM);

//        if(Build.VERSION.SDK_INT < 23){
//            if(Build.VERSION.SDK_INT >= 19){
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
//                        Common.getDailyActivityReminder(),
//                        alarmIntent);
//
//                alarmManager2.setExact(AlarmManager.RTC_WAKEUP,
//                        Common.getDailyActivityReminderByWeekly(),
//                        alarmIntent2);
//
//            }
//            else{
//                alarmManager.set(AlarmManager.RTC_WAKEUP,
//                        Common.getDailyActivityReminder(),
//                        alarmIntent);
//
//                alarmManager2.set(AlarmManager.RTC_WAKEUP,
//                        Common.getDailyActivityReminderByWeekly(),
//                        alarmIntent2);
//            }
//        }
//        else{
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
//                    Common.getDailyActivityReminder(),
//                    alarmIntent);
//
//            alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
//                    Common.getDailyActivityReminderByWeekly(),
//                    alarmIntent2);
//        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                Utils.getInstance().getWarningAlarm(),
                AlarmManager.INTERVAL_DAY,
                alarmIntent);


// Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String category = intent.getType();
        Intent serviceIntent = null;
        Timber.e("onReceive !!!!");
//        PreferencesManager.getInstance().setReminder(false);
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
            serviceIntent = ReminderIntentService.createIntentStartNotificationService(context, category);
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
            serviceIntent = ReminderIntentService.createIntentDeleteNotification(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }


    private static PendingIntent getStartPendingIntent(Context context, String category) {
        Intent intent = new Intent(context, ReminderEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        intent.setType(category);
        return PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, ReminderEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public boolean isStarted(Context context) {
        Intent intent = new Intent(context, ReminderEventReceiver.class);//the same as up
        return (PendingIntent.getBroadcast(context, 1001,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);
    }
}