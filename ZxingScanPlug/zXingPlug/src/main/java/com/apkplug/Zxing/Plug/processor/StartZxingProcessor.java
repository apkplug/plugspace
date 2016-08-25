package com.apkplug.Zxing.Plug.processor;

import android.content.Intent;
import android.os.Bundle;

import com.apkplug.Zxing.Plug.CaptureActivity;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/24.
 */
public class StartZxingProcessor extends BaseProcessor {

    public StartZxingProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        Intent intent = new Intent(context.getBundleContext(), CaptureActivity.class);
        intent.putExtra("msgid",getMsgId());
        context.getBundleContext().startActivity(intent);

    }
}
