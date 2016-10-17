package com.apkplug.kf5plug;

import android.content.Intent;
import android.util.Log;

import com.common.IKF5OpenKfActivity;

/**
 * Created by qinfeng on 2016/10/12.
 */

public class RPCOpenKF5 implements IKF5OpenKfActivity {
    @Override
    public void openKF5(String appId, String helpAddress, String userEmail) {
        UserHelper.userEmail = userEmail;
        UserHelper.appId = appId;
        UserHelper.helpAddress = helpAddress;
        try {
            Intent intent = new Intent();
            intent.setClassName(MainActivitor.bundleContext.getBundleContext(),"com.apkplug.kf5plug.MainActivity");
            MainActivitor.bundleContext.getBundleContext().startActivity(intent);
            //dispatchAgent.reply(getMsgId(),true);
        } catch (Exception e) {
            e.printStackTrace();
//            dispatchAgent.reply(getMsgId(),false,e);
            Log.e("error",e.getMessage());
        }
    }
}
