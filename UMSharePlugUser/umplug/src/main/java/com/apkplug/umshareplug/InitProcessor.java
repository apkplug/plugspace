package com.apkplug.umshareplug;

import com.umeng.socialize.PlatformConfig;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qinfeng on 2016/8/17.
 */
public class InitProcessor extends BaseProcessor{

    public InitProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMapin) {

        List<HashMap<String,Object>> plateforms = (List<HashMap<String, Object>>) hashMapin.get(PlugConstants.PLATEPORMS);

        for(HashMap<String,Object> hashMap : plateforms){
            String appid = (String) hashMap.get(PlugConstants.APPID);
            String appkey = (String) hashMap.get(PlugConstants.APPKEY);

            if(appid == null){
                dispatchAgent.reply(getMsgId(),false,"appId is null");
                return;
            }
            if(appkey == null){
                dispatchAgent.reply(getMsgId(),false,"appKey is null");
                return;
            }

            String platform = (String) hashMap.get(PlugConstants.PLATFORM);
            if(platform == null){
                dispatchAgent.reply(getMsgId(),false,"platform is null");
                return;
            }

            try {
                if(PlugConstants.PLATFORM_WIEXIN.equals(platform)){
                    PlatformConfig.setWeixin(appid, appkey);
                }else if(PlugConstants.PLATFORM_SINA.equals(platform)){
                    PlatformConfig.setSinaWeibo(appid,appkey);
                }else if(PlugConstants.PLATFORM_QQ.equals(platform)){
                    PlatformConfig.setQQZone(appid,appkey);
                }
                dispatchAgent.reply(getMsgId(),true,platform);
            } catch (Exception e) {
                e.printStackTrace();
                dispatchAgent.reply(getMsgId(),false,e);
            }

        }

    }

}
