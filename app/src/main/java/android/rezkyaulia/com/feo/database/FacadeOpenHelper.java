package android.rezkyaulia.com.feo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.rezkyaulia.com.feo.database.entity.DaoMaster;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTblDao;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTblDao;
import android.rezkyaulia.com.feo.database.entity.UserTblDao;

import org.greenrobot.greendao.database.Database;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class FacadeOpenHelper extends DaoMaster.OpenHelper {
    public FacadeOpenHelper(Context context, String name) {
        super(context, name);
    }

    public FacadeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);

    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Timber.e("oldDB vers : "+oldVersion);
        switch (oldVersion) {
            case 1:
                LibraryTblDao.dropTable(db, true);
                LibraryTblDao.createTable(db, true);

                ScoreTblDao.dropTable(db, true);
                ScoreTblDao.createTable(db, true);

                UserTblDao.dropTable(db, true);
                UserTblDao.createTable(db, true);
        }

    }
}
