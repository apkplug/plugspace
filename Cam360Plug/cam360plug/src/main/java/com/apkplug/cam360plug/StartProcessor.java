package com.apkplug.cam360plug;

import android.content.Intent;
import android.util.Log;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/26.
 */
public class StartProcessor extends BaseProcessor {
    public StartProcessor(BundleContext contextin) {
        super(contextin);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        Log.e("receive","iiiiii");

        String outFilePath = (String) hashMap.get("ditPath");
        Intent intent = new Intent(context.getBundleContext(),StartActivity.class);
        intent.putExtra("distPath",outFilePath);
        intent.putExtra("msgid",getMsgId());
        context.getBundleContext().startActivity(intent);
    }
}
