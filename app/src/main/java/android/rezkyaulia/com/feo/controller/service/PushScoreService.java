package android.rezkyaulia.com.feo.controller.service;

import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.ScoreApi;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/30/2017.
 */

public class PushScoreService extends JobService {


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
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Timber.e("OnStopJob");

        return false;
    }

    private void pushData(JobParameters jobParameters){
        List<ScoreTbl> scoreTbls = Facade.getInstance().getManageScoreTbl().getAllEmptyScoreId();
        Timber.e("scoreTbl : "+new Gson().toJson(scoreTbls));
        if (scoreTbls != null){
            if (scoreTbls.size() > 0){
                Timber.e("scoreTbls.size()"+scoreTbls.size());
                ApiClient.getInstance().score().bulk(scoreTbls).getAsObject(ScoreApi.Response.class, new ParsedRequestListener<ScoreApi.Response>() {

                    @Override
                    public void onResponse(ScoreApi.Response response) {
                        if (response != null){
                            Timber.e("OnREsponse true");
                            List<ScoreTbl> tempScores = response.getApiList();
                            Timber.e("RESPONSE : "+new Gson().toJson(response));

                            if (tempScores != null){
                                if (tempScores.size() > 0){
                                    Timber.e("tempscores.size : "+tempScores.size());
                                    Facade.getInstance().getManageScoreTbl().add(tempScores);
                                    for (ScoreTbl item : scoreTbls){
                                        Facade.getInstance().getManageScoreTbl().remove(item);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e("OnERROR UPLOAD SCORES : "+anError.getErrorBody() +" , "+anError.getErrorDetail());
//                        jobFinished(jobParameters,true);
                    }
                });


            }
        }
    }
}
