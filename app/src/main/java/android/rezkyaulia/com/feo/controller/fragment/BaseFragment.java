package android.rezkyaulia.com.feo.controller.fragment;


import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


/**
 * Created by Shiburagi on 16/06/2016.
 */
public class BaseFragment extends Fragment {


    private static final String TAG = BaseFragment.class.getSimpleName();

    protected PreferencesManager pref;
    protected Facade facade;
    protected UserTbl userTbl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facade = Facade.getInstance();
        pref = PreferencesManager.getInstance();
        userTbl = Facade.getInstance().getManagerUserTbl().get();

    }

    @Override
    public void onStart() {
        super.onStart();
       /* if (isObserverFragment()){
            EventBus.getDefault().register(this);
            Timber.e("ON OBSERVER START");
        }*/
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (isObserverFragment()){
            EventBus.getDefault().unregister(this);
            Timber.e("ON OBSERVER STOP");

        }*/
    }


    private boolean isObserverFragment(){
        return this instanceof SpeedReadingFragment || this instanceof LibraryFragment;
    }
    protected void displayFragment(int id, Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(id, fragment).commitAllowingStateLoss();
    }

    protected void addFragment(int id, Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .add(id, fragment).commitAllowingStateLoss();
    }
    protected void removeFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .remove(fragment).commitAllowingStateLoss();
    }




    @Override
    public void onResume() {
        super.onResume();

    }
    protected int getColorPrimary() {
        return ContextCompat.getColor(getContext(), R.color.colorPrimary);
    }

    public void update() {

    }
}
