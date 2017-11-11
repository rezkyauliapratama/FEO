package com.rezkyaulia.android.light_optimization_data;

/**
 * Created by Rezky Aulia Pratama on 7/4/2017.
 */

public class NConstants {

    // Step 1: private static variable of INSTANCE variable
    private static volatile NConstants INSTANCE;

    // Step 2: private constructor
    private NConstants() {

    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static NConstants init() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (NConstants.class) {
                if(null == INSTANCE){
                    INSTANCE = new NConstants();

                }

            }
        }
        return INSTANCE;
    }
    
    public final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
    public final int UPDATE = 0x01;
    public final String CACHE_DIR_NAME = "cache_an";
   
    //call type
    public final int ASYNC = 0;
    public final int SYNC = 1;

    
}
