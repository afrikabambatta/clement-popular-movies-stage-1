package com.example.android.popularmovies.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Utils.JsonUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * The Main Activity is a 2 column grid of movie posters queried from TheMovieDB API.
 * By default movie posters are sorted by popularity.
 * Clicking on a movie poster will take the user to a details page with more movie information.
 */
public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieItemClickListener {

    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;
    private ArrayList<Movie> mMoviesList;

    /**
     * On program boot up this will create the main activity.
     *
     * @param savedInstanceState Null because this project doesn't require saving the state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get a handle to the recycler view
        mRecyclerView = findViewById(R.id.rv_movies);

        // apply a 2 column grid layout manager to the recycler view
        mRecyclerView.setLayoutManager(new GridLayoutManager
                (this, 2, RecyclerView.VERTICAL, false));

        // let the recycler view know viewholders are a fixed size to boost performance
        mRecyclerView.setHasFixedSize(true);

        // create a movie adapter for the recycler view
        mAdapter = new MovieGridAdapter(this);

        // set the movie adapter on the recycler view
        mRecyclerView.setAdapter(mAdapter);

        // method used to fetch and create list of movies using TheMovieDB API
        loadMoviesList();

    }

    /**
     * This listener triggers when a movie viewholder is clicked. It retrieves the clicked movie's
     * index which is used to pass in that movie's information when this function sends an intent
     * to start detail activity.
     *
     * @param clickedItemIndex The index of the clicked viewholder, used so the detail activity
     *                        receives information from the correct movie in the movie list
     */
    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);

        // calls a function to put all the extras needed to populate the detail activity
        intent = putDetailExtras(intent, clickedItemIndex);

        startActivity(intent);
    }

    /**
     * Use to put extras into the intent used to start detail activity. Extras include title,
     * poster url, vote average, release date, and overview.
     *
     * @param intent The intent that this function will put extras into
     * @param clickedItemIndex The index used to get data from the correct movie from mMovieList
     * @return The intent that will be used to start detail activity
     */
    public Intent putDetailExtras(Intent intent, int clickedItemIndex){

        // put the title
        intent.putExtra(DetailActivity.EXTRA_TITLE,
                mMoviesList.get(clickedItemIndex).getTitle());

        // put the poster url
        intent.putExtra(DetailActivity.EXTRA_POSTER_URL,
                mMoviesList.get(clickedItemIndex).getPosterPath().toString());

        // put the release date
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE,
                mMoviesList.get(clickedItemIndex).getReleaseDate());

        // put the vote average, converted to string because textviews require strings
        intent.putExtra(DetailActivity.EXTRA_VOTE_AVERAGE,
                String.valueOf(mMoviesList.get(clickedItemIndex).getVoteAverage()));

        // put the overview
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW,
                mMoviesList.get(clickedItemIndex).getOverview());

        return intent;
    }

    /**
     * Call function to fetch the movie list from TheMovieDB API. The movie list returned is
     * dependent on the state of the spinner: popularity or vote average (descending order)
     */
    public void loadMoviesList() {
        // TODO: retrieve the current setting of the spinner then pass it into FetchMoviePoster
        new FetchMoviesTask().execute("popularity");

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
         * Makes an HTTP request that returns a json object sorted by movie popularity or
         * vote average
         *
         * @param filterOption Determines whether movies should be sorted by popularity or vote
         *                     average
         * @return A string representing a json object that holds data to a sorted list of movies
         */
        @Override
        protected String doInBackground(String... filterOption) {

            URL movieListRequestUrl;

            /*
             * Creates a url based on the filter option: popularity or vote average
             */
            if (filterOption[0].equals("popularity")) {
                movieListRequestUrl = TheMovieDB.getMoviesSortedByPopularity();
            } else if (filterOption[0].equals("vote_average")) {
                movieListRequestUrl = TheMovieDB.getMoviesSortedByVoteAvg();
            } else {
                // defaults to popularity sort
                movieListRequestUrl = TheMovieDB.getMoviesSortedByPopularity();
                Log.w("MainActivity", "Movie sort was forced to default to popularity");
            }

            /*
             * Use the generated url to make an http request to TheMovieDB API and return
             * a string representation of a json object containing a sorted movie list
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
         * @param jsonMovieResponse The string json object retrieved from the background task
         */
        @Override
        protected void onPostExecute(String jsonMovieResponse) {

            // parse json string to populate movies list
            mMoviesList = JsonUtils.parseMovieJson(jsonMovieResponse);

            // notify the adapter that the movie list has changed
            mAdapter.setMoviesList(mMoviesList);
        }
    }
}
