package android.rezkyaulia.com.feo.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rezky Aulia Pratama on 11/30/2017.
 */

public class Header implements Parcelable{
    @SerializedName(value = "ApiKey")
    public String ApiKey;

    @SerializedName(value = "UserKey")
    public String UserKey;

    @SerializedName(value = "Content-Type")
    public String ContentType;

    public Header(String apiKey, String userKey, String contentType) {
        ApiKey = apiKey;
        UserKey = userKey;
        ContentType = contentType;
    }

    public Header() {
    }

    public String getApiKey() {
        return ApiKey;
    }

    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ApiKey);
        dest.writeString(this.UserKey);
        dest.writeString(this.ContentType);
    }

    protected Header(Parcel in) {
        this.ApiKey = in.readString();
        this.UserKey = in.readString();
        this.ContentType = in.readString();
    }

    public static final Creator<Header> CREATOR = new Creator<Header>() {
        @Override
        public Header createFromParcel(Parcel source) {
            return new Header(source);
        }

        @Override
        public Header[] newArray(int size) {
            return new Header[size];
        }
    };
}
