package com.apkplug.baseplugmodle.pluginterface;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/10/13.
 */

public interface IZxingStart {
    void startScan(Action2<Boolean, String> callback);
}
