package com.tommy.androidms.retrofit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    String BASE_URL = "https://demo.aimsan.com/21080/";
    static RetrofitClient mInstance;
    public static RetrofitClient getInstance(){
        if (mInstance == null){
            synchronized (RetrofitClient.class){ //锁,防止线程问题
                if (mInstance == null){
                    mInstance = new RetrofitClient();

                }
            }
        }

        return mInstance;
    }

    private Retrofit retrofit;

    private Retrofit getRetrofit(){



        if (retrofit == null){

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 可以设置不同的级别

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(client)// 使用自定义的OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }


        return retrofit;
    }

    //创建RetrofitClient对象后调用,参数传对应的Service,即可调用Service中的方法,Service在下文会讲到
    public <T> T getService(Class<T> cls){
        return getRetrofit().create(cls);
    }
}
