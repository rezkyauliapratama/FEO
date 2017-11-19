package android.rezkyaulia.com.feo.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 10/18/2017.
 */

public class BaseActivity extends AppCompatActivity {


    private static final String TAG = BaseActivity.class.getSimpleName();
    //    private float IMAGE_SIZE = 720f;
    protected PreferencesManager pref;

    public static String CURRENT_ACTIVITY = null;

    private String currentLocale;

    private long lastLockDialogShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferencesManager.getInstance();



    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (isObserveActivity()){
            Timber.e("ON START OBS ACT");

            EventBus.getDefault().register(this);

        }*/

    }

    @Override
    protected void onStop() {
        super.onStop();
       /* if (isObserveActivity()) {
            Timber.e("ON STOP OBS ACT");
            EventBus.getDefault().unregister(this);
        }*/
    }

    private boolean isObserveActivity(){
        return false;
    }

    public void addFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();

    }

    public void displayFragment(int id, Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(id, fragment)
                    .commitAllowingStateLoss();
        } catch (Exception e) {

        }

    }

    public void removeFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slid_left, R.anim.do_nothing);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.do_nothing, R.anim.slid_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.do_nothing, R.anim.slid_right);
    }




}
