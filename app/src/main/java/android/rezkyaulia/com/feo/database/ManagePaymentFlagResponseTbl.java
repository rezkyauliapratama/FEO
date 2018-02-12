package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.PaymentFlagResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentFlagResponseTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManagePaymentFlagResponseTbl {

    private Facade facade;


    private PaymentFlagResponseTblDao dao;

    ManagePaymentFlagResponseTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getPaymentFlagResponseTblDao();
    }

    public long add(PaymentFlagResponseTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<PaymentFlagResponseTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<PaymentFlagResponseTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public PaymentFlagResponseTbl get(long id) {
        return dao.queryBuilder().where(PaymentFlagResponseTblDao.Properties.PaymentFlagResponseId.eq(id)).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(PaymentFlagResponseTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
