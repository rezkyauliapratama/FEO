package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManagePaymentRegistrationResponseTbl {

    private Facade facade;


    private PaymentRegistrationResponseTblDao dao;

    ManagePaymentRegistrationResponseTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getPaymentRegistrationResponseTblDao();
    }

    public long add(PaymentRegistrationResponseTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<PaymentRegistrationResponseTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<PaymentRegistrationResponseTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public PaymentRegistrationResponseTbl get(long id) {
        return dao.queryBuilder().where(PaymentRegistrationResponseTblDao.Properties.PaymentRegResponseId.eq(id)).unique();
    }

    public PaymentRegistrationResponseTbl getByRegId(long id) {
        return dao.queryBuilder().where(PaymentRegistrationResponseTblDao.Properties.PaymentRegId.eq(id)).limit(1).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(PaymentRegistrationResponseTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
