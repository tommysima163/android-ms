package com.tommy.androidms.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.tommy.androidms.mvp.model.User;
import com.tommy.androidms.mvp.view.LoginIView;


public class LoginPresenterImp implements LoginIPresenter{

    private LoginIView loginIView;
    public LoginPresenterImp(LoginIView loginIView) {
            this.loginIView = loginIView;

    }


    @Override
    public void loginBtnClick(User user) {

        if (user.getAccount().equals("tommy") && user.getPassword().equals("123456")){
            this.loginIView.onLoginSuccess();
        }else {
            this.loginIView.onLoginFailed();
        }

    }



    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    loginIView.onLoginSuccess();
                    break;
                case 2:
                    loginIView.onLoginFailed();
                    break;
            }
        }

    };
    @Override
    public void loginBtnClickLong(User user) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //模拟耗时操作
                    Thread.sleep(2000);
                    Message msg = Message.obtain();
                    if (user.getAccount() == "tommy" && user.getPassword()=="123456"){
                        msg.what = 1;

                    }else {
                        msg.what = 2;
                    }
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

    }


}
