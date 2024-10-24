package com.tommy.androidms.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ServiceOne extends Service {
    String TAG = ServiceOne.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate---");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand---");
        Toast.makeText(this, "服务已经启动", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind---");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind---");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG,"onRebind---");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy---");
        Toast.makeText(this, "服务已经停止", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
