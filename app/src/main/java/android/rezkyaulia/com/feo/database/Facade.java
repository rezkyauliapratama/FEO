package android.rezkyaulia.com.feo.database;


import android.database.Cursor;
import android.rezkyaulia.com.feo.database.entity.DaoSession;


import org.greenrobot.greendao.database.Database;

/**
 * Created by Shiburagi on 07/12/2016.
 */

public class Facade {

    private static Facade instance;
    final DaoSession session;

    public static void init(DaoSession daoSession) {
        instance = new Facade(daoSession);
    }

    public static Facade getInstance() {
        return instance;
    }

    private ManageLibraryTbl manageLibraryTbl;
    private ManageUserTbl manageUserTbl;
    private ManageScoreTbl manageScoreTbl;
    private ManageNotificationTbl manageNotificationTbl;
    private ManagePaymentRegistrationTbl managePaymentRegistrationTbl;
    private ManagePaymentRegistrationResponseTbl managePaymentRegistrationResponseTbl;
    private ManagePaymentFlagTbl managePaymentFlagTbl;
    private ManagePaymentFlagResponseTbl managePaymentFlagResponseTbl;
    private ManageSubscriptionTbl manageSubscriptionTbl;
    private ManagePlanTbl managePlanTbl;

//    private ManagePlan manageSubscriptionTbl;




    Facade(DaoSession daoSession) {
        this.session = daoSession;

        manageLibraryTbl = new ManageLibraryTbl(this);
        manageUserTbl = new ManageUserTbl(this);
        manageScoreTbl = new ManageScoreTbl(this);
        manageNotificationTbl = new ManageNotificationTbl(this);
        managePaymentRegistrationTbl= new ManagePaymentRegistrationTbl(this);
        managePaymentFlagTbl = new ManagePaymentFlagTbl(this);
        managePaymentRegistrationResponseTbl = new ManagePaymentRegistrationResponseTbl(this);
        managePaymentFlagResponseTbl = new ManagePaymentFlagResponseTbl(this);
        manageSubscriptionTbl = new ManageSubscriptionTbl(this);
        managePlanTbl = new ManagePlanTbl(this);

    }

    public DaoSession getDaoSession(){
        return session;
    }

    public ManageLibraryTbl getManageLibraryTbl() {
        return manageLibraryTbl;
    }

    public ManageUserTbl getManagerUserTbl() {
        return manageUserTbl;
    }

    public ManageScoreTbl getManageScoreTbl() {
        return manageScoreTbl;
    }

    public ManageNotificationTbl getManageNotificationTbl() {
        return manageNotificationTbl;
    }

    public ManagePaymentRegistrationTbl getManagePaymentRegistrationTbl() {
        return managePaymentRegistrationTbl;
    }

    public ManagePaymentRegistrationResponseTbl getManagePaymentRegistrationResponseTbl() {
        return managePaymentRegistrationResponseTbl;
    }

    public ManagePaymentFlagTbl getManagePaymentFlagTbl() {
        return managePaymentFlagTbl;
    }

    public ManagePaymentFlagResponseTbl getManagePaymentFlagResponseTbl() {
        return managePaymentFlagResponseTbl;
    }

    public ManageSubscriptionTbl getManageSubscriptionTbl() {
        return manageSubscriptionTbl;
    }

    public ManageUserTbl getManageUserTbl() {
        return manageUserTbl;
    }

    public ManagePlanTbl getManagePlanTbl() {
        return managePlanTbl;
    }
}
