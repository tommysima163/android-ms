package com.tommy.androidms.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ServiceTwo extends Service {

  String TAG = ServiceTwo.class.getSimpleName();
  MyBinder myBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }



    public class MyBinder extends Binder{

      public ServiceTwo getService() {
        return ServiceTwo.this;
      }

      void dealService(){
        Log.e(TAG,"dealService----");
      }
    }



  @Override
  public void onCreate() {
    super.onCreate();
    Log.e(TAG,"onCreate---");
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
