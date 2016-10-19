package com.apkplug.baseplugmodle.pluginterface;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

import java.util.List;

/**
 * Created by qinfeng on 2016/10/13.
 */

public interface IEaseInstance {
    void loginByCode(String userName, String password, Action2<Boolean, String> callback);
    void loginByView(String userName, String password, Action2<Boolean, String> callback);
    boolean addFriend(String userName);
    List<String> getContents();
    boolean regist(String userName, String password);
}
