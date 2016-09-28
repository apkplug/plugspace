package com.apkplug.umshareplug;

import com.apkplug.umshareplug.wxapi.WXEntryActivity;

import org.apkplug.Bundle.ClassHook;

/**
 * Created by qinfeng on 2016/8/31.
 */
class ImpClassHook implements ClassHook {
    @Override
    public boolean isHandler(String s) {
        if(s.endsWith("WXEntryActivity")){
            return true;
        }else if(s.endsWith("WBShareActivity")){
            return true;
        }
        return false;
    }

    @Override
    public Object loadClass(String s) {
        if(s.endsWith("WXEntryActivity")){
            return WXEntryActivity.class;
        }else if(s.endsWith("WBShareActivity")){
            return WBShareActivity.class;
        }
        return null;
    }
}
