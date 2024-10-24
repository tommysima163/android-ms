package com.tommy.androidms.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    String TAG = MyReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"onReceive----");
//        String bData = intent.getStringExtra("bData");
//        Log.e(TAG,"bData:"+bData);
        //耗时操作放service里
    }
}
