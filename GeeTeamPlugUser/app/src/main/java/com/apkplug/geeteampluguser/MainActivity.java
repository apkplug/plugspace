package com.apkplug.geeteampluguser;

import android.content.Intent;
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

    public static final String FIRSTURL = "firstUrl";
    public static final String SECONDURL = "secondUrl";
    public static final String TIEMOUT = "timeout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PlugManager.getInstance().init(this,FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),"fsa");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlugManager.getInstance().installAssets(this, "GeeTeamPlug-debug.apk", "1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                Log.e("isntall s",bundle.getName());
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
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put(FIRSTURL,"http://webapi.geelao.ren:8011/gtcap/start-mobile-captcha/");
        hashMap.put(SECONDURL,"http://webapi.geelao.ren:8011/gtcap/gt-server-validate/");
        hashMap.put(TIEMOUT,5000);
        hashMap.put("activity",this);
        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
        dispatchAgent.call("http://apkplug.plug.com/geeteam/start", hashMap, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {
                Log.e("reple",objects[0].toString());
            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                throwable.printStackTrace();
                Log.e("exception",throwable.getMessage());
            }
        });
//        Intent intent = new Intent();
//        intent.setClassName(this,"com.geetest.android.demo.MainActivity");
//        startActivity(intent);
    }
}
