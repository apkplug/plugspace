package com.apkplug.baseplugmodle;

import android.app.Application;

import com.apkplug.trust.PlugManager;

import org.apkplug.app.FrameworkFactory;

/**
 * Created by qinfeng on 2016/9/2.
 */
public class MainApplication extends Application {

    //change
    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
