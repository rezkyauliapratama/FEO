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
        return dao.queryBuilder().orderDesc(NotificationTblDao.Properties.CreatedDate).list();
    }

    public NotificationTbl get(long id) {
        return dao.queryBuilder().where(NotificationTblDao.Properties.NotificationId.eq(id)).unique();
    }
    public long countNotSeen(String UserId) {
        return dao.queryBuilder().where(NotificationTblDao.Properties.UserId.eq(UserId), NotificationTblDao.Properties.FlagRead.eq(0)).count();
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
