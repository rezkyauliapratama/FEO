package android.rezkyaulia.com.feo.controller.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.utility.Constant;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

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
        checkAppPermission();
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

    public void redirectToMainActivity() {

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void checkAppPermission() {

        final List<String> permissions = new ArrayList<>();
        boolean showMessage = readPhoneState(permissions);

//        AppPermissions.getInstance().checkAppPermission(this, permissions);

        if (permissions.size() > 0) {
            final String strings[] = new String[permissions.size()];

            if (showMessage) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(R.string.permissionrequestmessage)
                        .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        permissions.toArray(strings),
                                        Constant.getInstance().PERMISSION_REQUEST);
                            }
                        });
                builder.create().show();
            } else
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(strings),
                        Constant.getInstance().PERMISSION_REQUEST);
        }

    }

    private boolean readPhoneState(List<String> permissions) {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                return true;
            }
        }
        return false;
    }

}
