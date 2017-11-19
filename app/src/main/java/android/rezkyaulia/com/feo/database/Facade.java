package android.rezkyaulia.com.feo.database;


import android.database.Cursor;
import android.rezkyaulia.com.feo.database.entity.DaoSession;


import org.greenrobot.greendao.database.Database;

/**
 * Created by Shiburagi on 07/12/2016.
 */

public class Facade {

    private static Facade instance;
    final DaoSession session;

    public static void init(DaoSession daoSession) {
        instance = new Facade(daoSession);
    }

    public static Facade getInstance() {
        return instance;
    }

    private ManageLibraryTbl manageLibraryTbl;
    private ManageUserTbl manageUserTbl;




    Facade(DaoSession daoSession) {
        this.session = daoSession;

        manageLibraryTbl = new ManageLibraryTbl(this);
        manageUserTbl = new ManageUserTbl(this);


    }

    public DaoSession getDaoSession(){
        return session;
    }

    public ManageLibraryTbl getManageLibraryTbl() {
        return manageLibraryTbl;
    }

    public ManageUserTbl getManagerUserTbl() {
        return manageUserTbl;
    }

}
