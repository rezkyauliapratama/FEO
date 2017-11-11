package com.rezkyaulia.android.light_optimization_data;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Rezky Aulia Pratama on 7/4/2017.
 */

public class Utils {

    // Step 1: private static variable of INSTANCE variable
    private static volatile Utils INSTANCE;

    // Step 2: private constructor
    private Utils() {

    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static Utils init() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (Utils.class) {
                if(null == INSTANCE){
                    INSTANCE = new Utils();

                }

            }
        }
        return INSTANCE;
    }

    public Cache getCache(Context context, int maxCacheSize, String uniqueName) {
        return new Cache(getDiskCacheDir(context, uniqueName), maxCacheSize);
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        return new File(context.getCacheDir(), uniqueName);
    }
}
