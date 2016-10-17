package com.apkplug.ciapluguser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;
import com.common.ICaiVerifyCallback;
import com.common.ICiaInit;
import com.common.ICiaVerify;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;

import java.net.URI;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText mPhoneEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取手机号码输入框
        mPhoneEt = (EditText) findViewById(R.id.et_phone);

        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),"fsa");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlugManager.getInstance().installAssets(this, "caiplug-debug.apk", "1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                Log.e("isntall s",bundle.getName());

                BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

                try {
                    ICiaInit iCiaInit = agent.syncCall("apkplug://cia/rpc/init", ICiaInit.class);
                    boolean init = iCiaInit.init("6c16f74868044396a3d0bcd2443b7f0c", "338611e4c15e4152bf59ca71e742a269");
                    Log.e("init",init+"");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }

            @Override
            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
                Log.e("install f",s);
                //initcia();
                BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

                try {
                    ICiaInit iCiaInit = agent.syncCall("apkplug://cia/rpc/init", ICiaInit.class);
                    boolean init = iCiaInit.init("6c16f74868044396a3d0bcd2443b7f0c", "338611e4c15e4152bf59ca71e742a269");
                    Log.e("init",init+"");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onDownloadFailure(String s) {

            }
        });


    }

//    private void initcia() {
//        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("AppId","6c16f74868044396a3d0bcd2443b7f0c");
//        hashMap.put("AuthKey","338611e4c15e4152bf59ca71e742a269");
//        dispatchAgent.call("apkplug://cia/init", hashMap, new WorkerCallback() {
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
//    }


    public void onClick(View view){
//        DispatchAgent dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
//
//        HashMap<String,Object> params = new HashMap<String, Object>();
//        params.put("phone",mPhoneEt.getText().toString());
//        params.put("AppId","6c16f74868044396a3d0bcd2443b7f0c");
//        params.put("AuthKey","338611e4c15e4152bf59ca71e742a269");
//        dispatchAgent.call("apkplug://cia/verify", params, new WorkerCallback() {
//            @Override
//            public void reply(URI uri, Object... objects) throws Exception {
//                Log.e("reply",objects[0].toString()+" "+objects[1]);
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
            ICiaVerify verify = agent.syncCall("apkplug://cia/rpc/verify", ICiaVerify.class);
            String phone = mPhoneEt.getText().toString();
            if(phone==null||"".equals(phone)){
                Log.e("error","电话号码不对");
                return;
            }
            verify.verify("6c16f74868044396a3d0bcd2443b7f0c", "338611e4c15e4152bf59ca71e742a269", phone, new ICaiVerifyCallback() {
                @Override
                public void onSuccess(String msg) {
                    Log.e("success",msg);
                }

                @Override
                public void onFail(String msg) {
                    Log.e("fail",msg);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
