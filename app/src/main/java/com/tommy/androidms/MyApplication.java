package com.tommy.androidms;

import android.app.Application;
import android.util.Log;

import me.jessyan.autosize.AutoSize;

public class MyApplication extends Application {
    String TAG = "MyApplication---";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"MyApplication onCreate----");
        AutoSize.initCompatMultiProcess(this);
    }
}
