package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManageNotificationTbl {

    private Facade facade;


    private NotificationTblDao dao;

    ManageNotificationTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getNotificationTblDao();
    }

    public long add(NotificationTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<NotificationTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<NotificationTbl> getAll() {
        return dao.queryBuilder().list();
    }

    public NotificationTbl get(long id) {
        return dao.queryBuilder().where(NotificationTblDao.Properties.NotificationId.eq(id)).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(NotificationTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
