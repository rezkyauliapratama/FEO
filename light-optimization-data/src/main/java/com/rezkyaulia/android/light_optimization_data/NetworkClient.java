package com.rezkyaulia.android.light_optimization_data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/4/2017.
 */

public class NetworkClient {

    private Context mContext;
    // Step 1: private static variable of INSTANCE variable
    private static volatile NetworkClient INSTANCE;
    private OkHttpClient sHttpClient;

    // Step 2: private constructor
    private NetworkClient() {
    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static NetworkClient initialize() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (NetworkClient.class) {
                if(null == INSTANCE){
                    INSTANCE = new NetworkClient();

                }

            }
        }
        return INSTANCE;
    }

  @NonNull
    public <T> HttpCore<T> as (Class<T> t) throws Exception{
        if (null == INSTANCE){
            throw new IOException("Instance is null");
        }

        if (sHttpClient == null){
            throw new IOException("OkhttpClient is null");
        }

        HttpCore<T> core = new HttpCore<T>(sHttpClient,t);
        return core;
    }

    public void client(Context context) {
        this.sHttpClient = new OkHttpClient().newBuilder()
                .cache(Utils.init().getCache(context, NConstants.init().MAX_CACHE_SIZE, NConstants.init().CACHE_DIR_NAME))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

}
