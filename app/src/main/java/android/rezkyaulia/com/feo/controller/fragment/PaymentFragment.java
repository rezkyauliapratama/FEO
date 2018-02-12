package android.rezkyaulia.com.feo.controller.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.PaymentFlagResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentFlagTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTbl;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.databinding.FragmentPaymentBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.PaymentFlagApi;
import android.rezkyaulia.com.feo.handler.api.SubscriptionApi;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.model.NotifModel;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.TextStyleBuilder;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/14/2017.
 */

public class PaymentFragment extends BaseFragment {
    public static final String ARG_PARAM1 = "ARGS1";
    FragmentPaymentBinding binding;

    private SubscriptionTbl mSubscriptionTbl;
    private PaymentRegistrationTbl mPaymentRegistrationTbl;
    private PaymentRegistrationResponseTbl mPaymentRegistrationResponseTbl;
    OnFragmentInteractionListener mListener;

    public static PaymentFragment newInstance(SubscriptionTbl subscriptionTbl) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, subscriptionTbl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments() != null){
            Timber.e("SAVEDINSTACESTATE");
            mSubscriptionTbl = getArguments().getParcelable(ARG_PARAM1);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_payment,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        if (mPaymentRegistrationResponseTbl != null){
            checkPayment();
        }

        RxBus.getInstance().observable(NotifModel.class).subscribe(new Observer<NotifModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotifModel notifModel) {
                Timber.e("OnNExt : "+new Gson().toJson(notifModel));
                checkPayment();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    void checkPayment(){
        ApiClient.getInstance().paymentFlag().getByInsertId(mPaymentRegistrationResponseTbl.getInsertId(), new ParsedRequestListener<PaymentFlagApi.Response>() {
            @Override
            public void onResponse(PaymentFlagApi.Response response) {
                Timber.e("PaymentFlagApi.Response : "+new Gson().toJson(response));
                if(HttpResponse.getInstance().success(response)){
                    PaymentFlagTbl paymentFlagTbl = response.getApiValue().getPaymentFlagTbl();
                    PaymentFlagResponseTbl paymentFlagResponseTbl= response.getApiValue().getPaymentRegistrationResponseTbl();
                    Timber.e("paymentFlagTbl : "+new Gson().toJson(paymentFlagTbl));
                    Timber.e("paymentFlagResponseTbl : "+new Gson().toJson(paymentFlagResponseTbl));
                    if (paymentFlagTbl != null && paymentFlagResponseTbl != null){

                        facade.getManagePaymentFlagTbl().add(paymentFlagTbl);
                        facade.getManagePaymentFlagResponseTbl().add(paymentFlagResponseTbl);

                        mSubscriptionTbl.setPaymentFlag(1L);
                        mSubscriptionTbl.setActiveFlag(1L);

                        ApiClient.getInstance().subscription().updateTime(mSubscriptionTbl, new ParsedRequestListener<SubscriptionApi.Response>() {
                            @Override
                            public void onResponse(SubscriptionApi.Response response) {
                                Timber.e("SubscriptionApi.Response : "+new Gson().toJson(response));
                                if (HttpResponse.getInstance().success(response)){
                                    mSubscriptionTbl = response.getApiValue();
                                    long id = facade.getManageSubscriptionTbl().updateBySubId(mSubscriptionTbl);
                                    if (id > 0){

                                        mListener.onRedirectToActivity();
                                    }

                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Timber.e("onError : "+new Gson().toJson(anError));
                            }
                        });

                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                Timber.e("Error paymentFlag().getByInsertId: "+new Gson().toJson(anError));
            }
        });
    }

    void initData(){
        Timber.e("initdata mSubscriptionTbl : "+new Gson().toJson(mSubscriptionTbl));
        if (mSubscriptionTbl != null){
            mPaymentRegistrationTbl = facade.getManagePaymentRegistrationTbl().getBySubsId(mSubscriptionTbl.getSubscriptionId());
            Timber.e("initdata mPaymentRegistrationTbl : "+new Gson().toJson(mPaymentRegistrationTbl));

            if (mPaymentRegistrationTbl != null){
                mPaymentRegistrationResponseTbl = facade.getManagePaymentRegistrationResponseTbl().getByRegId(mPaymentRegistrationTbl.getPaymentRegId());
                Timber.e("initdata mPaymentRegistrationResponseTbl  : "+new Gson().toJson(mPaymentRegistrationResponseTbl ));
            }
        }
    }


    void initView(){
        initData();

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogCancel();
            }
        });

        if (mPaymentRegistrationTbl != null){
            NumberFormat formatter = new DecimalFormat("#,###");

            binding.textViewVirtualAccount.setText(mPaymentRegistrationTbl.getCustomerAccount());
            String amount  = "<b>Rp. "+formatter.format(mPaymentRegistrationTbl.getTransactionAmount())+"</b>";
            binding.textViewPayment.setText(TextStyleBuilder.getInstance().parse(getContext(),getResources().getString(R.string.your_payment_s).concat(amount)));


            Date expiredDate = Utils.getInstance().time().parseDate(mPaymentRegistrationTbl.getTransactionExpire());
            Calendar cal = Calendar.getInstance();
            Date nowDate = cal.getTime();

            long diffInMs = expiredDate.getTime() - nowDate.getTime();

            final long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);


            if (diffInSec > 0){
                Observable.interval(1, TimeUnit.SECONDS)
                        .take(diffInSec) // up to 30 items
                        .map(v -> diffInSec - (v + 1)) // shift it to 1 .. 30
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            if (result > 0){
                                binding.textViewTimeExpired.setText(
                                        Utils.getInstance().time().timeConversion(getContext(),result));
                            }else{
                                mListener.onCancelPayment(mSubscriptionTbl);
                            }


                        });

            }else{
                mListener.onCancelPayment(mSubscriptionTbl);
            }


        }
    }

    private void showAlertDialogCancel(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(R.string.cancel_payment); //Set Alert dialog title here
        alert.setMessage(R.string.doyouwanttocancelthispayment); //Message here
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               mListener.onCancelPayment(mSubscriptionTbl);
                dialog.dismiss();
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();

            }
        }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onCancelPayment(SubscriptionTbl subscriptionTbl);
        void onRedirectToActivity();
    }

}
