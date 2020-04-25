package com.example.android.popularmovies.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.popularmovies.Models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Query("SELECT * FROM favorites WHERE id = :movieId")
    LiveData<Movie> getMovie(String movieId);

    // QUESTION: Why does this have to be a List and ArrayList does not work
    @Query("SELECT * FROM favorites")
    LiveData<List<Movie>> getAllMovies();

    @Query("DELETE FROM favorites WHERE id = :movieId")
    void removeMovie(String movieId);
}
