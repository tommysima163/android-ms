package com.tommy.androidms.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("admin-api/news/system/login")
    Call<ResData> loginVerify(@Body User user);
}
