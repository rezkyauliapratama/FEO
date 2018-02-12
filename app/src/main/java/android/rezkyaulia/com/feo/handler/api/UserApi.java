package android.rezkyaulia.com.feo.handler.api;

import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationResponseTbl;
import android.rezkyaulia.com.feo.database.entity.PaymentRegistrationTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.model.api.ApiResponse;
import android.rezkyaulia.com.feo.utility.Constant;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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


    public void checkAuth(ParsedRequestListener<Response> callback){
        Timber.e("PATH : "+path.concat("/checkauth"));
        AndroidNetworking.get(path.concat("/checkauth"))
                .addHeaders(ApiClient.getInstance().getHeader())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Response.class, callback);
    }

    public class ResponseRegistration extends ApiResponse<UserTbl>{

    }

    public class Response extends ApiResponse<ModelResponse> {
    }

    public class ModelResponse {
        @SerializedName("UserTbl")
        UserTbl userTbl;
        @SerializedName("SubscriptionTbl")
        SubscriptionTbl subsciptionTbl;
        @SerializedName("PaymentRegistrationTbl")
        PaymentRegistrationTbl paymentRegistrationTbl;
        @SerializedName("PaymentRegistrationResponseTbl")
        PaymentRegistrationResponseTbl paymentRegistrationResponseTbl;
        @SerializedName("LibraryTbl")
        List<LibraryTbl> libraryTbls;
        @SerializedName("ScoreTbl")
        List<ScoreTbl> scoreTbls;

        public UserTbl getUserTbl() {
            return userTbl;
        }

        public void setUserTbl(UserTbl userTbl) {
            this.userTbl = userTbl;
        }

        public SubscriptionTbl getSubsciptionTbl() {
            return subsciptionTbl;
        }

        public void setSubsciptionTbl(SubscriptionTbl subsciptionTbl) {
            this.subsciptionTbl = subsciptionTbl;
        }

        public PaymentRegistrationTbl getPaymentRegistrationTbl() {
            return paymentRegistrationTbl;
        }

        public PaymentRegistrationResponseTbl getPaymentRegistrationResponseTbl() {
            return paymentRegistrationResponseTbl;
        }

        public void setPaymentRegistrationTbl(PaymentRegistrationTbl paymentRegistrationTbl) {
            this.paymentRegistrationTbl = paymentRegistrationTbl;
        }

        public void setPaymentRegistrationResponseTbl(PaymentRegistrationResponseTbl paymentRegistrationResponseTbl) {
            this.paymentRegistrationResponseTbl = paymentRegistrationResponseTbl;
        }

        public List<LibraryTbl> getLibraryTbls() {
            return libraryTbls;
        }

        public void setLibraryTbls(List<LibraryTbl> libraryTbls) {
            this.libraryTbls = libraryTbls;
        }

        public List<ScoreTbl> getScoreTbls() {
            return scoreTbls;
        }

        public void setScoreTbls(List<ScoreTbl> scoreTbls) {
            this.scoreTbls = scoreTbls;
        }
    }
}
