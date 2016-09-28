package com.apkplug.umshareplug;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.apkplug.gesture.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.apkplug.Bundle.dispatch.DispatchAgent;

import java.lang.reflect.Field;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    HashMap<String,Object> hashMap ;
    public  static int msgid;
    int first = 0;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("oncreate","create in");
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        msgid = getIntent().getIntExtra(ShareProessor.MSGID,0);
        hashMap = (HashMap<String, Object>) getIntent().getSerializableExtra(ShareProessor.PARAMMAP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume","resume");
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
        Log.e("fc","ffffcccc"+first+" "+hasFocus);
        if(hasFocus){
            first++;
            if(first>=2){
                finish();
            }
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

        Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.RED);
        imageView.setImageBitmap(bitmap);
        UMImage umImage = new UMImage(this,bitmap);

        UMImage umImage1 = new UMImage(this, BitmapFactory.decodeResource(getResources(),R.drawable.sina_web_default));
        imageView.setImageBitmap(umImage1.asBitmap());
        new ShareAction(MainActivity.this).setDisplayList( displaylist )
                .withTitle("tasdfa")
                .withText("fawkfjlkadsm")
                .withTargetUrl("https://baidu.com")
                .withMedia(umImage1)
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
                        finish();
                    }
                })
                .open();
    }

    void share2(HashMap<String,Object> hashMap, final int msgid){

        final DispatchAgent dispatchAgent = new DispatchAgent(BaseProcessor.context);

        Log.e("share2","inini");

        String[] medias = (String[]) hashMap.get(PlugConstants.SHARE_MEDIAS);
        if(medias == null){
            dispatchAgent.reply(msgid,false,"share medias is null");
            return;
        }
        final SHARE_MEDIA[] displaylist = getShareMedias(medias);

        try {
            String text = (String) hashMap.get(PlugConstants.TEXT);
            String title = (String) hashMap.get(PlugConstants.TITLE);
            Integer imageRes = (Integer) hashMap.get(PlugConstants.IMAGE_RES);
            Log.e("imageres",imageRes+"");
            String imageUrl = (String) hashMap.get(PlugConstants.IMAGE_URL);
            Bitmap bitmap = (Bitmap) hashMap.get(PlugConstants.BITMAP);

            UMImage umImage = null;
            if(imageRes != null){
                umImage = new UMImage(this,imageRes);
            }

            if(imageUrl != null){
                umImage = new UMImage(this,imageUrl);
            }

            if(bitmap != null){
                Log.e("bitmap","not null");
                umImage = new UMImage(this,bitmap);
                imageView.setImageBitmap(bitmap);
            }

            String targetUrl = (String) hashMap.get(PlugConstants.TARGET_URL);

            final ShareAction shareAction = new ShareAction(this);


            shareAction.setShareboardclickCallback(new ShareBoardlistener() {
                @Override
                public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                    first--;
                    Class c = shareAction.getClass();
                    try {
                        Field fs = c.getDeclaredField("n");
                        fs.setAccessible(true);
                        ShareBoardlistener shareBoardlistener = (ShareBoardlistener) fs.get(shareAction);
                        shareBoardlistener.onclick(snsPlatform,share_media);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }  catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
            shareAction.setDisplayList( displaylist );
            if(text != null){
                Log.e("text",text);
                shareAction.withText(text);
            }
            if(title != null){
                Log.e("title",title);
                shareAction.withTitle(title);
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
                    Log.e("shareresult",share_media.name());
                    dispatchAgent.reply(msgid,true,share_media.name());
                }

                @Override
                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                    dispatchAgent.reply(msgid,false,throwable);
                    Log.e("shareerror",share_media.name()+""+throwable.getMessage());
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media) {
                    dispatchAgent.reply(msgid,false,"cancel");
                    Log.e("sharecancel",share_media.name());
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
