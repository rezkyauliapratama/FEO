package android.rezkyaulia.com.feo.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rezky Aulia Pratama on 9/6/2017.
 */

public class ApiResponse<T>   {

    @SerializedName(value = "ApiStatus")
    @Expose
    public String ApiStatus;
    @SerializedName(value = "ApiMessage")
    @Expose
    public String ApiMessage;

    @SerializedName("ApiElapsed")
    @Expose
    public String ApiElapsed;
    @SerializedName(value = "ApiList")
    @Expose
    public List<T> ApiList=new ArrayList<>();

    @SerializedName(value = "ApiValue")
    @Expose
    public T ApiValue;

    public ApiResponse() {
    }

    public String getApiStatus() {
        return ApiStatus;
    }

    public void setApiStatus(String apiStatus) {
        ApiStatus = apiStatus;
    }

    public String getApiMessage() {
        return ApiMessage;
    }

    public void setApiMessage(String apiMessage) {
        ApiMessage = apiMessage;
    }

    public String getApiElapsed() {
        return ApiElapsed;
    }

    public void setApiElapsed(String apiElapsed) {
        ApiElapsed = apiElapsed;
    }

    public List<T> getApiList() {
        return ApiList;
    }

    public void setApiList(List<T> apiList) {
        ApiList = apiList;
    }
}
