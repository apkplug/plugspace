package com.qiniu.qlive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.qlive.common.IQLLogin;
import com.qiniu.qlive.activity.MainActivity;
import com.qiniu.qlive.config.APICode;
import com.qiniu.qlive.service.UserService;
import com.qiniu.qlive.service.result.LoginResult;
import com.qiniu.qlive.utils.Account;
import com.qiniu.qlive.utils.Tools;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/10/13.
 */

public class RPCQLiveLogin implements IQLLogin {

    Action2<Boolean,String> action2;

    @Override
    public void login(final Context context, String phone, final String password, Action2<Boolean, String> callback) {
        final String mobile = phone;

        Log.e("plug in",mobile+" "+password+" "+context.getPackageName());

        //start(mobile,password,context);
        action2 = callback;
        new Thread(){
            @Override
            public void run() {
                Log.e("run in",Thread.currentThread().getName());

                RPCQLiveLogin.this.start(mobile, password, context);
            }
        }.start();

    }

    private void start(String mobile, String password, Context context) {
        LoginResult loginResult = UserService.login(mobile, password);
        if (loginResult != null) {
            switch (loginResult.getCode()) {
                case APICode.API_OK:
                    Account.save(context, mobile, password);
                    Tools.writeSession(context, loginResult.getSessionId(), loginResult.getUserName());
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //dispatchAgent.reply(getMsgId(),true,"success");
                    action2.call(true,"success");
                    break;
                case APICode.API_USER_NOT_FOUND_ERROR:
                    Tools.showToast(context, "该用户不存在，请注册！");
                    //dispatchAgent.reply(getMsgId(),false,"user no fond");
                    action2.call(false,"该用户不存在，请注册！");
                    break;
                case APICode.API_USER_PWD_ERROR:
                    Tools.showToast(context, "密码输入错误！");
                    //dispatchAgent.reply(getMsgId(),false,"password wrong");
                    action2.call(false,"密码输入错误！");
                    break;
                case APICode.API_SERVER_ERROR:
                    Tools.showToast(context, "服务器内部错误，请稍后重试！");
                    //dispatchAgent.reply(getMsgId(),false,"server fail");
                    action2.call(false,"服务器内部错误，请稍后重试！");
                    break;
                default:
                    Tools.showToast(context, String.format("未知错误，%d %s", loginResult.getCode(), loginResult.getDesc()));
                    //dispatchAgent.reply(getMsgId(),false,"unknown error");
                    action2.call(false,"unknown error");
            }
        } else {
            Tools.showToast(context, "请求失败，请检查网络状况！");
            //dispatchAgent.reply(getMsgId(),false,"net error");
            action2.call(false,"请求失败，请检查网络状况！");
        }
    }
}
