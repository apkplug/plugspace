package cn.ciaapp.cia_example;

import com.common.ICiaInit;

import cn.ciaapp.sdk.CIAService;

/**
 * Created by qinfeng on 2016/10/12.
 */

public class RPCCaiInit implements ICiaInit {
    @Override
    public boolean init(String appId, String authKey) {

        try {
            CIAService.init(MainActivitor.bundleContext.getBundleContext(), appId, authKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
