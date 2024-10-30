package com.tommy.androidms;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.BuildConfig;

public class MyApplication extends Application {
    String TAG = "MyApplication---";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"MyApplication onCreate--1234--");
        AutoSize.initCompatMultiProcess(this);
//
       // if (BuildConfig.DEBUG) {
            // Debug包必须开启调试模式！否则会有各种问题(线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
            ARouter.openLog();
       // }
//// 尽可能早，推荐在Application中初始化
        ARouter.init(this);
        Log.e(TAG,"ARouter.init--");

    }
}
