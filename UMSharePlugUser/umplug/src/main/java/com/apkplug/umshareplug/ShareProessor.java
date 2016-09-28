package com.apkplug.umshareplug;

import android.content.Intent;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qinfeng on 2016/8/17.
 */
public class ShareProessor extends BaseProcessor {
    public static final  String MSGID = "msgid";
    public static final String PARAMMAP = "parammap";

    public static boolean init;

    public ShareProessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        if(!init){
            List<HashMap<String,Object>> plateforms = (List<HashMap<String, Object>>) hashMap.get(PlugConstants.INIT);

            for(HashMap<String,Object> oneplate : plateforms){
                String appid = (String) oneplate.get(PlugConstants.APPID);
                String appkey = (String) oneplate.get(PlugConstants.APPKEY);

                if(appid == null){
                    dispatchAgent.reply(getMsgId(),false,"appId is null");
                    return;
                }
                if(appkey == null){
                    dispatchAgent.reply(getMsgId(),false,"appKey is null");
                    return;
                }

                String platform = (String) oneplate.get(PlugConstants.PLATFORM);
                if(platform == null){
                    dispatchAgent.reply(getMsgId(),false,"platform is null");
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
        Intent intent = new Intent(context.getBundleContext(),MainActivity.class);
        intent.putExtra(MSGID,getMsgId());
        intent.putExtra(PARAMMAP,hashMap);
        context.getBundleContext().startActivity(intent);
    }

}
