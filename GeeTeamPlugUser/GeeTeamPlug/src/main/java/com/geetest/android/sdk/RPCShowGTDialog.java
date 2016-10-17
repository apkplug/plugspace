package com.geetest.android.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.common.IGTRPCCallback;
import com.common.IShowGTDialog;
import com.geetest.android.MainActivitor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinfeng on 2016/10/12.
 */

public class RPCShowGTDialog implements IShowGTDialog {

    Activity activity;
    IGTRPCCallback callback;
    @Override
    public void showGTDialog(Activity activity, String firstUri, String secondUri, int timeout, final IGTRPCCallback callback) {
        String firstUrl = firstUri;
        String secondUrl = secondUri;
        this.activity = activity;
        this.callback = callback;

        Log.e("in call","in the rpc");

        captcha = new Geetest(

                // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
//                "http://webapi.geelao.ren:8011/gtcap/start-mobile-captcha/",
                firstUrl,

                // 设置二次验证的URL，需替换成自己的服务器URL
//                "http://webapi.geelao.ren:8011/gtcap/gt-server-validate/"
                secondUrl
        );

        captcha.setTimeout(timeout);

        captcha.setGeetestListener(new Geetest.GeetestListener() {
            @Override
            public void readContentTimeout() {
                //dispatchAgent.reply(getMsgId(),false,"read content timeout");
                callback.onFail("read content timeout");
            }

            @Override
            public void submitPostDataTimeout() {
                //dispatchAgent.reply(getMsgId(),false,"submit post data timeout");
                callback.onFail("submit post data timeout");
            }
        });

        new GtAppDlgTask().execute();
    }


    private Geetest captcha;


    class GtAppDlgTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            return captcha.checkServer();
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                // 根据captcha.getSuccess()的返回值 自动推送正常或者离线验证
                openGtTest(activity, captcha.getGt(), captcha.getChallenge(), captcha.getSuccess());

            } else {

                // TODO 从API_1获得极验服务宕机或不可用通知, 使用备用验证或静态验证
                // 静态验证依旧调用上面的openGtTest(_, _, _), 服务器会根据getSuccess()的返回值, 自动切换
                Toast.makeText(
                        MainActivitor.bundleContext.getBundleContext(),
                        "Geetest Server is Down.",
                        Toast.LENGTH_LONG).show();

                // 执行此处网站主的备用验证码方案
                openGtTest(activity,captcha.getGt(),captcha.getChallenge(),captcha.getSuccess());
            }
        }
    }

    public void openGtTest(Context ctx, String id, String challenge, boolean success) {

        GtDialog dialog = new GtDialog(ctx, id, challenge, success);

        // 启用debug可以在webview上看到验证过程的一些数据
//        dialog.setDebug(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //TODO 取消验证
                toastMsg("user close the geetest.");
                //dispatchAgent.reply(getMsgId(),false,"cancel");
                callback.onCancel("cancel");
            }
        });

        dialog.setGtListener(new GtDialog.GtListener() {

            @Override
            public void gtResult(boolean success, String result) {

                if (success) {

                    toastMsg("client captcha succeed:" + result);

                    try {
                        JSONObject res_json = new JSONObject(result);

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("geetest_challenge", res_json.getString("geetest_challenge"));

                        params.put("geetest_validate", res_json.getString("geetest_validate"));

                        params.put("geetest_seccode", res_json.getString("geetest_seccode"));

                        String response = captcha.submitPostData(params, "utf-8");

                        //TODO 验证通过, 获取二次验证响应, 根据响应判断验证是否通过完整验证
                        toastMsg("server captcha :" + response);

                        //dispatchAgent.reply(getMsgId(),true,response);
                        callback.onSuccess(response);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                } else {
                    //TODO 验证失败
                    toastMsg("client captcha failed:" + result);
                    //dispatchAgent.reply(getMsgId(),false,"local captcha failed");
                    callback.onFail("local captcha failed");
                }
            }

            @Override
            public void gtCallClose() {

                toastMsg("close geetest windows");
                //dispatchAgent.reply(getMsgId(),false,"close geetest windows");
                callback.onFail("close geetest windows");
            }

            @Override
            public void gtCallReady(Boolean status) {

                if (status) {
                    //TODO 验证加载完成
                    toastMsg("geetest finish load");
                }else {
                    //TODO 验证加载超时,未准备完成
                    toastMsg("there's a network jam");
                }
            }

        });

    }
    private void toastMsg(String msg) {

        Toast.makeText(MainActivitor.bundleContext.getBundleContext(), msg, Toast.LENGTH_SHORT).show();

    }
}
