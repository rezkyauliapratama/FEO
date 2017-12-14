package android.rezkyaulia.com.feo.controller.activity;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.LoginFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ActivityLoginBinding;
import android.support.v7.widget.Toolbar;

/**
 * Created by Rezky Aulia Pratama on 11/18/2017.
 */

public class LoginActivity extends BaseActivity implements LoginFragment.OnFragmentInteractionListener{

    ActivityLoginBinding binding;

    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserTbl userTbl = Facade.getInstance().getManagerUserTbl().get();

        if (userTbl != null)
            redirectToMainActivity();


        loginFragment = LoginFragment.newInstance();
        displayFragment(binding.layoutContent.getId(),loginFragment);
    }



    public void redirectToMainActivity() {

        super.redirectToMainActivity();
    }

    @Override
    public void onLoginSuccessfull() {
        redirectToMainActivity();
    }
}
