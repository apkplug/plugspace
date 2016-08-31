package com.apkplug.umshareplug;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by qinfeng on 2016/8/17.
 */
public class PlugApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxe11d7c9e16d9556e", "d1b94ae7e1ca1e122f2d04f2d402fc99");
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("1105439618", "xV30cmR6uB83n1dr");
        context = this;
    }

    public static Context context;
}
