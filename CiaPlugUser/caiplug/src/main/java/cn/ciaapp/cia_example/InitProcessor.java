package cn.ciaapp.cia_example;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

import cn.ciaapp.sdk.CIAService;

/**
 * Created by qinfeng on 2016/8/26.
 */
public class InitProcessor extends BaseProcessor {
    public InitProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        try {
            String appid = (String) hashMap.get("AppId");
            String authKey = (String) hashMap.get("AuthKey");

            CIAService.init(context.getBundleContext(), appid, authKey);
            dispatchAgent.reply(getMsgId(),true);
        } catch (Exception e) {
            e.printStackTrace();
            dispatchAgent.reply(getMsgId(),false,e);
        }
    }
}
