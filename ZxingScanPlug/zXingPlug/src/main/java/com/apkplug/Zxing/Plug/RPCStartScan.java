package com.apkplug.Zxing.Plug;

import android.content.Intent;

import com.common.IZxingStart;

import org.apkplug.Bundle.bundlerpc.ObjectPool;
import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/10/13.
 */

public class RPCStartScan implements IZxingStart {
    @Override
    public void startScan(Action2<Boolean, String> callback) {
        Intent intent = new Intent(MainActivitor.bundleContext.getBundleContext(), CaptureActivity.class);
        intent.putExtra("rpc_callback",new ObjectPool<Action2<Boolean,String>>(callback));
        MainActivitor.bundleContext.getBundleContext().startActivity(intent);
    }
}
