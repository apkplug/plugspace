package com.apkplug.kf5plug;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/23.
 */
public class UserInfoProcessor extends BaseProcessor{

    public UserInfoProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        UserHelper.userName = (String) hashMap.get(UserHelper.USERNAME);
        UserHelper.userPhone = (String) hashMap.get(UserHelper.USERPHONE);
        UserHelper.userEmail = (String) hashMap.get(UserHelper.USEREMAIL);
        UserHelper.appId = (String) hashMap.get(UserHelper.APPID);
        UserHelper.helpAddress = (String) hashMap.get(UserHelper.HELPADDRESS);

        dispatchAgent.reply(getMsgId(),true,"success");
    }
}
