package com.example.android.popularmovies.Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mMovies;
    private MutableLiveData<String> mFilter;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mMovies = mMovieDao.getMovies(); //TODO: Get rid of this?
    }

    // TODO: Change this to get movies based on filter option
//    public LiveData<List<Movie>> getMovies() {
//        // Call fetch data for popularity and vote average
//        // or just get movies from room database
//        return mMovies;
//    }

    // TODO: Change this to get movies based on filter option
    public LiveData<List<Movie>> getMovies(String filter) {
        if (filter.equals(TheMovieDB.POPULARITY_DESCENDING) || filter.equals(TheMovieDB.VOTE_AVG_DESCENDING)) {
            mMovies = new FetchMoviesTask().execute(filter);
        } else if (filter.equals(TheMovieDB.FAVORITES)) {
            mMovies = mMovieDao.getMovies();
        }
        return mMovies;
    }

    public MutableLiveData<String> getFilter() {
        return mFilter;
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


    /**
     * Makes an HTTP request to TheMovieDB that returns a json object containing a list of movies
     * sorted by popularity or vote average. The post execution then parses the data from that
     * json object and populates the arraylist mMovieList
     */
    public class FetchMoviesTask extends AsyncTask<String, Void, String> {

        /**
         * No pre execution task required.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Makes an HTTP request that returns a json string containing a movie list sorted
         * by movie popularity or vote average
         *
         * @param filterOption Determines whether movies should be sorted by popularity or vote
         *                     average
         * @return A string representing a json array that holds data to a sorted list of movies
         */
        @Override
        protected String doInBackground(String... filterOption) {

            URL movieListRequestUrl;

            /*
             * Creates a url based on the filter option: popularity or vote average
             */
            if (filterOption[0].equals(TheMovieDB.POPULARITY_DESCENDING)) {
                movieListRequestUrl = TheMovieDB.buildMovieListUrl(TheMovieDB.POPULARITY_DESCENDING);
            } else if (filterOption[0].equals(TheMovieDB.VOTE_AVG_DESCENDING)) {
                movieListRequestUrl = TheMovieDB.buildMovieListUrl(TheMovieDB.VOTE_AVG_DESCENDING);
            } else {
                // defaults to popularity sort and gives a warning
                movieListRequestUrl = TheMovieDB.buildMovieListUrl(TheMovieDB.POPULARITY_DESCENDING);
            }

            /*
             * Use the generated url to make an http request to TheMovieDB API and return
             * a string representation of a json array containing a sorted movie list
             */
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieListRequestUrl);
                return jsonMovieResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Takes the json object that was queried in the background and parses it to populate
         * the movies list stored in mMoviesList
         *
         * @param jsonMovieResponse The json string produced as a result of the background task
         */
        @Override
        protected void onPostExecute(String jsonMovieResponse) {
            // Parse json string to populate movies list
            mMovies = JsonUtils.parseMovieJson(jsonMovieResponse);
        }
    }
}