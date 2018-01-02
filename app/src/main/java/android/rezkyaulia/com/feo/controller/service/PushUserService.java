package android.rezkyaulia.com.feo.controller.service;

import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.ScoreApi;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/30/2017.
 */

public class PushUserService extends JobService {


    @Override
    public boolean onStartJob(JobParameters job) {
        Timber.e("OnStartJob");
        //Offloading work to a new thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                Timber.e("ON RUN THREAD");
                pushData(job);
            }
        }).start();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.e("OnStopJob");

        return false;
    }

    private void pushData(JobParameters jobParameters){
        UserTbl userTbl = Facade.getInstance().getManagerUserTbl().get();
        Timber.e("UserTbl : "+new Gson().toJson(userTbl));
        if (userTbl!= null){

                ApiClient.getInstance().user().update(userTbl).getAsObject(UserApi.Response.class, new ParsedRequestListener<UserApi.Response>() {

                    @Override
                    public void onResponse(UserApi.Response response) {
                        if (response != null){
                            Timber.e("OnREsponse :"+new Gson().toJson(response));
                           if (HttpResponse.getInstance().success(response)){
                               Timber.e("OnREsponse true");
                           }else{
                               Toast.makeText(PushUserService.this,"Failed to update date user into server",Toast.LENGTH_LONG).show();
                           }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e("OnERROR UPLOAD User : "+anError.getErrorBody() +" , "+anError.getErrorDetail());
                        jobFinished(jobParameters,true);
                    }
                });



        }
    }
}
