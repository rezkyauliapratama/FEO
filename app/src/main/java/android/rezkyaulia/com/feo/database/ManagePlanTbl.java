package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.PlanTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManagePlanTbl {

    private Facade facade;


    private PlanTblDao dao;

    ManagePlanTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getPlanTblDao();
    }

    public long add(PlanTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<PlanTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<PlanTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public PlanTbl get(long id) {
        return dao.queryBuilder().where(PlanTblDao.Properties.PlanId.eq(id)).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(PlanTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
