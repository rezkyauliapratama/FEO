package android.rezkyaulia.com.feo.handler.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jetbrains.annotations.Contract;

/**
 * Created by Rezky Aulia Pratama on 11/19/2017.
 */

public class ApiClient {
    // Static member class member that holds only one instance of the
    // SingletonExample class
    private Context mContext;
    private static class SingletonHolder{
        public static ApiClient singletonInstance =
                new ApiClient();
    }
    // SingletonExample prevents any other class from instantiating
    private ApiClient() {
        user = new UserApi(this);
    }

    // Providing Global point of access
    @Contract(pure = true)
    public static ApiClient getInstance() {
        return SingletonHolder.singletonInstance;
    }

    private final UserApi user;
    public UserApi user() {
        return user;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
