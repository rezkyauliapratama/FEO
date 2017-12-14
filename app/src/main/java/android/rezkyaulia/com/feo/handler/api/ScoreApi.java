package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/23/2017.
 */

public class ScoreApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/score");

    protected ScoreApi(ApiClient api) {
        this.api = api;
    }

    public ANRequest bulk(final List<ScoreTbl> scores){
        Timber.e("PATH : "+path.concat("/bulk"));
        Timber.e("SCORES : "+new Gson().toJson(scores));
        Timber.e("HEADER : "+new Gson().toJson(ApiClient.getInstance().getHeader()));
        return AndroidNetworking.post(path.concat("/bulk"))
                .addStringBody(new Gson().toJson(scores)) // posting java object
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.HIGH)
                .build();
    }

    public class Response extends ApiResponse<ScoreTbl> {
    }
}
