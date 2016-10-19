package com.apkplug.cam360plug;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallSLPlugListener;
import com.cam360.common.ICam360Start;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.bundlerpc.functions.Action2;
import org.apkplug.app.FrameworkFactory;

import java.io.File;

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

//        PlugManager.getInstance().installAssets(this, "cam360plug-debug.apk", "1.0.0", new OnInstallListener() {
//            @Override
//            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {
//
//            }
//
//            @Override
//            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
//                Log.e("isntall s",bundle.getName());
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

        PlugManager.getInstance().installPlugFromShortLink("http://yyfr.net/q1z", new OnInstallSLPlugListener() {
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


//        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
//        HashMap<String,Object> hashMap = new HashMap<>();
        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath() + File.separator+"test.png";
//        hashMap.put("ditPath",folderPath);
//        dispatchAgent.call("apkplug://cam360/start", hashMap, new WorkerCallback() {
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

        BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
        try {
            ICam360Start iCam360Start = agent.syncCall("apkplug://cam360/rpc/start", ICam360Start.class);
            iCam360Start.start(folderPath, new Action2<Boolean, String>() {
                @Override
                public void call(Boolean aBoolean, String s) {
                    Log.e("callback",aBoolean+" "+s);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

}
