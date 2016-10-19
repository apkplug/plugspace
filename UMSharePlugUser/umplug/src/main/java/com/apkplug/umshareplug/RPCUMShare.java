package com.apkplug.umshareplug;

import android.content.Intent;
import android.util.Log;

import com.umshare.common.IUMShare;
import com.umeng.socialize.PlatformConfig;

import org.apkplug.Bundle.bundlerpc.ObjectPool;
import org.apkplug.Bundle.bundlerpc.functions.Action2;

import java.util.HashMap;
import java.util.List;

/**
 * Created by qinfeng on 2016/10/13.
 */

public class RPCUMShare implements IUMShare {
    public static boolean init;
    public static final String PARAMMAP = "parammap";
    @Override
    public void share(HashMap<String, Object> params, Action2<Boolean, String> callback) {
        if(!init){
            List<HashMap<String,Object>> plateforms = (List<HashMap<String, Object>>) params.get(PlugConstants.INIT);

            for(HashMap<String,Object> oneplate : plateforms){
                String appid = (String) oneplate.get(PlugConstants.APPID);
                String appkey = (String) oneplate.get(PlugConstants.APPKEY);

                if(appid == null){
                    //dispatchAgent.reply(getMsgId(),false,"appId is null");
                    callback.call(false,"appId is null");
                    return;
                }
                if(appkey == null){
                    //dispatchAgent.reply(getMsgId(),false,"appKey is null");
                    callback.call(false,"appKey is null");
                    return;
                }

                String platform = (String) oneplate.get(PlugConstants.PLATFORM);
                if(platform == null){
                    //dispatchAgent.reply(getMsgId(),false,"platform is null");
                    callback.call(false,"platform is null");
                    return;
                }


                if (PlugConstants.PLATFORM_WIEXIN.equals(platform)) {
                    PlatformConfig.setWeixin(appid, appkey);
                } else if (PlugConstants.PLATFORM_SINA.equals(platform)) {
                    PlatformConfig.setSinaWeibo(appid, appkey);
                } else if (PlugConstants.PLATFORM_QQ.equals(platform)) {
                    PlatformConfig.setQQZone(appid, appkey);
                }

            }
            init = true;
        }


        Log.e("receive","ininin");
        Intent intent = new Intent(MainActivitor.bundleContext.getBundleContext(),MainActivity.class);
        intent.putExtra("rpc_callback",new ObjectPool<Action2<Boolean,String>>(callback));
        intent.putExtra(PARAMMAP,params);
        MainActivitor.bundleContext.getBundleContext().startActivity(intent);
    }

}
