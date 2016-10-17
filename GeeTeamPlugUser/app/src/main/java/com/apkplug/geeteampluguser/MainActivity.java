package com.apkplug.geeteampluguser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnCallDPBySLListener;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;
import com.common.IGTRPCCallback;
import com.common.IShowGTDialog;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;

import java.net.URI;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FIRSTURL = "firstUrl";
    public static final String SECONDURL = "secondUrl";
    public static final String TIEMOUT = "timeout";

    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        try {
            PlugManager.getInstance().init(this,FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put(FIRSTURL,"http://webapi.geelao.ren:8011/gtcap/start-mobile-captcha/");
//        hashMap.put(SECONDURL,"http://webapi.geelao.ren:8011/gtcap/gt-server-validate/");
//        hashMap.put(TIEMOUT,5000);
//        hashMap.put("activity",this);
//
//        PlugManager.getInstance().callDispatchWithShortLink("http://yyfr.net/q1v", "apkplug://geeteam/start", hashMap, new OnCallDPBySLListener() {
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
//            public void onDispatcherReply(URI uri, Object... objects) throws Exception {
//                Log.e("reply",objects[0].toString());
//            }
//
//            @Override
//            public void onFail(String s, String s1) {
//                Log.e("fail",s+" "+s1);
//            }
//        });
//
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view){
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put(FIRSTURL,"http://webapi.geelao.ren:8011/gtcap/start-mobile-captcha/");
//        hashMap.put(SECONDURL,"http://webapi.geelao.ren:8011/gtcap/gt-server-validate/");
//        hashMap.put(TIEMOUT,5000);
//        hashMap.put("activity",this);
//        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
//        dispatchAgent.call("apkplug://geeteam/start", hashMap, new WorkerCallback() {
//            @Override
//            public void reply(URI uri, Object... objects) throws Exception {
//                Log.e("reple",objects[0].toString());
//            }
//
//            @Override
//            public void timeout(URI uri) throws Exception {
//
//            }
//
//            @Override
//            public void Exception(URI uri, Throwable throwable) {
//                throwable.printStackTrace();
//                Log.e("exception",throwable.getMessage());
//            }
//        });

        BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

        try {
            IShowGTDialog iShowGTDialog = agent.syncCall("apkplug://geeteam/rpc/start",IShowGTDialog.class);
            if(iShowGTDialog == null){
                Log.e("null","showinterface is null");
            }
            iShowGTDialog.showGTDialog(MainActivity.this,
                    "http://webapi.geelao.ren:8011/gtcap/start-mobile-captcha/",
                    "http://webapi.geelao.ren:8011/gtcap/gt-server-validate/",
                    5000,
                    new IGTRPCCallback() {
                        @Override
                        public void onSuccess(String msg) {
                            Log.e("success",msg);
                        }

                        @Override
                        public void onFail(String msg) {
                            Log.e("fail",msg);
                        }

                        @Override
                        public void onCancel(String msg) {
                            Log.e("cancel",msg);
                        }
                    });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
//        Intent intent = new Intent();
//        intent.setClassName(this,"com.geetest.android.demo.MainActivity");
//        startActivity(intent);
    }
}
