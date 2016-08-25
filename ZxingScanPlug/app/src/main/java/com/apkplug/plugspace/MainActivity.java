package com.apkplug.plugspace;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),"fasdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlugManager.getInstance().installAssets(this, "zXingPlug-debug.apk", "1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                Log.e("install s",bundle.getName());
            }

            @Override
            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
                Log.e("install f",s);
            }

            @Override
            public void onDownloadFailure(String s) {

            }
        });
    }

    public void onClick(View view){
        DispatchAgent dispatchAgent = new DispatchAgent(PlugManager.getInstance().getBundleContext());
        dispatchAgent.call("http://apkplug.plug.com/zxing/start", null, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {
                if((boolean)objects[0]){
                    ((Activity)objects[3]).finish();

                    Toast.makeText(MainActivity.this,(String)objects[1],Toast.LENGTH_LONG).show();

                }else {
                    Log.e("call s f",objects[1].toString());
                }
            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                Log.e("call e",throwable.getMessage());
            }
        });
    }

}
