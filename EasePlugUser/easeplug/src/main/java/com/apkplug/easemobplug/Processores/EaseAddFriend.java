package com.apkplug.easemobplug.Processores;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/7/22.
 */
public class EaseAddFriend extends BaseProcessor {
    public EaseAddFriend(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        String username = (String) hashMap.get("UserName");
        try {
            EMClient.getInstance().contactManager().addContact(username, "you have to accept");
            dispatchAgent.reply(getMsgId(),true,"success");
        } catch (HyphenateException e) {
            dispatchAgent.reply(getMsgId(),false,e);
        }
    }
}
