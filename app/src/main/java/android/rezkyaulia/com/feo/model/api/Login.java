package android.rezkyaulia.com.feo.model.api;

import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 12/26/2017.
 */

public class Login {
    List<UserTbl> userTbls;
    List<SubscriptionTbl> subscriptionTbls;
    List<LibraryTbl> libraryTbls;
    List<ScoreTbl> scoreTbls;
    List<NotificationTbl> notificationTbls;

    public Login() {
    }

    public List<UserTbl> getUserTbls() {
        return userTbls;
    }

    public void setUserTbls(List<UserTbl> userTbls) {
        this.userTbls = userTbls;
    }

    public List<SubscriptionTbl> getSubscriptionTbls() {
        return subscriptionTbls;
    }

    public void setSubscriptionTbls(List<SubscriptionTbl> subscriptionTbls) {
        this.subscriptionTbls = subscriptionTbls;
    }

    public List<LibraryTbl> getLibraryTbls() {
        return libraryTbls;
    }

    public void setLibraryTbls(List<LibraryTbl> libraryTbls) {
        this.libraryTbls = libraryTbls;
    }

    public List<ScoreTbl> getScoreTbls() {
        return scoreTbls;
    }

    public void setScoreTbls(List<ScoreTbl> scoreTbls) {
        this.scoreTbls = scoreTbls;
    }

    public List<NotificationTbl> getNotificationTbls() {
        return notificationTbls;
    }

    public void setNotificationTbls(List<NotificationTbl> notificationTbls) {
        this.notificationTbls = notificationTbls;
    }
}
