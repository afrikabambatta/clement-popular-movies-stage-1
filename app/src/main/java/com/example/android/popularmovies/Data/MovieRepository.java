package com.example.android.popularmovies.Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.popularmovies.Models.Movie;

import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    LiveData<List<Movie>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(Movie movie) {
        new insertAsyncTask(mMovieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<Movie>>> {

        private MovieDao mAsyncTaskDao;

        getAllAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        //QUESTION: Can I pass in void...?
        @Override
        protected LiveData<List<Movie>> doInBackground(Void... nothing) {
            return mAsyncTaskDao.getAllMovies();

        }
    }
}