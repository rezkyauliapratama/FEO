package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/23/2017.
 */

public class NotificationApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/notification");

    protected NotificationApi(ApiClient api) {
        this.api = api;
    }


    public void delete(NotificationTbl notificationTbl, ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path);
        Timber.e("HEADER : "+new Gson().toJson(ApiClient.getInstance().getHeader()));
        Timber.e("notificationTbl : "+new Gson().toJson(notificationTbl));

        AndroidNetworking.delete(path)
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.MEDIUM)
                .addStringBody(new Gson().toJson(notificationTbl)) // posting java object
                .build()
                .getAsObject(Response.class, callback);
    }


    public class Response extends ApiResponse<LibraryTbl> {
    }
}
