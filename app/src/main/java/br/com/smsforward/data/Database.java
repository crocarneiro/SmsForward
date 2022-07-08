package br.com.smsforward.data;

import android.content.Context;
import androidx.room.Room;

public class Database {
    private static AppDatabase database;

    public static void initDatabase(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "yum-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
