package android.rezkyaulia.com.feo.controller.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.LoginFragment;
import android.rezkyaulia.com.feo.controller.fragment.RegisterFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ActivityLoginBinding;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import timber.log.Timber;

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
    public void onSubscribeInteraction(UserTbl userTbl) {
        long id = Facade.getInstance().getManagerUserTbl().add(userTbl);
        if (id>0) {
            redirectToMainActivity();
        }
    }

    @Override
    public void onRegisterInteraction() {
        displayFragment(binding.layoutContent.getId(),registerFragment);

    }

    @Override
    public void onSignInteraction() {
        displayFragment(binding.layoutContent.getId(),loginFragment);
    }

    @Override
    public void onRegisteredSuccesful(UserApi.Response response) {
        showAlertDialog(response);
    }

    @Override
    public void onRegisteredFailed(UserApi.Response response) {
        Snackbar.make(binding.layoutContent,response.ApiMessage,Snackbar.LENGTH_LONG).show();
    }

    private void showAlertDialog(UserApi.Response response){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Register"); //Set Alert dialog title here
        alert.setMessage("Thank you, please login with your new account"); //Message here
        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.

                displayFragment(binding.layoutContent.getId(),loginFragment);
                if (loginFragment != null)
                RxBus.getInstance().post(response);

            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }


}
