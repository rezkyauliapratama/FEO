package android.rezkyaulia.com.feo.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.SubscribeRVAdapter;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.databinding.ActivitySubscribeBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.PlanApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/23/2017.
 */

public class SubscribeActivity extends BaseActivity implements SubscribeRVAdapter.OnAdapterInteractionListener {

    ActivitySubscribeBinding binding;

    List<PlanTbl> planTbls;
    private LinearLayoutManager mLayoutManager;
    private SubscribeRVAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscribe);


        initData();

        binding.imageViewLogout.setOnClickListener(v->{
            showAlertDialogLogout();
        });
    }

    void initData(){
        binding.content.textViewUsername.setText(userTbl.getUsername());
        SubscriptionTbl subscriptionTbl = Facade.getInstance().getManageSubscriptionTbl().getNewest(userTbl.getUserId());

        if (subscriptionTbl != null){
            Date endDate = Utils.getInstance().time().parseDate(subscriptionTbl.getSubscriptionEndTimestamp());
            if (endDate != null)
                binding.content.textViewStatus.setText("Your last subscription ended on ".concat(Utils.getInstance().time().getUserFriendlyDateString(endDate)));
        }else{
            binding.content.textViewStatus.setVisibility(View.GONE);
        }



        planTbls = new ArrayList<>();
        ApiClient.getInstance().plan().getAll(new ParsedRequestListener<PlanApi.Response>() {
            @Override
            public void onResponse(PlanApi.Response response) {
                if (HttpResponse.getInstance().success(response)){
                    if (response.getApiList().size() > 0){
                        planTbls.clear();
                        for (PlanTbl item : response.getApiList()){
                            if (item.getActiveFlag() == 1)
                                planTbls.add(item);
                        }

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
            mAdapter = new SubscribeRVAdapter(this,planTbls,this);
            binding.content.recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onListInteraction(PlanTbl planTbl) {
        Timber.e("onListInteraction : "+new Gson().toJson(planTbl));
        Intent i =new Intent(this,PaymentActivity.class);
        i.putExtra(PaymentActivity.ARGS1,planTbl);
        startActivity(i);
        finish();
    }

    private void showAlertDialogLogout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout"); //Set Alert dialog title here
        alert.setMessage("Do you want to logout ?"); //Message here
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                redirect();
                dialog.dismiss();
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();

            }
        }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}
