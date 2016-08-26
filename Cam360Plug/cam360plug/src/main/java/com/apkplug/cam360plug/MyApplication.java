package com.apkplug.cam360plug;

import android.app.Application;

import us.pinguo.edit.sdk.PGEditImageLoader;
import us.pinguo.edit.sdk.base.PGEditSDK;

/**
 * Created by taoli on 14/11/5.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PGEditImageLoader.initImageLoader(this);
        PGEditSDK.instance().initSDK(this);
    }
}
