package com.geeteam.common;

import android.app.Activity;

/**
 * Created by qinfeng on 2016/10/12.
 */

public interface IShowGTDialog {
    void showGTDialog(Activity activity, String firstUri, String secondUri, int timeout,IGTRPCCallback callback);
}
