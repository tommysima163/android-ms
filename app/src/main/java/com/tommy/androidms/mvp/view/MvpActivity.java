package com.tommy.androidms.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.tommy.androidms.databinding.MvpActivityBinding;
import com.tommy.androidms.mvp.model.User;
import com.tommy.androidms.mvp.presenter.LoginPresenterImp;

import me.jessyan.autosize.internal.CustomAdapt;

public class MvpActivity extends AppCompatActivity implements LoginIView , CustomAdapt {
    LoginPresenterImp loginPresenterImp = new LoginPresenterImp(this);

    String TAG = MvpActivity.class.getSimpleName();
    MvpActivityBinding binding ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","MvpActivity----onCreate");
        binding = MvpActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setAccount(binding.accountEt.getText().toString());
                user.setPassword(binding.passwordEt.getText().toString());
//                loginPresenterImp.loginBtnClick(user);

                loginPresenterImp.loginBtnClickLong(user);
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        Log.e(TAG,"current Thread:"+ Thread.currentThread());
        Toast.makeText(this,"登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFailed() {
        Log.e(TAG,"current Thread:"+ Thread.currentThread());
        Toast.makeText(this,"登录失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFirstName() {

    }

    @Override
    public void setLastName() {

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
