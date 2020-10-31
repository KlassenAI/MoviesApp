package com.android.moviesapp;

import android.app.Application;

import androidx.room.Room;

import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.utils.Util;

public class App extends Application {

    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class, Util.DB_FAVORITES_MOVIES)
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
