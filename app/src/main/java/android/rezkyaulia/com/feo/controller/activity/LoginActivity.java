package android.rezkyaulia.com.feo.controller.activity;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.LoginFragment;
import android.rezkyaulia.com.feo.controller.fragment.RegisterFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ActivityLoginBinding;
import android.support.v7.widget.Toolbar;

/**
 * Created by Rezky Aulia Pratama on 11/18/2017.
 */

public class LoginActivity extends BaseActivity implements
        LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener{

    ActivityLoginBinding binding;

    LoginFragment loginFragment;
    RegisterFragment registerFragment;

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
        registerFragment = RegisterFragment.newInstance();

        displayFragment(binding.layoutContent.getId(),loginFragment);
    }


    @Override
    public void onLoginSuccessfullInteraction() {
        redirectToMainActivity();
    }

    @Override
    public void onRegisterInteraction() {
        displayFragment(binding.layoutContent.getId(),registerFragment);

    }

    @Override
    public void onSignInteraction() {
        displayFragment(binding.layoutContent.getId(),loginFragment);
    }


}
