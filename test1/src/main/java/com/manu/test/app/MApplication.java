package com.manu.test.app;

import android.app.Application;
import android.content.Context;


/**
 * Powered by jzman.
 * Created on 2018/6/26 0026.
 */
public class MApplication extends Application {

    private MApplication mInstance;

    public  Context getInstance() {
        if (mInstance == null) {
            mInstance = this;
        }
        return mInstance;
    }
}
