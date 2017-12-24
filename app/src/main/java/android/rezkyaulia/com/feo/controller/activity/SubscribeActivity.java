package android.rezkyaulia.com.feo.controller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.ScoreRVAdapter;
import android.rezkyaulia.com.feo.controller.adapter.SubscribeRBAdapter;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.databinding.ActivitySubscribeBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.PlanApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.support.v7.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/23/2017.
 */

public class SubscribeActivity extends BaseActivity {

    ActivitySubscribeBinding binding;

    List<PlanTbl> planTbls;
    private LinearLayoutManager mLayoutManager;
    private SubscribeRBAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscribe);


        initData();
    }

    void initData(){
        planTbls = new ArrayList<>();

        ApiClient.getInstance().plan().getAll(new ParsedRequestListener<PlanApi.Response>() {
            @Override
            public void onResponse(PlanApi.Response response) {
                if (HttpResponse.getInstance().success(response)){
                    if (response.getApiList().size() > 0){
                        planTbls.clear();
                        planTbls.addAll(response.getApiList());

                        initRecyclerView();

                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                Timber.e("error : "+new Gson().toJson(anError));
            }
        });
    }


    void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(this);
        binding.content.recyclerView.setLayoutManager(mLayoutManager);
        if (planTbls != null){
            mAdapter = new SubscribeRBAdapter(this,planTbls);
            binding.content.recyclerView.setAdapter(mAdapter);
        }
    }
}
