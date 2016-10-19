package com.apkplug.baseplugmodle;

import android.widget.ProgressBar;

/**
 * Created by qinfeng on 2016/10/17.
 */

public class PlugListItem<T>{
    public T classInstance;
    public Class<T> rpcInterfaceClass;
    public String shortLink;
    public String rpcUri;
    public String plugName;
    public ProgressBar progressBar;
    public String mothedName;
    public Object[] params;
    public Class[] paramType;
}
