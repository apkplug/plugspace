package com.common;

/**
 * Created by qinfeng on 2016/10/12.
 */

public interface IGTRPCCallback {
    void onSuccess(String msg);
    void onFail(String msg);
    void onCancel(String msg);
}
