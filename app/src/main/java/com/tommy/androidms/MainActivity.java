package com.tommy.androidms;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.JsonParser;
import com.tommy.androidms.broadcast.MyReceiver;
import com.tommy.androidms.broadcast.MyReceiver2;
import com.tommy.androidms.coroutines.CoroutineTest;
import com.tommy.androidms.databinding.MainActivityBinding;
import com.tommy.androidms.multithreading.MyRunnable;
import com.tommy.androidms.multithreading.MyThread;
import com.tommy.androidms.mvp.view.MvpActivity;
import com.tommy.androidms.mvvm.view.MvvmActivity;
import com.tommy.androidms.retrofit.LoginService;
import com.tommy.androidms.retrofit.ResData;
import com.tommy.androidms.retrofit.RetrofitClient;
import com.tommy.androidms.retrofit.User;
import com.tommy.androidms.service.ServiceOne;
import com.tommy.androidms.service.ServiceTwo;
import com.tommy.nativecpp.JNITool;

import org.json.JSONObject;

import me.jessyan.autosize.internal.CancelAdapt;
import me.jessyan.autosize.internal.CustomAdapt;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements CustomAdapt {
    String TAG = MainActivity.class.getSimpleName();
    MainActivityBinding binding;
    ServiceTwo serviceTwo;


    MyReceiver myReceiver;
    MyReceiver2 myReceiver2;
    IntentFilter filter;
    IntentFilter filter2;

    boolean mBound = false;


    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            Log.e(TAG,"handleMessage current Thread:" + Thread.currentThread());
            switch (msg.what){
                case 1:
                    Log.e(TAG,"what=1");
                    break;
                default:
                    Log.e(TAG,"default");
                    break;
                }
            }

    };

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceTwo.MyBinder binder = (ServiceTwo.MyBinder) service;
            serviceTwo = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mBound = false;
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate----");

        // ViewBinding ,  MainActivityBinding  与 对应的xml  main_activity 对应
        binding = MainActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        // startService
        binding.startService1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"start 1 onClick----");
                startService(new Intent(getBaseContext(), ServiceOne.class));
            }
        });
        binding.stopService1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"stop 1 onClick----");
                stopService(new Intent(getBaseContext(),ServiceOne.class));
            }
        });


        // bindService
        binding.startService2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"start 2 onClick----");
                bindService(new Intent(getBaseContext(),ServiceTwo.class),serviceConnection, Context.BIND_AUTO_CREATE);

            }
        });

        binding.stopService2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"stop 2 onClick----");
                unbindService(serviceConnection);

            }
        });

        binding.startBroadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"startBroadcastBtn onClick----");
                Intent intent = new Intent("com.tommy.xxx");
//                intent.putExtra("bData","123");
//                intent.setAction("com.tommy.androidms.LOCAL_BROADCAST");
                sendBroadcast(intent);
//                sendOrderedBroadcast();
            }
        });

        binding.startBroadcastDynamicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"startBroadcastDynamicBtn onClick----");
                Intent intent = new Intent("com.tommy.xxx");
                sendBroadcast(intent);

            }
        });


        binding.sendSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"sendSortBtn onClick----");
                Intent intent = new Intent("com.tommy.xxx");
                intent.putExtra("data","xxx");
                sendOrderedBroadcast(intent,null);

            }
        });

        binding.contentProviderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"contentProviderBtn onClick----");
                ContentResolver contentResolver = getContentResolver();
                MainActivityPermissionsDispatcher.queryContactsWithPermissionCheck(MainActivity.this,contentResolver);
            }
        });



        //开启线程
        binding.startThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"startThreadBtn onClick----");
                MyThread myThread = new MyThread();
                myThread.start();
            }
        });

        binding.startRunnableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"startRunnableBtn onClick----");
               Thread thread = new Thread(new MyRunnable());
               thread.start();
            }
        });


        binding.handlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"handlerBtn onClick----");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG,"handler.post current Thread:" + Thread.currentThread());
                    }
                });
            }
        });

        binding.handler2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"handlerBtn2 onClick----");
                // 在子线程中发送消息
                new Thread(()->{
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    Log.e(TAG,"handler.sendMessage current Thread:" + Thread.currentThread());
                    handler.sendMessage(message);

                }).start();
            }
        });


        binding.looperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"looperBtn onClick----");
                startThread();
            }
        });


        binding.jniBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"jniBtn onClick----");
                int sum =  JNITool.addNumber(33,44);
                Log.e(TAG,"sum(33,44):"+sum);
//                Toast.makeText(MainActivity.this, JNITool.addNumber(1,2),Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, JNITool.getUserName(),Toast.LENGTH_LONG).show();
            }

        });

        binding.mvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MvpActivity.class));
            }
        });

        binding.mvvmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MvvmActivity.class));
            }
        });



        //retrofitBtn
        binding.retrofitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("tommy","123456");
                RetrofitClient.getInstance().getService(LoginService.class).loginVerify(user).enqueue(new Callback<ResData>() {
                    @Override
                    public void onResponse(Call<ResData> call, Response<ResData> response) {
                        Log.e(TAG,"onResponse:" + response.message());
                        Log.e(TAG,"onResponse:" + response);
//                        Log.e(TAG,"call:" +);
                    }

                    @Override
                    public void onFailure(Call<ResData> call, Throwable throwable) {
                        Log.e(TAG,"onFailure:" + throwable.getMessage());
                    }
                });

            }
        });

        binding.coroutineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"coroutineBtn---");
                CoroutineTest coroutineTest = new CoroutineTest();
//                coroutineTest.doSomething()
//                coroutineTest.test();
//                coroutineTest.testSerialTask();
//                coroutineTest.testSerialTask2();

                //使用 async 并行执行任务
               // coroutineTest.testAsync(MainActivity.this);
// 使用 runBlocking 函数可以保证在协程作用域内的所有代码和子协程没有全部执行完之前一直阻塞当前线程。
                coroutineTest.main123();
            }
        });

    }




    // 手动在子线程中创建 Looper
    void startThread(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"thread 0:"+Thread.currentThread());
                Looper.prepare();
                Handler handler = new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        // 处理消息
                        Log.e("LooperExample", "Message received: " + msg.what);
                        Log.e(TAG,"thread 1:"+Thread.currentThread());
                    }
                };


                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);

                // 启动消息循环，保持线程运行以处理消息
                Looper.loop();

                // Looper.loop() 之后的代码不会立即执行，因为 loop 进入了无限循环
                Log.e("LooperExample", "Looper has ended");
            }
        });
        thread.start();
    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    public void showRationaleForReadContacts(PermissionRequest request) {
        Log.e(TAG,"Rationale-----");
        Toast.makeText(this, "通讯录权限用于XXX", Toast.LENGTH_SHORT).show();
        request.proceed();
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    public void onReadContactsDenied() {
        Log.e(TAG,"Denied-----");
        Toast.makeText(this, "通讯录权限被拒绝", Toast.LENGTH_SHORT).show();
    }
    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    public void onReadContactsNeverAskAgain() {
        Log.e(TAG,"NeverAskAgain-----");
        Toast.makeText(this, "通讯录权限被拒绝切不再提示", Toast.LENGTH_SHORT)
                .show();
    }


    @NeedsPermission(Manifest.permission.READ_CONTACTS)
     void queryContacts(ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Log.e("Contact", "Id: " + id + ", Name: " + name);

                // 获取电话号码
                Cursor phones = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null);

                if (phones != null && phones.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("Contact", "Phone Number: " + phoneNumber);
                    } while (phones.moveToNext());
                    phones.close();
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG,"requestCode:"+requestCode);

        for (String permission :
        permissions) {
            Log.e(TAG,"permission:"+permission);
        }
        for (int grantResult :
                grantResults) {
            Log.e(TAG,"grantResult:"+grantResult);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        onRequestPermissionsResult(requestCode, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume---");
        //动态注册
        myReceiver = new MyReceiver();
        myReceiver2 = new MyReceiver2();
        filter = new IntentFilter("com.tommy.xxx");
        filter.setPriority(99);
        filter2 = new IntentFilter("com.tommy.xxx");
        filter2.setPriority(199);
        registerReceiver(myReceiver,filter,Context.RECEIVER_EXPORTED);
        registerReceiver(myReceiver2,filter2,Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onPause() {
        Log.e(TAG,"onPause---");
        super.onPause();
        unregisterReceiver(myReceiver);
        unregisterReceiver(myReceiver2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //解绑服务
        Log.e(TAG,"onDestroy 解绑服务");
        if (mBound){
            unbindService(serviceConnection);
            mBound = false;
        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 360;
    }
}
