package com.tommy.androidms.retrofit;

public class User {
//    private int id;

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }


}
