package com.qiniu.qlive.processores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.qiniu.qlive.activity.MainActivity;
import com.qiniu.qlive.service.UserService;
import com.qiniu.qlive.service.result.LoginResult;
import com.qiniu.qlive.utils.Account;
import com.qiniu.qlive.utils.Tools;

import com.qiniu.qlive.config.APICode;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/22.
 */
public class LoginProcessor extends BaseProcessor {

    public static final String MOBLE = "mobile";
    public static final String PASSWORD = "password";
    public static final String CONCTEXT = "context";

    public LoginProcessor(BundleContext context) {
        super(context);
        Log.e("init ","init thread "+Thread.currentThread().getName());
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        final String mobile = (String) hashMap.get(MOBLE);
        final String password = (String) hashMap.get(PASSWORD);
        final Context context = (Context) hashMap.get(CONCTEXT);

        Log.e("plug in",mobile+" "+password+" "+context.getPackageName());

        //start(mobile,password,context);

        new Thread(){
            @Override
            public void run() {
                Log.e("run in",Thread.currentThread().getName());

                LoginProcessor.this.start(mobile, password, context);
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
                    context.startActivity(intent);
                    dispatchAgent.reply(getMsgId(),true,"success");
                    break;
                case APICode.API_USER_NOT_FOUND_ERROR:
                    Tools.showToast(context, "该用户不存在，请注册！");
                    dispatchAgent.reply(getMsgId(),false,"user no fond");
                    break;
                case APICode.API_USER_PWD_ERROR:
                    Tools.showToast(context, "密码输入错误！");
                    dispatchAgent.reply(getMsgId(),false,"password wrong");

                    break;
                case APICode.API_SERVER_ERROR:
                    Tools.showToast(context, "服务器内部错误，请稍后重试！");
                    dispatchAgent.reply(getMsgId(),false,"server fail");
                    break;
                default:
                    Tools.showToast(context, String.format("未知错误，%d %s", loginResult.getCode(), loginResult.getDesc()));
                    dispatchAgent.reply(getMsgId(),false,"unknown error");
            }
        } else {
            Tools.showToast(context, "请求失败，请检查网络状况！");
            dispatchAgent.reply(getMsgId(),false,"net error");
        }
    }
}
