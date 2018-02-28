package android.rezkyaulia.com.feo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.rezkyaulia.com.feo.database.entity.DaoMaster;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTblDao;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTblDao;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTblDao;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTblDao;
import android.rezkyaulia.com.feo.database.entity.UserTblDao;
import android.rezkyaulia.com.feo.handler.api.NotificationApi;
import android.rezkyaulia.com.feo.utility.PreferencesManager;

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
        switch (newVersion) {
            case 3:
                LibraryTblDao.dropTable(db, true);
                LibraryTblDao.createTable(db, true);

                ScoreTblDao.dropTable(db, true);
                ScoreTblDao.createTable(db, true);

                UserTblDao.dropTable(db, true);
                UserTblDao.createTable(db, true);

                NotificationTblDao.dropTable(db, true);
                NotificationTblDao.createTable(db, true);

                SubscriptionTblDao.dropTable(db, true);
                SubscriptionTblDao.createTable(db, true);


        }

    }
}
