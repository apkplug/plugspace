package com.geetest.android.sdk;


import android.util.Log;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

/**
 * Created by qinfeng on 2016/7/20.
 */
public abstract class BaseProcessor extends Processor {

    BundleContext context;
    DispatchAgent dispatchAgent;

    public BaseProcessor(BundleContext context) {
        super(context);
        Log.e("init ","init thread "+Thread.currentThread().getName());
        this.context = context;
        dispatchAgent = new DispatchAgent(context);
    }
}
