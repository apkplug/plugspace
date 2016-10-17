package com.common;

/**
 * Created by qinfeng on 2016/10/12.
 */

public interface ICiaVerify {
    void verify(String appId,String authKey,String phone,ICaiVerifyCallback caiVerifyCallback);
}
