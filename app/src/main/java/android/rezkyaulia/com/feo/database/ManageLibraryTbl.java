package android.rezkyaulia.com.feo.database;

import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTblDao;

import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class ManageLibraryTbl {

    private Facade facade;


    private LibraryTblDao dao;

    ManageLibraryTbl(Facade facade) {
        this.facade = facade;
        this.dao = facade.session.getLibraryTblDao();
    }

    public long add(LibraryTbl object) {
        return dao.insertOrReplace(object);
    }

    public void add(List<LibraryTbl> object) {
        dao.insertOrReplaceInTx(object);
    }

    public List<LibraryTbl> getAll() {
        return dao.queryBuilder().list();
    }
    public List<LibraryTbl> getAllEmptyId() {
        return dao.queryBuilder().where(LibraryTblDao.Properties.LibraryId.isNull()).list();

    }

    public LibraryTbl get(long id) {
        return dao.queryBuilder().where(LibraryTblDao.Properties.Id.eq(id)).unique();
    }

    public void removeAll() {
        dao.deleteAll();
    }

    public void remove(LibraryTbl object) {
        dao.delete(object);
    }

    public long size(){
        return dao.count();
    }
}
