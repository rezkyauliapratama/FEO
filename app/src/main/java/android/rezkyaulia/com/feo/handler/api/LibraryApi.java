package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
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

public class LibraryApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/library");

    protected LibraryApi(ApiClient api) {
        this.api = api;
    }

    public ANRequest bulk(final List<LibraryTbl> libraryTbls){
        Timber.e("PATH : "+path.concat("/bulk"));
        Timber.e("Libraries : "+new Gson().toJson(libraryTbls));
        Timber.e("HEADER : "+new Gson().toJson(ApiClient.getInstance().getHeader()));
        return AndroidNetworking.post(path.concat("/bulk"))
                .addStringBody(new Gson().toJson(libraryTbls)) // posting java object
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.HIGH)
                .build();
    }

    public void getAll(ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path);
        Timber.e("HEADER : "+new Gson().toJson(ApiClient.getInstance().getHeader()));

        AndroidNetworking.get(path)
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Response.class, callback);
    }


    public void delete(LibraryTbl libraryTbl, ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path);
        Timber.e("HEADER : "+new Gson().toJson(ApiClient.getInstance().getHeader()));

        AndroidNetworking.delete(path)
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.MEDIUM)
                .addStringBody(new Gson().toJson(libraryTbl)) // posting java object
                .build()
                .getAsObject(Response.class, callback);
    }


    public class Response extends ApiResponse<LibraryTbl> {
    }
}
