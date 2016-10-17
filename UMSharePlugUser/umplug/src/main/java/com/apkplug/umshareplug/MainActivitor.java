package com.apkplug.umshareplug;

import android.util.Log;

import com.apkplug.umshareplug.wxapi.WXEntryActivity;

import org.apkplug.Bundle.ClassHook;
import org.apkplug.Bundle.ClassHookService;
import org.apkplug.Bundle.OSGIServiceAgent;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by qinfeng on 2016/8/31.
 */
public class MainActivitor implements BundleActivator {
    public static BundleContext bundleContext;
    OSGIServiceAgent<ClassHookService> agentclass=null;
    ImpClassHook hook=new ImpClassHook();
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        this.bundleContext = bundleContext;
        Log.e("start","start in");
        agentclass=new OSGIServiceAgent<ClassHookService>(bundleContext,ClassHookService.class);
        try {
            agentclass.getService().registerHook(bundleContext, hook);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        try {
            agentclass.getService().unregisterHook(bundleContext, hook);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}


