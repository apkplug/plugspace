package com.apkplug.umshareplug;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.apkplug.gesture.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.apkplug.Bundle.dispatch.DispatchAgent;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    HashMap<String,Object> hashMap ;
    int msgid;
    int first = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("oncreate","create in");
        setContentView(R.layout.activity_main);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] mPermissionList = new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.GET_ACCOUNTS};
                ActivityCompat.requestPermissions(MainActivity.this,mPermissionList, 100);

            }

        msgid = getIntent().getIntExtra(ShareProessor.MSGID,0);
        hashMap = (HashMap<String, Object>) getIntent().getSerializableExtra(ShareProessor.PARAMMAP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                share2(hashMap,msgid);
            }
        },100);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        first++;
        if(hasFocus && first>=2){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    public void share(){
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                };
        new ShareAction(MainActivity.this).setDisplayList( displaylist )
                .withTitle("tasdfa").withText("fawkfjlkadsm")
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.e("share r",share_media.name());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.e("share e",share_media.name()+""+throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.e("share c",share_media.name());
                    }
                })
                .open();
    }

    void share2(HashMap<String,Object> hashMap, final int msgid){

        Log.e("share2","inini");

        final DispatchAgent dispatchAgent = new DispatchAgent(BaseProcessor.context);

        String[] medias = (String[]) hashMap.get(PlugConstants.SHARE_MEDIAS);
        if(medias == null){
            dispatchAgent.reply(msgid,false,"share medias is null");
            return;
        }
        final SHARE_MEDIA[] displaylist = getShareMedias(medias);

        try {
            String text = (String) hashMap.get(PlugConstants.TEXT);
            Integer imageRes = (Integer) hashMap.get(PlugConstants.IMAGE_RES);
            Log.e("imageres",imageRes+"");
            String imageUrl = (String) hashMap.get(PlugConstants.IMAGE_URL);
            UMImage umImage = null;
            if(imageRes != null){
                umImage = new UMImage(this,imageRes);
            }else if(imageUrl != null){
                umImage = new UMImage(this,imageUrl);
            }
            String targetUrl = (String) hashMap.get(PlugConstants.TARGET_URL);

            ShareAction shareAction = new ShareAction(this);
            shareAction.setDisplayList( displaylist );
            if(text != null){
                Log.e("text",text);
                shareAction.withText(text);
            }
            if(umImage != null){
                Log.e("umImage",umImage.toString());
                shareAction.withMedia(umImage);
            }
            if(targetUrl != null){
                Log.e("targeturl",targetUrl);
                shareAction.withTargetUrl(targetUrl);
            }
            if(shareAction == null){
                dispatchAgent.reply(msgid,false,"shareAction is null");
                return;
            }
            shareAction.setCallback(new UMShareListener() {
                @Override
                public void onResult(SHARE_MEDIA share_media) {
                    Log.e(getClass().getName(),share_media.name());
                    dispatchAgent.reply(msgid,true,share_media.name());
                }

                @Override
                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                    dispatchAgent.reply(msgid,false,throwable);
                    Log.e(getClass().getName(),share_media.name()+""+throwable.getMessage());
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media) {
                    dispatchAgent.reply(msgid,false,"cancel");
                    Log.e(getClass().getName(),share_media.name());
                }
            });
            shareAction.open();
        } catch (Exception e) {
            e.printStackTrace();
            dispatchAgent.reply(msgid,false,e);
        }
    }


    private SHARE_MEDIA[] getShareMedias(String[] shareMedias){
        SHARE_MEDIA[] result = new SHARE_MEDIA[shareMedias.length];
        for(int i=0;i<shareMedias.length;i++){
            result[i] = chageMedias(shareMedias[i]);
        }
        return result;
    }

    private SHARE_MEDIA chageMedias(String name){
        if(PlugConstants.WEIXIN.equals(name)){
            return SHARE_MEDIA.WEIXIN;
        }
        if(PlugConstants.QQ.equals(name)){
            return SHARE_MEDIA.QQ;
        }
        if(PlugConstants.QZONE.equals(name)){
            return SHARE_MEDIA.QZONE;
        }
        if(PlugConstants.SINA.equals(name)){
            return SHARE_MEDIA.SINA;
        }
        return SHARE_MEDIA.QZONE;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
