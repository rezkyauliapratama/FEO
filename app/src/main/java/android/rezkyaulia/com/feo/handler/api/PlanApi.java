package android.rezkyaulia.com.feo.handler.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class PlanApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/plan");
    public final String pathImage = Constant.getInstance().BASE_IMAGE_URL.concat("/plan/");

    protected PlanApi(ApiClient api) {
        this.api = api;
    }

    public void getAll(ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path);
        AndroidNetworking.get(path)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Response.class, callback);
    }

    public Bitmap getImage(Context context,String name) throws ExecutionException, InterruptedException {
        return Glide.with(context).load(pathImage.concat(name)).asBitmap().into(-1,-1).get();
    }
    public class Response extends ApiResponse<PlanTbl> {
    }
}
