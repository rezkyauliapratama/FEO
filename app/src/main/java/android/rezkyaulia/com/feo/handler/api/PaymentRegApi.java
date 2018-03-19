package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 1/23/2018.
 */

public class PaymentRegApi {
    private final ApiClient api;
    final String path = Constant.getInstance().BASE_URL.concat("/paymentreglive");

    protected PaymentRegApi(ApiClient api) {
        this.api = api;
    }

    public void postReg(PaymentRegistrationTbl paymentRegistrationTbl, ParsedRequestListener<ResponsePost> callback){
        Timber.e("PATH : "+path);
        Timber.e("paymentRegistrationTbl : "+new Gson().toJson(paymentRegistrationTbl));
        AndroidNetworking.post(path)
                .addStringBody(new Gson().toJson(paymentRegistrationTbl)) // posting java object
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(ResponsePost.class,callback);
    }

    public class Response extends ApiResponse<PaymentRegistrationTbl> {
    }

    public class ResponsePost extends ApiResponse<ModelResponse> {
    }

    public class ModelResponse {
        @SerializedName("PaymentRegistrationTbl")
        PaymentRegistrationTbl paymentRegistrationTbl;
        @SerializedName("PaymentRegistrationResponseTbl")
        PaymentRegistrationResponseTbl paymentRegistrationResponseTbl;

        public PaymentRegistrationTbl getPaymentRegistrationTbl() {
            return paymentRegistrationTbl;
        }

        public void setPaymentRegistrationTbl(PaymentRegistrationTbl paymentRegistrationTbl) {
            this.paymentRegistrationTbl = paymentRegistrationTbl;
        }

        public PaymentRegistrationResponseTbl getPaymentRegistrationResponseTbl() {
            return paymentRegistrationResponseTbl;
        }

        public void setPaymentRegistrationResponseTbl(PaymentRegistrationResponseTbl paymentRegistrationResponseTbl) {
            this.paymentRegistrationResponseTbl = paymentRegistrationResponseTbl;
        }
    }
}
