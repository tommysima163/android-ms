package com.tommy.androidms.multithreading;

import static java.lang.Thread.sleep;

import android.util.Log;

public class MyRunnable implements Runnable{
    String TAG = MyRunnable.class.getSimpleName();
    @Override
    public void run() {
        Log.e(TAG,"run start");
        try {

            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Log.e(TAG,"run end");
    }
}
