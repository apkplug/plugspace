package com.apkplug.qlivepluguser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;

import java.net.URI;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String MOBLE = "mobile";
    public static final String PASSWORD = "password";
    public static final String CONCTEXT = "context";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),"fasdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlugManager.getInstance().installAssets(this, "plug1-debug.apk", "v1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
                Log.e("install f",s+"  "+i+" "+plugInfo.getPlug_name());
                startQlive();
            }



            @Override
            public void onDownloadFailure(String s) {

            }
        });

    }



    public void onClick(View view){
        startQlive();
    }


    private void startQlive() {

        Log.e("run in main",Thread.currentThread().getName());

        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        hashMap.put(MOBLE,"18801425179");
        hashMap.put(PASSWORD,"@Aa123456");
        hashMap.put(CONCTEXT,this);
        dispatchAgent.call("http://apkplug.plug.com/qlive/login", hashMap, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {
                if((boolean)objects[0]){
                    Log.e("callback","start success");
                }else {
                    Log.e("callback","start fail");
                }
            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                Log.e("exception",throwable.getMessage());
            }
        });
    }
}
