package com.apkplug.kf5pluguser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),"fasf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlugManager.getInstance().installAssets(this, "kf5plug-debug.apk", "1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                Log.e("install s","success:"+plugInfo.getPlug_name());
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

    public void onKefu(View v){
        new DispatchAgent(PlugManager.getInstance().getBundleContext()).call("http://apkplug.plug.com/kf5/openactivity", null, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {
                Log.e("reply",objects[0].toString());
            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                Log.e("exception",throwable.getMessage());
            }
        });
//        Intent intent = new Intent();
//        intent.setClassName(this,"com.apkplug.kf5plug.MainActivity");
//        startActivity(intent);
    }
}
