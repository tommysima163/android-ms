package com.tommy.nativecpp;

import android.util.Log;

public class JNITool {

    static {

        System.loadLibrary("nativecpp");

        Log.e("TAG","loadLibrary nativecpp---");
    }

    /**
     * 添加 native 方法
     */
    public  static native String stringFromJNI();



    public  static native String getUserName();

    public  static native int addNumber(int a, int b);


    /**
    cpp 代码

    extern "C" JNIEXPORT jstring
JNICALL
Java_com_tommy_nativecpp_JNITool_getUserName(
        JNIEnv *env,
jobject ) {
        std::string hello = "Tommy";
        return env->NewStringUTF(hello.c_str());
    }


    extern "C" JNIEXPORT jint
    JNICALL
    Java_com_tommy_nativecpp_JNITool_addNumber( JNIEnv *env,jobject thiz , jint a, jint b){
        return a+b;
    }
     */
}
