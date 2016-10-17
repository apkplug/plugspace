package com.common;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/10/13.
 */

public interface ICam360Start {
    void start(String distPath, Action2<Boolean,String> callback);
}
