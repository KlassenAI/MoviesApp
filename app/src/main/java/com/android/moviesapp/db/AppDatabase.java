package com.android.moviesapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.moviesapp.entity.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao getWordDao();
}
