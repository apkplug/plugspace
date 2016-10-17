package com.apkplug.qlivepluguser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.common.listeners.OnInstallSLPlugListener;
import com.apkplug.trust.data.PlugInfo;
import com.common.IQLLogin;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.bundlerpc.functions.Action2;
import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;

import java.net.URI;
import java.security.PublicKey;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String MOBLE = "mobile";
    public static final String PASSWORD = "password";
    public static final String CONCTEXT = "context";

    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Log.e("publickey",PUBLICKEY);
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
            Log.e("publickey",PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        PlugManager.getInstance().installAssets(this, "plug1-debug.apk", "v1.0.0", new OnInstallListener() {
//            @Override
//            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {
//
//            }
//
//            @Override
//            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
//
//            }
//
//            @Override
//            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
//                Log.e("install f",s+"  "+i+" "+plugInfo.getPlug_name());
//                startQlive();
//            }
//
//
//
//            @Override
//            public void onDownloadFailure(String s) {
//
//            }
//        });

        PlugManager.getInstance().installPlugFromShortLink("http://yyfr.net/q21", new OnInstallSLPlugListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                Log.e("install s",bundle.getName());
            }

            @Override
            public void onInstallFailuer(int i, String s) {
                Log.e("install f",s);
            }

            @Override
            public void onDownloadFailure(String s) {
                Log.e("download f",s);
            }
        });

    }



    public void onClick(View view){
        startQlive();
    }


    private void startQlive() {

        Log.e("run in main",Thread.currentThread().getName());

//        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
//        HashMap<String,Object> hashMap = new HashMap<String, Object>();
//        hashMap.put(MOBLE,"18801425179");
//        hashMap.put(PASSWORD,"@Aa123456");
//        hashMap.put(CONCTEXT,this);
//        dispatchAgent.call("apkplug://qlive/login", hashMap, new WorkerCallback() {
//            @Override
//            public void reply(URI uri, Object... objects) throws Exception {
//                if((boolean)objects[0]){
//                    Log.e("callback","start success");
//                }else {
//                    Log.e("callback","start fail");
//                }
//            }
//
//            @Override
//            public void timeout(URI uri) throws Exception {
//
//            }
//
//            @Override
//            public void Exception(URI uri, Throwable throwable) {
//                Log.e("exception",throwable.getMessage());
//            }
//        });

        BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

        try {
            IQLLogin iqlLogin = agent.syncCall("apkplug://qlive/rpc/login",IQLLogin.class);
            iqlLogin.login(this, "18801245179", "@Aa123456", new Action2<Boolean, String>() {
                @Override
                public void call(Boolean aBoolean, String s) {
                    Log.e("callback",aBoolean+"  "+s);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
