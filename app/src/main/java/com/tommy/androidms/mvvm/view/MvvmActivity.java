package com.tommy.androidms.mvvm.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.tommy.androidms.databinding.MvvmActivityBinding;
import com.tommy.androidms.mvvm.viewmodel.LoginViewModel;

public class MvvmActivity extends AppCompatActivity {

    LoginViewModel loginViewModel = new LoginViewModel();

    MvvmActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MvvmActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.loginVerify(binding.accountEt.getText().toString(),binding.passwordEt.getText().toString());

            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MvvmActivity.this,s,Toast.LENGTH_LONG).show();
            }
        });

    }
}
