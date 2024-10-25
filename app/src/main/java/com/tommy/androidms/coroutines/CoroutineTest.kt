package com.tommy.androidms.coroutines

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineTest{
    val TAG = "CoroutineTest"
    suspend fun doSomething(){
        //执行耗时操作

    }



    fun test(){
        CoroutineScope(Dispatchers.Default).launch {
            Log.e(TAG,"Default------"+Thread.currentThread())
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.e(TAG,"Main------"+Thread.currentThread())
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.e(TAG,"IO------"+Thread.currentThread())
        }
    }


    //在协程中执行串行的代码
    fun testSerialTask(){
        CoroutineScope(Dispatchers.Main).launch {

            launch {

                delay(1000)
                Log.e(TAG, "thread 1:" + Thread.currentThread().name)
            }
            Log.e(TAG, "thread 2:" + Thread.currentThread().name)
        }
    }

    //withContext
    fun testSerialTask2(){
        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO){

                delay(1000)
                Log.e(TAG, "1thread 1:" + Thread.currentThread().name)
            }

            Log.e(TAG, "1thread 2:" + Thread.currentThread().name)
        }
    }

}
