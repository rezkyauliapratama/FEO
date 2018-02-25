package android.rezkyaulia.com.feo.controller.service;

import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.LibraryApi;
import android.rezkyaulia.com.feo.handler.api.ScoreApi;

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

public class PushLibraryService extends JobService {


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
        List<LibraryTbl> libraryTbls = Facade.getInstance().getManageLibraryTbl().getAllEmptyId();
        Timber.e("scoreTbl : "+new Gson().toJson(libraryTbls));
        if (libraryTbls != null){
            if (libraryTbls.size() > 0){
                Timber.e("library.size()"+libraryTbls.size());
                ApiClient.getInstance().library().bulk(libraryTbls).getAsObject(LibraryApi.Response.class, new ParsedRequestListener<LibraryApi.Response>() {

                    @Override
                    public void onResponse(LibraryApi.Response response) {
                        if (response != null){
                            Timber.e("OnREsponse true");
                            List<LibraryTbl> tempLibaries = response.getApiList();
                            Timber.e("RESPONSE : "+new Gson().toJson(response));

                            if (tempLibaries != null){
                                if (tempLibaries.size() > 0){
                                    Timber.e("tempLibary.size : "+tempLibaries.size());
                                    Facade.getInstance().getManageLibraryTbl().add(tempLibaries);
                                    for (LibraryTbl item : libraryTbls){
                                        Facade.getInstance().getManageLibraryTbl().remove(item);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e("OnERROR UPLOAD SCORES : "+anError.getErrorBody() +" , "+anError.getErrorDetail());
                        jobFinished(jobParameters,false);
                    }
                });


            }
        }
    }
}
