package com.apkplug.umsharepluguser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnCallDPBySLListener;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugDownloadState;
import com.apkplug.trust.data.PlugInfo;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DispatchAgent dispatchAgent;
    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);

        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY,true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("init fail",e.getMessage());
        }

        dispatchAgent = new DispatchAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

        PlugManager.getInstance().installAssets("umplug-debug.apk", "1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                //callUMShareInit();

            }

            @Override
            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
                Log.e(getClass().getName(),"install fail "+s);
                //callUMShareInit();

            }

            @Override
            public void onDownloadFailure(String s) {

            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    private void callUMShareInit() {
        HashMap<String,Object> hashMapwx = new HashMap<String, Object>();
        hashMapwx.put(PlugConstants.PLATFORM,PlugConstants.PLATFORM_WIEXIN);
        hashMapwx.put(PlugConstants.APPID,"wxe11d7c9e16d9556e");
        hashMapwx.put(PlugConstants.APPKEY,"d1b94ae7e1ca1e122f2d04f2d402fc99");

        //PlatformConfig.setQQZone("1105439618", "xV30cmR6uB83n1dr");
        HashMap<String,Object> hashMapqz = new HashMap<>();
        hashMapqz.put(PlugConstants.PLATFORM,PlugConstants.PLATFORM_QQ);
        hashMapqz.put(PlugConstants.APPID,"1105439618");
        hashMapqz.put(PlugConstants.APPKEY,"xV30cmR6uB83n1dr");

        List<HashMap<String,Object>> plateforms = new ArrayList<>();
        plateforms.add(hashMapwx);
        plateforms.add(hashMapqz);
        HashMap<String,Object> params = new HashMap<>();
        params.put(PlugConstants.INIT,plateforms);
        dispatchAgent.call("apkplug://umshare/init", params, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {
                if((boolean)objects[0]){
                    Log.e(getClass().getName(), (String) objects[1]);
                }else {
                    Log.e(getClass().getName(),"call fail "+objects[1]);
                }
            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                Log.e(getClass().getName(),"call fail 2"+throwable.getMessage());
            }
        });
    }

    void share(){

        ParamsHelper paramsHelper = ParamsHelper.getInstance();
        paramsHelper.putInitParam(PlugConstants.PLATFORM_WIEXIN,"wxe11d7c9e16d9556e","d1b94ae7e1ca1e122f2d04f2d402fc99");
        paramsHelper.putInitParam(PlugConstants.PLATFORM_QQ,"1105439618","xV30cmR6uB83n1dr");
        paramsHelper.putInitParam(PlugConstants.PLATFORM_SINA,"3921700954","04b48b094faeb16683c32669824ebdad");
        paramsHelper.setShareTitle("fasdfas");
        paramsHelper.setShareText("fasdfasfasfasfsafaf");
        paramsHelper.setShareTargetUrl("https://baidu.com");
        paramsHelper.setShareMediars(new String[]{PlugConstants.WEIXIN,PlugConstants.QQ,PlugConstants.QZONE,PlugConstants.SINA});

        HashMap<String,Object> hashMap = paramsHelper.getParams();

        Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.RED);
        imageView.setImageBitmap(bitmap);
        hashMap.put(PlugConstants.BITMAP,bitmap);

        PlugManager.getInstance().callDispatchWithShortLink("http://yyfr.net/q1t", "apkplug://umshare/share", hashMap, new OnCallDPBySLListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                Log.e("install s",bundle.getName());
            }

            @Override
            public void onDispatcherReply(URI uri, Object... objects) throws Exception {
                Log.e("reply",objects[0].toString());
            }

            @Override
            public void onFail(String s, String s1) {
                Log.e("fail",s+" "+s1);
            }
        });

    }
}
