package com.tommy.androidms.mvp.presenter;

import com.tommy.androidms.mvp.model.User;

public interface LoginIPresenter {
    void loginBtnClick(User user);

    void loginBtnClickLong(User user);

//    void attachView(T view);
//
//    void detachView();

}
