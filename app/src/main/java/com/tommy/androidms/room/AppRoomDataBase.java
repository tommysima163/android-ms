package com.tommy.androidms.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AccountDataItem.class},version = 1,exportSchema = false)
public abstract class AppRoomDataBase extends RoomDatabase {
    private static volatile AppRoomDataBase INSTANCE;
//    public abstract AccountListDao accountListDao();

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    // 单例模式
    public static AppRoomDataBase getDataBase(Context context){
        if (INSTANCE == null) {
            synchronized (AppRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppRoomDataBase.class,
                                    "记账数据库"
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }
//    public abstract AccountDao accountDao();
}