package cn.ciaapp.cia_example;

import android.app.Application;

import cn.ciaapp.sdk.CIAService;

/**
 * Created by baoyz on 15/5/21.
 */
public class ExampleApplication extends Application {

    // TODO
    protected static final String appId = "6c16f74868044396a3d0bcd2443b7f0c";
    protected static final String authKey = "338611e4c15e4152bf59ca71e742a269";

    private int state;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO 初始化CIA服务，填写申请的appId和authKey
        CIAService.init(this, appId, authKey);
    }
}
