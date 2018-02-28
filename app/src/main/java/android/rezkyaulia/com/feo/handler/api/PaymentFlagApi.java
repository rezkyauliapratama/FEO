package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.PaymentFlagResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentFlagTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;
import android.view.Display;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 1/23/2018.
 */

public class PaymentFlagApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/paymentflag");

    protected PaymentFlagApi(ApiClient api) {
        this.api = api;
    }

    public void getByInsertId(Long insertId, ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path+"/insertid/"+insertId);
        AndroidNetworking.get(path+"/insertid/"+insertId)
                .setPriority(Priority.MEDIUM)
                .addHeaders(ApiClient.getInstance().getHeader())
                .build()
                .getAsObject(Response.class, callback);
    }


    public class Response extends ApiResponse<ModelResponse> {
    }


    public class ModelResponse {
        @SerializedName("PaymentFlagTbl")
        PaymentFlagTbl paymentFlagTbl;

        @SerializedName("PaymentFlagResponseTbl")
        PaymentFlagResponseTbl paymentFlagResponseTbl;

        @SerializedName("SubscriptionTbl")
        SubscriptionTbl SubscriptionTbl;

        public PaymentFlagTbl getPaymentFlagTbl() {
            return paymentFlagTbl;
        }

        public void setPaymentFlagTbl(PaymentFlagTbl paymentFlagTbl) {
            this.paymentFlagTbl = paymentFlagTbl;
        }

        public PaymentFlagResponseTbl getPaymentFlagResponse() {
            return paymentFlagResponseTbl;
        }

        public void setPaymentFlagResponse(PaymentFlagResponseTbl paymentFlagResponseTbl) {
            this.paymentFlagResponseTbl = paymentFlagResponseTbl;
        }

        public SubscriptionTbl getSubscriptionTbl() {
            return SubscriptionTbl;
        }

        public void setSubscriptionTbl(SubscriptionTbl subscriptionTbl) {
            SubscriptionTbl = subscriptionTbl;
        }
    }
}
