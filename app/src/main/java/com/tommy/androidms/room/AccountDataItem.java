package com.tommy.androidms.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// 账单数据类
@Entity(tableName = "AccountListItemTable")
public class AccountDataItem {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String money; // 账单金额
    private String type; // 消费类别 -餐饮类
    private String detail; // 消费详情(备注)
    private String data; //消费时间
    private int in; // 1.收入 2.支出

    public AccountDataItem(String money, String type, String detail, String data, int in) {
        this.money = money;
        this.type = type;
        this.detail = detail;
        this.data = data;
        this.in = in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    @NonNull
    @Override
    public String toString() {
        return getId()+getDetail()+getMoney();
    }
}