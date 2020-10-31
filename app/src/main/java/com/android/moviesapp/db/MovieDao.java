package com.android.moviesapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.android.moviesapp.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    long insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("select * from favorites_movies")
    List<Movie> getAllFavoritesMovies();

    @Query("select * from favorites_movies where movie_id ==:id")
    Movie getMovie(String id);
}
