package com.rezkyaulia.android.light_optimization_data;

import android.support.annotation.WorkerThread;

import com.rezkyaulia.android.light_optimization_data.RequestListener.ParsedCallback;
import com.rezkyaulia.android.light_optimization_data.parser.ParseUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/4/2017.
 */

public class HttpCore<T> {
    private OkHttpClient mClient;
    private String mURL ;
    private Class<?> mClass;
    T t = null;
    IOException e = null;

    //private constructor
    public HttpCore(OkHttpClient client,Class<T> aClass) {
        this.mClient = client;
        this.mClass = aClass;
        this.mURL = "";
    }

    //private constructor
    public HttpCore(OkHttpClient client,Type type) {
        this.mClient = client;
        this.mClass = type.getClass();
        this.mURL = "";
    }

    public HttpCore<T> withURL(String url){
        this.mURL = url;
        return this;
    }

    @WorkerThread
    public T getSyncFuture() throws IOException {

        if (mURL.isEmpty()){
            throw new IOException("URL is null");
        }

        Request request = new Request.Builder()
                .url(mURL)
                .build();
        Timber.e("GNS URL : " + mURL);
        if (mClient != null){
            Response response = mClient.newCall(request).execute();
            if (!response.isSuccessful()){
                e = new IOException("Unexpected code " + response);
                throw e;
            }

            t = (T) ParseUtil.getParserFactory().responseBodyParser(mClass).convert(response.body());
        }else{
            e = new IOException("Client is null");
            throw e;
        }
        return t;
    }

    public HttpCore<T> getAsFuture(final ParsedCallback<T> callback) {

        if (mURL.isEmpty()){
            callback.onFailure(new IOException("URL is null"));
            return this;
        }

        Request request = new Request.Builder()
                .url(mURL)
                .build();
        Timber.e("ASYNC URL : " + mURL);

        if (mClient != null){
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException ex) {
                    e = ex;
                    callback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    t = (T) ParseUtil.getParserFactory().responseBodyParser(mClass).convert(response.body());
                    callback.onCompleted(t);
                }
            });
        }else{
            e = new IOException("Client is null");
            callback.onFailure(e);

        }
        return this;
    }

    public HttpCore getAsString(){
        mClass = String.class;
        Request request = new Request.Builder()
                .url(mURL)
                .build();

        if (mClient != null){
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    t = (T) response.body().string();
                }
            });
        }else{
            e = new IOException("Client is null");
        }

        return this;
    }


}
