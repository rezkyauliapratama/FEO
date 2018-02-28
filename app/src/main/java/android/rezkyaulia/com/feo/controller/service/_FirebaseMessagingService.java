package android.rezkyaulia.com.feo.controller.service; /**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.activity.LoginActivity;
import android.rezkyaulia.com.feo.controller.activity.MainActivity;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.model.NotifModel;
import android.rezkyaulia.com.feo.utility.NotificationUtil;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.GeneralSecurityException;

import timber.log.Timber;

public class _FirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.e("From: " + remoteMessage.getFrom());

       /* // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Timber.e("Message data payload: " + remoteMessage.getData());
            sendNotification(new Gson().toJson(remoteMessage.getData()));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Timber.e("Message Notification Body: " + remoteMessage.getNotification().getBody());
//            sendNotification(new Gson().toJson(remoteMessage.getData()));

        }
*/
        Timber.e("Message data payload: " + remoteMessage.getData());

        if (remoteMessage.getData().size() > 0) {
            initNotification(remoteMessage);
        }

//        sendNotification(new Gson().toJson(remoteMessage.getData()));

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    private void initNotification(RemoteMessage remoteMessage){
        Timber.e("remote message : "+new Gson().toJson(remoteMessage.getData()));

        GsonBuilder builder = new GsonBuilder();
        Gson mGson = builder.create();

        NotifModel notifModel = mGson.fromJson(new Gson().toJson(remoteMessage.getData()),NotifModel.class);
        Timber.e("notifModel : "+new Gson().toJson(notifModel));

        if (notifModel.getAction() == NotifModel.NOTIF_PAYMENT_SUCCESS ){
            NotificationUtil.getInstance().defaultNotification(_FirebaseMessagingService.this,getString(R.string.payment_success_message_title),getString(R.string.payment_success_message_body));
            RxBus.getInstance().post(notifModel);

        }else if (notifModel.getAction() == NotifModel.NOTIF_PAYMENT_FAILED ){
            NotificationUtil.getInstance().defaultNotification(_FirebaseMessagingService.this,getString(R.string.payment_failed_message_title),getString(R.string.payment_failed_message_body));
            RxBus.getInstance().post(notifModel);

        }
        else if (notifModel.getAction() == NotifModel.NOTIF_MESSAGE){
            NotificationTbl notificationTbl = (NotificationTbl) notifModel;
            notificationTbl.setUserId(Facade.getInstance().getManagerUserTbl().get().getUserId());
            notificationTbl.setFlagRead(0);
            long id = Facade.getInstance().getManageNotificationTbl().add(notificationTbl);
            Timber.e("ID INsert notification : "+id);
            if (id > 0){
                NotificationUtil.getInstance().defaultNotification(_FirebaseMessagingService.this,notificationTbl.getTitle(),notificationTbl.getBody());
                RxBus.getInstance().post(notifModel);
            }
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

}
