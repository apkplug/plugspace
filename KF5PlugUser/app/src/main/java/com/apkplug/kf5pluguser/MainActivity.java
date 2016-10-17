package com.apkplug.kf5pluguser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.common.listeners.OnInstallSLPlugListener;
import com.apkplug.trust.data.PlugInfo;
import com.common.IKF5OpenKfActivity;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.app.FrameworkFactory;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {
    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        PlugManager.getInstance().installAssets(this, "kf5plug-debug.apk", "1.0.0", new OnInstallListener() {
//            @Override
//            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {
//
//            }
//
//            @Override
//            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
//                Log.e("install s","success:"+plugInfo.getPlug_name());
//            }
//
//            @Override
//            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
//                Log.e("install f",s);
//            }
//
//            @Override
//            public void onDownloadFailure(String s) {
//
//            }
//        });

        PlugManager.getInstance().installPlugFromShortLink("http://yyfr.net/q1w", new OnInstallSLPlugListener() {
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

    public void onKefu(View v){
//        new DispatchAgent(PlugManager.getInstance().getBundleContext()).call("apkplug://kf5/openactivity", null, new WorkerCallback() {
//            @Override
//            public void reply(URI uri, Object... objects) throws Exception {
//                Log.e("reply",objects[0].toString());
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
//        Intent intent = new Intent();
        BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

        try {
            IKF5OpenKfActivity openKfActivity = agent.syncCall("apkplug://kf5/rpc/openactivity",IKF5OpenKfActivity.class);
            openKfActivity.openKF5("00157bbb168b36e8fb8146ed32afa43d8b546d1d85465c4f","diandou.kf5.com","test@kf.com");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
