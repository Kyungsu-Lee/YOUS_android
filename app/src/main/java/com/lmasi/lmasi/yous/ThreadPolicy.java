package com.lmasi.lmasi.yous;

import android.os.StrictMode;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class ThreadPolicy {

    // For smooth networking
    public ThreadPolicy() {

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }
}


