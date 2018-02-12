package android.rezkyaulia.com.feo.handler.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.rezkyaulia.com.feo.database.entity.PaymentFlagTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.concurrent.ExecutionException;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class SubscriptionApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/subscription");

    protected SubscriptionApi(ApiClient api) {
        this.api = api;
    }

    public void post(SubscriptionTbl subscriptionTbl, ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path);
        Timber.e("subscriptionTbl : "+new Gson().toJson(subscriptionTbl));

        AndroidNetworking.post(path)
                .addStringBody(new Gson().toJson(subscriptionTbl)) // posting java object
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Response.class,callback);
    }

    public void updateTime(SubscriptionTbl subscriptionTbl, ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path+"/update");
        Timber.e("subscriptionTbl : "+new Gson().toJson(subscriptionTbl));

        AndroidNetworking.post(path+"/update")
                .addStringBody(new Gson().toJson(subscriptionTbl)) // posting java object
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Response.class,callback);
    }


    public class Response extends ApiResponse<SubscriptionTbl> {
    }



}
