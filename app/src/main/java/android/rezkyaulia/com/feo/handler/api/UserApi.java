package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.google.gson.Gson;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class UserApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/user");

    protected UserApi(ApiClient api) {
        this.api = api;
    }

    public ANRequest login(final UserTbl user){
        Timber.e("PATH : "+ Constant.getInstance().BASE_URL.concat("/login"));
        Timber.e("USER : "+new Gson().toJson(user));
        return AndroidNetworking.post( Constant.getInstance().BASE_URL.concat("/login"))
                .addStringBody(new Gson().toJson(user)) // posting java object
                .setPriority(Priority.MEDIUM)
                .build();
    }

    public ANRequest post(final UserTbl user){
        Timber.e("PATH : "+path);
        Timber.e("USER : "+new Gson().toJson(user));
        return AndroidNetworking.post(path)
                .addStringBody(new Gson().toJson(user)) // posting java object
                .setPriority(Priority.MEDIUM)
                .build();
    }

    public ANRequest update(final UserTbl user){
        Timber.e("PATH : "+path.concat("/").concat(String.valueOf(user.getUserId())));
        Timber.e("USER : "+new Gson().toJson(user));
        return AndroidNetworking.post(path.concat("/").concat(String.valueOf(user.getUserId())))
                .addHeaders(ApiClient.getInstance().getHeader())
                .addStringBody(new Gson().toJson(user)) // posting java object
                .setPriority(Priority.MEDIUM)
                .build();
    }

    public class Response extends ApiResponse<UserTbl> {
    }
}
