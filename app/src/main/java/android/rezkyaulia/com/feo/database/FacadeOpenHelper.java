package android.rezkyaulia.com.feo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.rezkyaulia.com.feo.database.entity.DaoMaster;

import org.greenrobot.greendao.database.Database;

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

    }
}
