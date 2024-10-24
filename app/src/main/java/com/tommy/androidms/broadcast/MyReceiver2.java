package com.tommy.androidms.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver2 extends BroadcastReceiver {
    String TAG = MyReceiver2.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"onReceive----");
        String bData = intent.getStringExtra("data");
        Log.e(TAG,"bData:"+bData);

//        abortBroadcast();
        //耗时操作放service里
    }
}
