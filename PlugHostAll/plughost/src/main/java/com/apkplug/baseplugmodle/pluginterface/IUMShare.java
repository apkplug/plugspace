package com.apkplug.baseplugmodle.pluginterface;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

import java.util.HashMap;

/**
 * Created by qinfeng on 2016/10/13.
 */

public interface IUMShare {
    void share(HashMap<String, Object> params, Action2<Boolean, String> callback);
}
