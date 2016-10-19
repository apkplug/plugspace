package com.apkplug.easemobplug;

import android.content.Intent;

import com.apkplug.easemobplug.ui.LoginActivity;
import com.ease.common.IEaseInstance;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.apkplug.Bundle.bundlerpc.ObjectPool;
import org.apkplug.Bundle.bundlerpc.functions.Action2;

import java.util.List;

/**
 * Created by qinfeng on 2016/10/13.
 */

public class EaseInstancImp implements IEaseInstance {
    @Override
    public void loginByCode(String userName, String passWord, final Action2<Boolean, String> callback) {

        if(userName == null || passWord == null){
            //dispatchAgent.reply(getMsgId(),false,new Exception("your username or password is null"));
            callback.call(false,"your username or password is null");
            return;
        }

        EMClient.getInstance().login(userName, passWord, new EMCallBack() {
            @Override
            public void onSuccess() {
                //dispatchAgent.reply(getMsgId(),true,"success");
                callback.call(true,"success");
            }

            @Override
            public void onError(int i, String s) {
                //dispatchAgent.reply(getMsgId(),false,s);
                callback.call(false,s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    public void loginByView(String userName, String password, Action2<Boolean, String> callback) {
        try {
            Intent intent = new Intent(MainActivitor.bundleContext.getBundleContext(), LoginActivity.class);

            intent.putExtra("rpc_callback",new ObjectPool<Action2<Boolean,String>>(callback));

            MainActivitor.bundleContext.getBundleContext().startActivity(intent);
            //dispatchAgent.reply(getMsgId(),true);

        } catch (Exception e) {
            e.printStackTrace();
            //dispatchAgent.reply(getMsgId(),false,e);
            callback.call(false,e.getMessage());
        }
    }

    @Override
    public boolean addFriend(String username) {
        try {
            EMClient.getInstance().contactManager().addContact(username, "you have to accept");
            //dispatchAgent.reply(getMsgId(),true,"success");
            return true;
        } catch (HyphenateException e) {
//            dispatchAgent.reply(getMsgId(),false,e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getContents() {
       List<String> allFirends = null;
       try {
           allFirends = EMClient.getInstance().contactManager().getAllContactsFromServer();
           //dispatchAgent.reply(getMsgId(),true,allFirends);
       } catch (HyphenateException e) {
           //dispatchAgent.reply(getMsgId(),false,e);
       }

        return allFirends;
    }

    @Override
    public boolean regist(String userName, String password) {
        if(userName == null || password == null){
            //dispatchAgent.reply(getMsgId(),false,new Exception("username or password is null"));
            return false;
        }
        try {
            EMClient.getInstance().createAccount(userName,password);
            //dispatchAgent.reply(getMsgId(),true,"success");
            return true;
        } catch (HyphenateException e) {
//            dispatchAgent.reply(getMsgId(),false,e);
            e.printStackTrace();
        }
        return false;
    }
}
