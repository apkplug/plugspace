package com.apkplug.kf5plug;

import android.content.Intent;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/25.
 */
public class OpenActivityProcessor extends BaseProcessor{
    public OpenActivityProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        try {
            Intent intent = new Intent();
            intent.setClassName(context.getBundleContext(),"com.apkplug.kf5plug.MainActivity");
            context.getBundleContext().startActivity(intent);
            dispatchAgent.reply(getMsgId(),true);
        } catch (Exception e) {
            e.printStackTrace();
            dispatchAgent.reply(getMsgId(),false,e);
        }
    }
}
