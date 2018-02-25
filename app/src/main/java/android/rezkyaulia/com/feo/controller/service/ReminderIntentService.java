package android.rezkyaulia.com.feo.controller.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.activity.LoginActivity;
import android.rezkyaulia.com.feo.controller.activity.MainActivity;
import android.rezkyaulia.com.feo.model.api.Login;
import android.rezkyaulia.com.feo.utility.Constant;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import timber.log.Timber;

/**
 * Created by Mutya Nayavashti on 09/03/2017.
 */
public class ReminderIntentService extends IntentService {

    private static final int NOTIFICATION_DAILY =3001;
    private static final int NOTIFICATION_WEEKLY = 3002;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public ReminderIntentService() {
        super(ReminderIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context, String category) {
        Intent intent = new Intent(context, ReminderIntentService.class);
        intent.setAction(ACTION_START);
        intent.setType(category);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, ReminderIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.e("onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                Timber.e("onHandleIntent, started handling a notification event");
                String category = intent.getType();
                Timber.e("Category : " + category);

                if (category.equals(Constant.getInstance().FLAG_ALARM)) {
                    processStartNotification(NOTIFICATION_DAILY);

                }

            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification(int notification_type) {
        // Do something. For example, fetch fresh data from backend to create a rich notification?
        Timber.e("process notificationya");

        String title = "";
        String message = "";

        if (notification_type == NOTIFICATION_DAILY) {
            title = "Speed reading activity";
            message = "Hi Friends, let's play speed reading !";
        } /*else {
            title = "Daily Activites";
            message = "Dear panellist, please be reminded to submit your weekly purchases. You will be rewarded with extra points upon completion.";
        }*/
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_feo_notification_icon)
                .setLargeIcon(BitmapFactory.decodeResource(
                        this.getResources(),
                        R.mipmap.ic_launcher
                ));

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                notification_type,
                new Intent(this, LoginActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        ReminderEventReceiver receiver = new ReminderEventReceiver();
        builder.setDeleteIntent(receiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notification_type, builder.build());
    }
}