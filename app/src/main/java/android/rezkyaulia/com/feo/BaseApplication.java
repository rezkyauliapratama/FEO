package android.rezkyaulia.com.feo;

import android.app.Application;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.FacadeOpenHelper;
import android.rezkyaulia.com.feo.database.entity.DaoMaster;
import android.rezkyaulia.com.feo.database.entity.DaoSession;
import android.rezkyaulia.com.feo.utility.Constant;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.app.infideap.stylishwidget.view.Stylish;


import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.query.QueryBuilder;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 8/31/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesManager.init(this);

        String fontFolder = "fonts/Exo_2/Exo2-";
        Stylish.getInstance().set(
                fontFolder.concat("Regular.ttf"),
                fontFolder.concat("Medium.ttf"),
                fontFolder.concat("RegularItalic.ttf")
        );

        Stylish.getInstance().setFontScale(
                PreferencesManager.getInstance().getFontSize()
        );

        Timber.e(Stylish.getInstance().getFontScale()+"");

        String databaseName = Constant.getInstance().DB_NAME;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, databaseName);
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        Facade.init(daoSession);
        AndroidNetworking.initialize(getApplicationContext());

        if (BuildConfig.DEBUG){
            Log.e("BaseApplication","is debug : "+BuildConfig.DEBUG);
            Timber.plant(new Timber.DebugTree());
            QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
            QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
            AndroidNetworking.enableLogging();

        }
    }
}
