package com.common;

/**
 * Created by qinfeng on 2016/10/12.
 */

public interface ICaiVerifyCallback {
    void onSuccess(String msg);
    void onFail(String msg);
}
