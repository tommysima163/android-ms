package com.tommy.androidms.coroutines

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                Log.e(TAG, "1 thread 1:" + Thread.currentThread().name)
            }

            Log.e(TAG, "1 thread 2:" + Thread.currentThread().name)
        }
    }


    // 使用 async 并行执行任务
   // async{}
   // 异步启动一个子协程，并返回Deffer对象，可通过调用Deffer.await()方法等待该子协程执行完成并获取结果，常用于并发执行-同步等待的情况
    fun testAsync(lifecycleOwner: LifecycleOwner){

        lifecycleOwner. lifecycleScope.launch {
            val  data1 = async {  fetchData1()}
            val  data2 = async {  fetchData2()}
            val ret1 = data1.await()
            Log.e(TAG, "ret1:$ret1")
            val ret2 = data2.await()
            Log.e(TAG, "ret2:$ret2")
            Log.e(TAG,"leScope.launch:"+Thread.currentThread())
        }



    }
    suspend fun  fetchData1(): Int{
        Log.e(TAG,"fetchData1----start:"+Thread.currentThread())
        delay(2000)
        Log.e(TAG,"fetchData1----end:"+Thread.currentThread())
        return 1
    }

    suspend fun  fetchData2() : Int{
        Log.e(TAG,"fetchData2----start:"+Thread.currentThread())
        delay(1500)
        Log.e(TAG,"fetchData2----end:"+Thread.currentThread())
        return 2
    }


    // a.runBlocking
   // 使用 runBlocking 函数可以保证在协程作用域内的所有代码和子协程没有全部执行完之前一直阻塞当前线程。


    fun main123() {
        Log.e(TAG,"runBlocking  start -----");
        runBlocking {
            launch {
                // 执行任务 a
                taskA()
                Log.e(TAG,"Task A completed:"+ Thread.currentThread())
            }

            launch {
                // 执行任务 b
                taskB()
                Log.e(TAG,"Task B completed:"+ Thread.currentThread())
            }
            launch {
                // 执行任务 b
                taskB()
                Log.e(TAG,"Task B2 completed:"+ Thread.currentThread())
            }

            // 等待 a 和 b 任务完成后再执行 c 任务
            coroutineScope {
                launch {
                    // 执行任务 c
                    taskC()
                    Log.e(TAG,"Task C completed:"+ Thread.currentThread())
                }
            }
            Log.e(TAG,"11111")
            // 等待 a 和 b 任务完成后再执行 c 任务
            coroutineScope {
                launch {
                    // 执行任务 c
                    taskD()
                    Log.e(TAG,"Task D completed:"+ Thread.currentThread())
                }
            }
            Log.e(TAG,"22222")


        }

        Log.e(TAG,"runBlocking  outside -----"+ Thread.currentThread());
    }

    suspend fun taskA() {
        // 执行任务 a 的代码
        delay(3000) // 模拟任务执行耗时
    }

    suspend fun taskB() {
        // 执行任务 b 的代码
        delay(2000) // 模拟任务执行耗时
    }

    suspend fun taskC() {
        // 执行任务 c 的代码
        delay(3000) // 模拟任务执行耗时
    }


    suspend fun taskD() {
        // 执行任务 c 的代码
        delay(2000) // 模拟任务执行耗时
    }


}
