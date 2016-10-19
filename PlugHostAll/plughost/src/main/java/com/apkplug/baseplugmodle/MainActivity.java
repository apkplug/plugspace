package com.apkplug.baseplugmodle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkplug.baseplugmodle.pluginterface.ICam360Start;
import com.apkplug.baseplugmodle.pluginterface.IEaseInstance;
import com.apkplug.baseplugmodle.pluginterface.IGTRPCCallback;
import com.apkplug.baseplugmodle.pluginterface.IKF5OpenKfActivity;
import com.apkplug.baseplugmodle.pluginterface.IQLLogin;
import com.apkplug.baseplugmodle.pluginterface.IShowGTDialog;
import com.apkplug.baseplugmodle.pluginterface.IUMShare;
import com.apkplug.baseplugmodle.pluginterface.IZxingStart;
import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnGetRPCInstanceListener;
import com.apkplug.trust.common.listeners.OnInstallSLPlugListener;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public PlugListItem<IZxingStart> itemZxing;
    public PlugListItem<ICam360Start> itemCam360;
    public PlugListItem<IUMShare> itemUMShare;
    public PlugListItem<IShowGTDialog> itemGeeteam;
    public PlugListItem<IKF5OpenKfActivity> itemKf5;
    public PlugListItem<IQLLogin> itemQlive;
    public PlugListItem<IEaseInstance> itemEase;


    @Bind(R.id.floatingActionButton)
    FloatingActionButton button;

    @Bind(R.id.pluglist)
    RecyclerView pluglist;

    @Bind(R.id.textView)
    TextView textView;

    @Bind(R.id.plugbaidu)
    View viewBaidu;

    @Bind(R.id.pluggaode)
    View viewGaode;

    ProgressBar baiduBar;
    ProgressBar gaodeBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<PlugListItem> plugListItems = getPlugListItems();


        PlugListAdapter adapter = new PlugListAdapter();
        adapter.setPlugitems(plugListItems);

        pluglist.setLayoutManager(new LinearLayoutManager(this));
        pluglist.setAdapter(adapter);

    }

    @NonNull
    private List<PlugListItem> getPlugListItems() {
        itemZxing = new PlugListItem<IZxingStart>();
        itemZxing.rpcUri="apkplug://zxing/rpc/start";
        itemZxing.shortLink="http://yyfr.net/q1B";
        itemZxing.rpcInterfaceClass =IZxingStart.class;
        itemZxing.plugName="二维码扫描插件";
        itemZxing.mothedName="startScan";
        itemZxing.paramType = new Class[]{Action2.class};
        Object[] pam = new Object[1];
        pam[0] = new Action2<Boolean,String>() {

            @Override
            public void call(Boolean aBoolean, String s) {
                Log.e("callback",s);
            }
        };
        itemZxing.params=pam;

        itemCam360 = new PlugListItem<ICam360Start>();
        itemCam360.rpcUri = "apkplug://cam360/rpc/start";
        itemCam360.rpcInterfaceClass =ICam360Start.class;
        itemCam360.shortLink= "http://yyfr.net/q1z";
        itemCam360.plugName="Camare360插件";
        itemCam360.mothedName="start";
        itemCam360.paramType = new Class[]{String.class,Action2.class};
        Object[] parm = new Object[2];
        parm[0] = getCacheDir().getAbsolutePath()+ File.separator+"test.png";
        parm[1] = new Action2<Boolean,String>() {
            @Override
            public void call(Boolean aBoolean, String s) {
                Log.e("callback",s);
            }
        };
        itemCam360.params = parm;


        itemUMShare = new PlugListItem<IUMShare>();
        itemUMShare.rpcInterfaceClass = IUMShare.class;
        itemUMShare.shortLink = "http://yyfr.net/q1t";
        itemUMShare.rpcUri = "apkplug://umshare/rpc/share";
        itemUMShare.plugName = "友盟分享插件";
        itemUMShare.mothedName = "share";
        itemUMShare.paramType=new Class[]{HashMap.class,Action2.class};

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
        hashMap.put(PlugConstants.BITMAP,bitmap);

        itemUMShare.params = new Object[]{hashMap,new Action2<Boolean,String>() {
            @Override
            public void call(Boolean aBoolean, String s) {
                Log.e("callback",s);
            }
        }};

        itemGeeteam = new PlugListItem<IShowGTDialog>();
        itemGeeteam.shortLink = "http://yyfr.net/q1v";
        itemGeeteam.rpcUri = "apkplug://geeteam/rpc/start";
        itemGeeteam.rpcInterfaceClass = IShowGTDialog.class;
        itemGeeteam.plugName = "极验验证插件";

        itemGeeteam.paramType = new Class[]{Activity.class, String.class, String.class, Integer.TYPE, IGTRPCCallback.class};
        itemGeeteam.params = new Object[]{this,
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
                }
        };
        itemGeeteam.mothedName="showGTDialog";

        itemKf5 = new PlugListItem<IKF5OpenKfActivity>();
        itemKf5.shortLink = "http://yyfr.net/q1w";
        itemKf5.rpcUri = "apkplug://kf5/rpc/openactivity";
        itemKf5.rpcInterfaceClass = IKF5OpenKfActivity.class;
        itemKf5.mothedName="openKF5";
        itemKf5.paramType = new Class[]{String.class,String.class,String.class};
        itemKf5.params = new Object[]{"00157bbb168b36e8fb8146ed32afa43d8b546d1d85465c4f","diandou.kf5.com","test@test.com"};
        itemKf5.plugName = "逸创云客服插件";

        itemQlive = new PlugListItem<IQLLogin>();
        itemQlive.shortLink = "http://yyfr.net/q21";
        itemQlive.rpcUri = "apkplug://qlive/rpc/login";
        itemQlive.rpcInterfaceClass = IQLLogin.class;
        itemQlive.mothedName = "login";
        itemQlive.paramType = new Class[]{Context.class,String.class,String.class,Action2.class};
        itemQlive.params = new Object[]{getApplicationContext(),"18801245179", "@Aa123456", new Action2<Boolean, String>() {
            @Override
            public void call(Boolean aBoolean, String s) {
                Log.e("callback",aBoolean+"  "+s);
            }
        }
        };
        itemQlive.plugName = "七牛云直播插件";

        itemEase = new PlugListItem<IEaseInstance>();
        itemEase.shortLink = "http://yyfr.net/q24";
        itemEase.rpcUri = "apkplug://measeplug/rpc/instance";
        itemEase.mothedName = "loginByCode";
        itemEase.paramType = new Class[]{String.class,String.class,Action2.class};
        itemEase.params = new Object[]{"apkplug", "lbh131206",new Action2<Boolean, String>() {
            @Override
            public void call(Boolean aBoolean, String s) {
                if(!aBoolean){
                    return;
                }
                Log.e("callback",s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(textView.getText()+"\n环信登陆成功");
                    }
                });
                startActivity("com.apkplug.easemobplug.ui.LoginActivity");
            }
        }};
        itemEase.rpcInterfaceClass = IEaseInstance.class;
        itemEase.plugName = "环信插件";
        List<PlugListItem> plugListItems = new ArrayList<>();
        plugListItems.add(itemZxing);
        plugListItems.add(itemCam360);
        plugListItems.add(itemUMShare);
        plugListItems.add(itemGeeteam);
        plugListItems.add(itemKf5);
        plugListItems.add(itemQlive);
        plugListItems.add(itemEase);
        ((TextView)viewBaidu.findViewById(R.id.plugname)).setText("百度地图插件");
        ((TextView)viewGaode.findViewById(R.id.plugname)).setText("高德地图插件");
        baiduBar = (ProgressBar) viewBaidu.findViewById(R.id.progressBar);
        gaodeBar = (ProgressBar) viewGaode.findViewById(R.id.progressBar);
        viewBaidu.findViewById(R.id.plugbutton).setOnClickListener(listenerBaidu);
        viewGaode.findViewById(R.id.plugbutton).setOnClickListener(listenerGaode);

        return plugListItems;
    }


    private <T> void getRpcInstances(final PlugListItem<T> listItem) throws Throwable {

        PlugManager.getInstance().getRPCInstanceByShortLink(listItem.shortLink, listItem.rpcUri, listItem.rpcInterfaceClass, new OnGetRPCInstanceListener<T>() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                Log.e("install s",bundle.getName());
            }

            @Override
            public void onGetRPCSuccess(T t) {
                listItem.classInstance =t;
                Log.e("rpc",t.toString());
            }

            @Override
            public void onFail(String s, String s1) {
                Log.e("fail",s+" "+s1);
            }
        });

    }


    private void startActivity(String activity) {
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this,activity);
        startActivity(intent);
    }


    View.OnClickListener listenerBaidu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            baiduBar.setVisibility(View.VISIBLE);
            PlugManager.getInstance().installPlugFromShortLink("http://yyfr.net/q23", new OnInstallSLPlugListener() {
                @Override
                public void onDownloadProgress(String s, String s1, long l, long l1) {
                    int percent = (int) ((float)l/(float) l1*100);
                    baiduBar.setProgress(percent);
                }

                @Override
                public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                    Log.e("install s",bundle.getName());
                    baiduInstallFinish();
                }

                @Override
                public void onInstallFailuer(int i, String s) {
                    Log.e("install f",s);
                    baiduInstallFinish();
                }

                @Override
                public void onDownloadFailure(String s) {
                    Log.e("download f",s);
                    baiduInstallFinish();
                }
            });
        }
    };

    private void baiduInstallFinish() {
        baiduBar.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this, "com.apkplug.baidumapplug.MainActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    View.OnClickListener listenerGaode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gaodeBar.setVisibility(View.VISIBLE);
            PlugManager.getInstance().installPlugFromShortLink("http://yyfr.net/q22", new OnInstallSLPlugListener() {
                @Override
                public void onDownloadProgress(String s, String s1, long l, long l1) {
                    int percent = (int) ((float)l/(float) l1*100);
                    gaodeBar.setProgress(percent);
                }

                @Override
                public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                    Log.e("install s",bundle.getName());
                    gaodeInstallFinish();
                }

                @Override
                public void onInstallFailuer(int i, String s) {
                    Log.e("install f",s);
                    gaodeInstallFinish();
                }

                @Override
                public void onDownloadFailure(String s) {
                    Log.e("download f",s);
                    gaodeInstallFinish();
                }
            });
        }
    };

    private void gaodeInstallFinish() {
        gaodeBar.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this, "com.apkplug.amapplug.MenuActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
