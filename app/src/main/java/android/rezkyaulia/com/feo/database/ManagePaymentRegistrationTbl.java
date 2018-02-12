package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTblDao;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTblDao;

import java.util.List;

import static android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTblDao.Properties.PaymentRegId;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManagePaymentRegistrationTbl {

    private Facade facade;


    private PaymentRegistrationTblDao dao;

    ManagePaymentRegistrationTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getPaymentRegistrationTblDao();
    }

    public long add(PaymentRegistrationTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<PaymentRegistrationTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<PaymentRegistrationTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public PaymentRegistrationTbl get(long id) {
        return dao.queryBuilder().where(PaymentRegId.eq(id)).unique();
    }

    public PaymentRegistrationTbl getBySubsId(long id) {
        return dao.queryBuilder().where(PaymentRegistrationTblDao.Properties.SubscriptionId.eq(id)).orderDesc(PaymentRegId).limit(1).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(PaymentRegistrationTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
