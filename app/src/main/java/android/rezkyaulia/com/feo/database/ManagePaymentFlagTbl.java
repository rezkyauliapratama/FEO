package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.PaymentFlagTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentFlagTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManagePaymentFlagTbl {

    private Facade facade;


    private PaymentFlagTblDao dao;

    ManagePaymentFlagTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getPaymentFlagTblDao();
    }

    public long add(PaymentFlagTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<PaymentFlagTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<PaymentFlagTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public PaymentFlagTbl get(long id) {
        return dao.queryBuilder().where(PaymentFlagTblDao.Properties.PaymentFlagId.eq(id)).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(PaymentFlagTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
