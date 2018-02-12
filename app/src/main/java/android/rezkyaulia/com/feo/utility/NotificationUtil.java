package android.rezkyaulia.com.feo.utility;


import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.activity.LoginActivity;
import android.rezkyaulia.com.feo.controller.activity.MainActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;

import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shiburagi on 23/09/2016.
 */

public class NotificationUtil {

    private static final NotificationUtil instance;
    public static final String UPDATE = "Update~>";


    static {
        instance = new NotificationUtil();
    }


    public static NotificationUtil getInstance() {
        return instance;
    }

    public void notifyDownloadProgress(Context context, String label, int id, double progress, double max) {
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.mipmap.ic_launcher
                ))
                .setContentTitle(label);

        if (max > progress) {

            builder.setOngoing(true)
                    .setProgress((int) max, (int) progress, false)
                    .setContentText(getProgressText(progress, max));
        } else
            builder.setContentText(
                    context.getResources().getString(R.string.downloadcomplete)
            );

        try {
            NotificationManager mNotifyManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyManager.notify(id, builder.build());
        } catch (Exception e) {
        }

    }

    public void notifyUploadProgress(Context context, String label, int id, double progress, double max) {
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_file_upload_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.mipmap.ic_launcher
                ))
                .setContentTitle(label);

        if (max > progress) {
            builder.setProgress((int) max, (int) progress, false)
                    .setContentText(getProgressText(progress, max));
        } else
            builder.setContentText(
                    context.getResources().getString(R.string.uploadcomplete)
            );

        NotificationManager mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(id, builder.build());
    }

    public void notifyPushProfileProgress(Context context, String label, int id, double progress, double max) {
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_file_upload_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.mipmap.ic_launcher
                ))
                .setContentTitle(label);

        if (max > progress) {
            builder.setProgress((int) max, (int) progress, false)
                    .setContentText(getProgressPercentageText(progress, max));
        } else
            builder.setContentText(
                    context.getResources().getString(R.string.uploadcomplete)
            );

        NotificationManager mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(id, builder.build());
    }

    private CharSequence getProgressPercentageText(double progress, double max) {
        double percentage = (progress/max)*100;
        String progressText = String.format(Locale.getDefault(),
                "%.1f", percentage).concat("%");
        final SpannableString styledResultText = new SpannableString(progressText);
        styledResultText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                0, progressText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return styledResultText;
    }


    private CharSequence getProgressText(double progress, double max) {
        String progressText = String.format(Locale.getDefault(),
                "%.1f / %.1fMB", progress, max);
        final SpannableString styledResultText = new SpannableString(progressText);
        styledResultText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                0, progressText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return styledResultText;
    }

    public void notify(Context context, String title, String label, int id, boolean b) {
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.mipmap.ic_launcher
                ))
                .setOngoing(b)
                .setContentTitle(title)
                .setContentText(label)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(label));



        NotificationManager mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            mNotifyManager.notify(id, builder.build());
        }catch (Exception e){

        }
    }

    public void cancel(Context context, int id) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }


    public void defaultNotification(Context context, RemoteMessage.Notification notification) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_feo_notification_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                context.getResources(),
                                R.mipmap.ic_launcher
                        ))
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void defaultNotification(Context context, RemoteMessage notification) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_feo_notification_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                context.getResources(),
                                R.mipmap.ic_launcher
                        ))
                        .setContentTitle(notification.getData().get("title"))
                        .setContentText(notification.getData().get("body"))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }


    public void defaultNotification(Context context, String title, String body) {

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_feo_notification_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                context.getResources(),
                                R.mipmap.ic_launcher
                        ))
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }

}
