package android.rezkyaulia.com.feo.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.SubscribeRVAdapter;
import android.rezkyaulia.com.feo.controller.fragment.CheckoutFragment;
import android.rezkyaulia.com.feo.controller.fragment.PaymentFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTbl;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.databinding.ActivityPaymentBinding;
import android.rezkyaulia.com.feo.databinding.ActivitySubscribeBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.PaymentRegApi;
import android.rezkyaulia.com.feo.handler.api.PlanApi;
import android.rezkyaulia.com.feo.handler.api.SubscriptionApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/23/2017.
 */

public class PaymentActivity extends BaseActivity implements
        CheckoutFragment.OnFragmentInteractionListener,
        PaymentFragment.OnFragmentInteractionListener
                                    {
    public final static String ARGS1 = "ARGS1";
    public final static String ARGS2 = "ARGS2";

    ActivityPaymentBinding binding;

    private PlanTbl mPlanTbl;
    private SubscriptionTbl mSubsciptionTbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);

        if (getIntent() != null){
            Timber.e("getIntent() != null");
            mPlanTbl = getIntent().getParcelableExtra(ARGS1);
            mSubsciptionTbl = getIntent().getParcelableExtra(ARGS2);
        }

        if (mSubsciptionTbl == null){
            Timber.e("mPlanTbl : "+new Gson().toJson(mPlanTbl));
            displayFragment(binding.content.layoutContent.getId(),CheckoutFragment.newInstance(mPlanTbl));
        }else{
            Timber.e("mSubscriptionTbl : "+new Gson().toJson(mSubsciptionTbl));
            displayFragment(binding.content.layoutContent.getId(),PaymentFragment.newInstance(mSubsciptionTbl));
        }


    }


    @Override
    public void onCheckout() {
        binding.content.layoutProgress.setVisibility(View.VISIBLE);
        SubscriptionTbl subscriptionTbl = new SubscriptionTbl();
        subscriptionTbl.setPlanId(mPlanTbl.getPlanId());
        subscriptionTbl.setUserId(userTbl.getUserId());
        ApiClient.getInstance().subscription().post(subscriptionTbl, new ParsedRequestListener<SubscriptionApi.Response>() {
            @Override
            public void onResponse(SubscriptionApi.Response response) {
                Timber.e("subc api : "+new Gson().toJson(response));
                if (HttpResponse.getInstance().success(response)){
                    SubscriptionTbl subscriptionTbl = response.getApiValue();
                    Long id = facade.getManageSubscriptionTbl().add(subscriptionTbl);
                    if (id > 0){
                        PaymentRegistrationTbl paymentRegistrationTbl = new PaymentRegistrationTbl();
                        paymentRegistrationTbl.setSubscriptionId(subscriptionTbl.getSubscriptionId());
                        paymentRegistrationTbl.setCustomerName(userTbl.getName());
                        paymentRegistrationTbl.setTransactionAmount((long)mPlanTbl.getPrice());

                        ApiClient.getInstance().paymentReg().postReg(paymentRegistrationTbl, new ParsedRequestListener<PaymentRegApi.ResponsePost>() {
                            @Override
                            public void onResponse(PaymentRegApi.ResponsePost response) {
                                Timber.e("payment reg api : "+new Gson().toJson(response));
                                if (HttpResponse.getInstance().success(response)){
                                    PaymentRegistrationTbl paymentRegistrationTbl = response.ApiValue.getPaymentRegistrationTbl();
                                    PaymentRegistrationResponseTbl paymentRegistrationResponseTbl= response.ApiValue.getPaymentRegistrationResponseTbl();

                                    if (paymentRegistrationTbl!= null && paymentRegistrationResponseTbl != null){
                                        Timber.e("paymentRegistrationResponseTbl: "+new Gson().toJson(paymentRegistrationResponseTbl    ));
                                        Facade.getInstance().getManagePaymentRegistrationTbl().add(paymentRegistrationTbl);
                                        Facade.getInstance().getManagePaymentRegistrationResponseTbl().add(paymentRegistrationResponseTbl);
                                        displayFragment(binding.content.layoutContent.getId(), PaymentFragment.newInstance(subscriptionTbl));
                                    }
                                }
                                binding.content.layoutProgress.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError(ANError anError) {
                                binding.content.layoutProgress.setVisibility(View.GONE);
                                Timber.e("Error paymentReg : "+new Gson().toJson(anError));
                            }
                        });
                    }else{
                        binding.content.layoutProgress.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                Timber.e("Error subs: "+new Gson().toJson(anError));

            }
        });



    }

    @Override
    public void onCancelPayment(SubscriptionTbl subscriptionTbl) {
        Timber.e("onCancelPayment : "+new Gson().toJson(subscriptionTbl));
        subscriptionTbl.setActiveFlag(0L);
        facade.getManageSubscriptionTbl().add(mSubsciptionTbl);
        finish();

        startActivity(new Intent(this,SubscribeActivity.class));
    }

    @Override
    public void onRedirectToActivity() {
        redirectToMainActivity();
    }


}
