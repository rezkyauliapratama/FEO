package android.rezkyaulia.com.feo.utility;


import android.rezkyaulia.com.feo.model.api.ApiResponse;

/**
 * Created by Shiburagi on 20/06/2017.
 */

public class HttpResponse {
    // Step 1: private static variable of INSTANCE variable
    private static volatile HttpResponse INSTANCE;

    // Step 2: private constructor
    private HttpResponse() {

    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static HttpResponse getInstance() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (HttpResponse.class) {
                if(null == INSTANCE){
                    INSTANCE = new HttpResponse();
                }
            }
        }
        return INSTANCE;
    }

    public final String STATUS_OK = "200";
    public final String UNAUTHORIZED = "401";
    public final String CREATED = "201";
    public final String NOT_FOUND = "404";
    public final String UPLOADED = "201";
    public final String DELETED = "201";

    public boolean success(ApiResponse result) {
        return STATUS_OK.equalsIgnoreCase(result.ApiStatus)
                ||CREATED.equalsIgnoreCase(result.ApiStatus)
                ||DELETED.equalsIgnoreCase(result.ApiStatus)
                ||UPLOADED.equalsIgnoreCase(result.ApiStatus)
                ;
    }
}
