package com.tommy.androidms.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tommy.androidms.mvvm.model.User;

public class LoginViewModel extends ViewModel {
//    LiveData<String> loginResult = new MutableLiveData<>();

    MutableLiveData<String> loginResult = new MutableLiveData<>();

    public MutableLiveData<String> getLoginResult() {
        return loginResult;
    }

    public void loginVerify(String account, String password){
        if (account.equals("tommy")&& password.equals("123456")){
            loginResult.postValue("登录成功");
        }
        else {
            loginResult.postValue("登录失败");
        }

    }
}
