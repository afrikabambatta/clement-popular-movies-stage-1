package com.example.android.popularmovies.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.popularmovies.Models.Movie;

@Database(entities={Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    private static MovieRoomDatabase INSTANCE;

    static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                } // QUESTION: Is there a way to persist the data on app close?
            }
        }
        return INSTANCE;
    }
}
