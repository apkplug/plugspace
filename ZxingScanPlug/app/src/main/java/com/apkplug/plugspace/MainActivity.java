package com.apkplug.plugspace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnGetRPCInstanceListener;
import com.apkplug.trust.common.listeners.OnInstallSLPlugListener;
import com.common.IZxingStart;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.bundlerpc.functions.Action2;
import org.apkplug.app.FrameworkFactory;

public class MainActivity extends AppCompatActivity {
    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

    IZxingStart iZxingStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        PlugManager.getInstance().installAssets(this, "zXingPlug-debug.apk", "1.0.0", new OnInstallListener() {
//            @Override
//            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {
//
//            }
//
//            @Override
//            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
//                Log.e("install s",bundle.getName());
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

//        PlugManager.getInstance().installPlugFromShortLink("http://yyfr.net/q1B", new OnInstallSLPlugListener() {
//            @Override
//            public void onDownloadProgress(String s, String s1, long l, long l1) {
//
//            }
//
//            @Override
//            public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
//                Log.e("isntall s",bundle.getName());
//            }
//
//            @Override
//            public void onInstallFailuer(int i, String s) {
//                Log.e("isntall f",s);
//            }
//
//            @Override
//            public void onDownloadFailure(String s) {
//                Log.e("download f",s);
//            }
//        });



        PlugManager.getInstance().getRPCInstanceByShortLink("http://yyfr.net/q1B", "apkplug://zxing/rpc/start", IZxingStart.class, new OnGetRPCInstanceListener<IZxingStart>() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                Log.e("install s",bundle.getName());
            }

            @Override
            public void onGetRPCSuccess(IZxingStart iZxingStart) {
                MainActivity.this.iZxingStart = iZxingStart;
                Log.e("rpc",iZxingStart.toString());
            }

            @Override
            public void onFail(String s, String s1) {
                Log.e("fail",s+" "+s1);
            }
        });
    }

    public void onClick(View view){
//        DispatchAgent dispatchAgent = new DispatchAgent(PlugManager.getInstance().getBundleContext());
//        dispatchAgent.call("apkplug://zxing/start", null, new WorkerCallback() {
//            @Override
//            public void reply(URI uri, Object... objects) throws Exception {
//                if((boolean)objects[0]){
//                    ((Activity)objects[3]).finish();
//
//                    Toast.makeText(MainActivity.this,(String)objects[1],Toast.LENGTH_LONG).show();
//
//                }else {
//                    Log.e("call s f",objects[1].toString());
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
//                Log.e("call e",throwable.getMessage());
//            }
//        });

        //BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
        try {
            //IZxingStart iZxingStart = agent.syncCall("apkplug://zxing/rpc/start",IZxingStart.class);
            iZxingStart.startScan(new Action2<Boolean, String>() {
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
