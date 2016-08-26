package com.qiniu.qlive.processores;

import com.qiniu.qlive.config.Config;
import com.qiniu.qlive.utils.Tools;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/23.
 */
public class LogoutProcessor extends BaseProcessor {
    public LogoutProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        Tools.writeSession(context.getBundleContext(), "", "");
        context.getBundleContext().deleteFile(Config.accountFileName);
    }
}
