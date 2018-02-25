package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTblDao;
import android.rezkyaulia.com.feo.utility.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManageSubscriptionTbl {

    private Facade facade;


    private SubscriptionTblDao dao;

    ManageSubscriptionTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getSubscriptionTblDao();
    }

    public long add(SubscriptionTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<SubscriptionTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public long updateBySubId(SubscriptionTbl subscriptionTbl){
        SubscriptionTbl oriSubsTbl = dao.queryBuilder().where(SubscriptionTblDao.Properties.SubscriptionId.eq(subscriptionTbl.getSubscriptionId())).limit(1).unique();

        subscriptionTbl.setId(oriSubsTbl.getId());
        return dao.insertOrReplace(subscriptionTbl);
    }
    public List<SubscriptionTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public SubscriptionTbl getActiveSubscription(long userId){
        Timber.e("userId : "+userId);
        SubscriptionTbl subscriptionTbl = dao.queryBuilder().where(SubscriptionTblDao.Properties.UserId.eq(userId)).orderDesc(SubscriptionTblDao.Properties.SubscriptionId).limit(1).unique();

        if (subscriptionTbl != null){
            boolean isSubscribe = checkActiveSubscription(subscriptionTbl);
            if(isSubscribe){
                return  subscriptionTbl;
            }else{
                return null;
            }
        }

        return null;

    }


    public SubscriptionTbl getPaymentSubscription(long userId){
        SubscriptionTbl subscriptionTbl = dao.queryBuilder().where(SubscriptionTblDao.Properties.UserId.eq(userId)).orderDesc(SubscriptionTblDao.Properties.SubscriptionId).limit(1).unique();

        if (subscriptionTbl != null){
            boolean isSubscribe = checkPaymentSubscription(subscriptionTbl);
            if(isSubscribe){
                return  subscriptionTbl;
            }else{
                return null;
            }
        }

        return null;

    }

    public boolean checkActiveSubscription(SubscriptionTbl subscriptionTbl){
        boolean isTrue = false;

        if (subscriptionTbl.getActiveFlag() == 1){
            Timber.e("subscriptionTbl.getActiveFlag() == 1");
            Date startTime = Utils.getInstance().time().parseDate(subscriptionTbl.getSubscriptionStartTimestamp());
            Date endTime = Utils.getInstance().time().parseDate(subscriptionTbl.getSubscriptionEndTimestamp());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            Date nowTime = calendar.getTime();

            if (startTime != null && endTime != null){
                Timber.e("nowTime : "+Utils.getInstance().time().getUserFriendlyDateTimeString(nowTime));
                Timber.e("startTime != null && endTime != null");
                if (startTime.getTime() <= nowTime.getTime() && endTime.getTime() >= nowTime.getTime()){
                    isTrue = true;
                }else{
                    isTrue = false;
                }
            }else{
                isTrue = true;
            }

        }else{
            isTrue = false;
        }

        return isTrue;
    }


    public boolean checkPaymentSubscription(SubscriptionTbl subscriptionTbl){
        boolean isTrue = false;

        if (subscriptionTbl.getPaymentFlag() == 1){
            Date startTime = Utils.getInstance().time().parseDate(subscriptionTbl.getSubscriptionStartTimestamp());
            Date endTime = Utils.getInstance().time().parseDate(subscriptionTbl.getSubscriptionEndTimestamp());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            Date nowTime = calendar.getTime();

            if (startTime.getTime() <= nowTime.getTime() && endTime.getTime() >= nowTime.getTime()){
                isTrue = true;
            }else{
                isTrue = false;
            }
        }else{
            isTrue = false;
        }

        return isTrue;
    }

    public SubscriptionTbl get(long id) {
        return dao.queryBuilder().where(SubscriptionTblDao.Properties.SubscriptionId.eq(id)).unique();
    }

    public SubscriptionTbl getNewest(){
        return  dao.queryBuilder().where(SubscriptionTblDao.Properties.PaymentFlag.eq(1)).orderDesc(SubscriptionTblDao.Properties.Id).limit(1).unique();
    }
    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(SubscriptionTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
