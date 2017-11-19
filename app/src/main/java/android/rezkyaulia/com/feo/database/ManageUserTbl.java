package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.database.entity.UserTblDao;
import android.rezkyaulia.com.feo.database.entity.UserTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class ManageUserTbl {

    private Facade facade;


    private UserTblDao dao;

    ManageUserTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getUserTblDao();
    }

    public long add(UserTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<UserTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<UserTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public UserTbl get() {
        List<UserTbl> userTbls =
                dao.queryBuilder().limit(1).list();

        return userTbls.size() == 0 ? null : userTbls.get(0);
    }

    public UserTbl get(long id) {
        return dao.queryBuilder().where(UserTblDao.Properties.UserKey.eq(id)).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(UserTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }

}
