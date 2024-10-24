package com.tommy.androidms.room;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface AccountDao{
    @Insert
    void insertAccount(AccountDataItem AccountDataItem);

    @Update
    void update(AccountDataItem AccountDataItem);

    @Query("SELECT * FROM AccountListItemTable")
    List<AccountDataItem> getAllData();

    @Query("DELETE FROM AccountListItemTable")
    void delete();
}