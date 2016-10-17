package com.apkplug.cam360plug;

import android.content.Intent;

import com.common.ICam360Start;

import org.apkplug.Bundle.bundlerpc.ObjectPool;
import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/10/13.
 */

public class RPCCam360Start implements ICam360Start {
    @Override
    public void start(String distPath, Action2<Boolean, String> callback) {
        String outFilePath = distPath;
        Intent intent = new Intent(MainActivitor.bundleContext.getBundleContext(),StartActivity.class);
        intent.putExtra("distPath",outFilePath);
        intent.putExtra("rpc_callback",new ObjectPool<Action2<Boolean,String>>(callback));
        MainActivitor.bundleContext.getBundleContext().startActivity(intent);
    }
}
