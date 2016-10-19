package com.qlive.common;

import android.content.Context;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/10/13.
 */

public interface IQLLogin {
    void login(Context context, String phone, String password, Action2<Boolean,String> callback);
}
