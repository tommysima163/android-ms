package com.tommy.androidms.multithreading;

import android.util.Log;

public class MyThread extends Thread {
    String TAG = MyThread.class.getSimpleName();
    @Override
    public void run() {
       // super.run();
        Log.e(TAG,"run start");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Log.e(TAG,"run end");
    }
}
