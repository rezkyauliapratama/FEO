package android.rezkyaulia.com.feo.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ActivityLoginBinding;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/18/2017.
 */

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initView(){

    }

    private void login(String username, String password){

    }
}
