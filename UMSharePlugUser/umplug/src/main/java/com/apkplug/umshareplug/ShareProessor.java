package com.apkplug.umshareplug;

import android.content.Intent;
import android.util.Log;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/17.
 */
public class ShareProessor extends BaseProcessor {
    public static final  String MSGID = "msgid";
    public static final String PARAMMAP = "parammap";

    public ShareProessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        Log.e("receive","ininin");
        Intent intent = new Intent(context.getBundleContext(),MainActivity.class);
        intent.putExtra(MSGID,getMsgId());
        intent.putExtra(PARAMMAP,hashMap);
        context.getBundleContext().startActivity(intent);
    }

}
