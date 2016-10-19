package com.apkplug.baseplugmodle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qinfeng on 2016/10/18.
 */

public class ParamsHelper {
    private HashMap<String,Object> params;
    private List<HashMap<String,Object>> platforms;

    private static ParamsHelper instance;

    public static ParamsHelper getInstance(){

        if(instance == null){
            synchronized (ParamsHelper.class){
                if(instance == null){
                    instance = new ParamsHelper();
                }
            }
        }

        return instance;
    }

    private ParamsHelper(){
        params = new HashMap<>();
        platforms = new ArrayList<>();
    }

    public void putSharePara(String key,Object value){
        params.put(key,value);
    }

    public void setShareTitle(String title){
        params.put(PlugConstants.TITLE,title);
    }

    public void setShareText(String text){
        params.put(PlugConstants.TEXT,text);
    }

    public void setShareTargetUrl(String url){
        params.put(PlugConstants.TARGET_URL,url);
    }

    public void setShareImage(String url){
        params.put(PlugConstants.IMAGE_URL,url);
    }

    public void setShareMediars(String[] mediars){
        params.put(PlugConstants.SHARE_MEDIAS,mediars);
    }

    public void putInitParam(String platform,String appId,String appKey){
        HashMap<String,Object> platformMap = new HashMap<>();
        platformMap.put(PlugConstants.PLATFORM,platform);
        platformMap.put(PlugConstants.APPID,appId);
        platformMap.put(PlugConstants.APPKEY,appKey);
        platforms.add(platformMap);
    }

    public void cleanPlateforms(){
        platforms.clear();
    }

    public void cleanParams(){
        params.clear();
    }

    public HashMap<String,Object> getParams(){
        params.put(PlugConstants.INIT,platforms);
        return params;
    }
}
