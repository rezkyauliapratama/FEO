package android.rezkyaulia.com.feo.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ActivityLoginBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

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

        UserTbl userTbl = Facade.getInstance().getManagerUserTbl().get();

        if (userTbl != null)
            redirectToMainActivity();

        initView();
    }

    private void initView(){
        binding.content.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.content.edittextUsername.getText().toString();
                String password = binding.content.edittextPin.getText().toString();
                login(email,password);
            }
        });
    }

    private void login(String email, String password){
        UserTbl userTbl = new UserTbl();
        userTbl.setEmail(email);
        userTbl.setPassword(password);

        binding.content.layoutProgress.setVisibility(View.VISIBLE);

        ApiClient.getInstance().user().login(userTbl).
                getAsObject(UserApi.Response.class, new ParsedRequestListener<UserApi.Response>() {

                    @Override
                    public void onResponse(UserApi.Response response) {
                        if (response != null){
                            if (HttpResponse.getInstance().success(response)){
                                Timber.e("RESPONSE : "+new Gson().toJson(response));

                                long id = Facade.getInstance().getManagerUserTbl().add(response.ApiValue);
                                if (id>0) {

                                    Thread thread = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(6000);
                                                runOnUiThread(() -> {
                                                    binding.content.layoutProgress.setVisibility(View.GONE);
                                                    redirectToMainActivity();
                                                });
                                            } catch (InterruptedException e) {
                                                Timber.e("ERROR : "+e.getMessage());
                                            }

                                        }};
                                    thread.start();
                                }else
                                    Snackbar.make(binding.loginActivity,"An error expected",Snackbar.LENGTH_LONG).show();

                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e("Error : "+anError.getMessage());
                        binding.content.layoutProgress.setVisibility(View.GONE);
                    }
                });
    }

    public void redirectToMainActivity() {
        /*if (mChkboxSaveAuth.isChecked())
            pref.setSaveAuth(true);

        pref.setLock(false);*/

        super.redirectToMainActivity();
    }

}
