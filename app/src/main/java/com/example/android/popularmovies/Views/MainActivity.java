package com.example.android.popularmovies.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

        // Get a handle to the recycler view
        mRecyclerView = findViewById(R.id.rv_movies);

        // Apply a 2 column grid layout manager to the recycler view
        mRecyclerView.setLayoutManager(new GridLayoutManager
                (this, 2, RecyclerView.VERTICAL, false));

        // Let the recycler view know viewholders are a fixed size to boost performance
        mRecyclerView.setHasFixedSize(true);

        // Create a movie adapter for the recycler view
        mAdapter = new MovieGridAdapter(this);

        // Set the movie adapter on the recycler view
        mRecyclerView.setAdapter(mAdapter);

        // Method used to fetch and create list of movies using TheMovieDB API TODO: Default to popularity
        loadMoviesList(TheMovieDB.POPULARITY_DESCENDING);
    }

    /**
     * Setup the options menu
     *
     * @param menu The menu to be created
     * @return Returns true if options menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater
        MenuInflater inflater = getMenuInflater();

        // Use the inflater's inflate method to inflate our menu layout to this menu
        inflater.inflate(R.menu.main, menu);

        // Return true so that the menu is displayed in the Toolbar
        return true;
    }

    /**
     * Called when an option item is selected
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to
     *         proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_by_popularity) {
            loadMoviesList(TheMovieDB.POPULARITY_DESCENDING);
        }
        else if(id == R.id.action_sort_by_rating){
            loadMoviesList(TheMovieDB.VOTE_AVG_DESCENDING);
        }

        return super.onOptionsItemSelected(item);
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

        // Calls a function to put all the extras needed to populate the detail activity
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

        // Put the title
        intent.putExtra(DetailActivity.EXTRA_TITLE,
                mMoviesList.get(clickedItemIndex).getTitle());

        // Put the poster url
        intent.putExtra(DetailActivity.EXTRA_POSTER_URL,
                mMoviesList.get(clickedItemIndex).getPosterPath());

        // Put the release date
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE,
                mMoviesList.get(clickedItemIndex).getReleaseDate());

        // Put the vote average, converted to string because textviews require strings
        intent.putExtra(DetailActivity.EXTRA_VOTE_AVERAGE,
                String.valueOf(mMoviesList.get(clickedItemIndex).getVoteAverage()));

        // Put the overview
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW,
                mMoviesList.get(clickedItemIndex).getOverview());

        return intent;
    }

    /**
     * Call function to fetch the movie list from TheMovieDB API. The movie list returned is
     * dependent on the state of the spinner: popularity or vote average (descending order)
     */
    public void loadMoviesList(String sortOrder) {

        // Get movies from TheMovieDB API
        new FetchMoviesTask().execute(sortOrder);

        // Change the title to match the sort order
        if(sortOrder.equals(TheMovieDB.POPULARITY_DESCENDING)) {
            setTitle(R.string.title_popular);
        } else if(sortOrder.equals(TheMovieDB.VOTE_AVG_DESCENDING)){
            setTitle(R.string.title_vote_average);
        }

        // Make the app scroll back to the top of the list every time sort order is changed
        mRecyclerView.smoothScrollToPosition(0);
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
                Log.w("MainActivity", "Movie sort was forced to default to popularity");
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
            mMoviesList = JsonUtils.parseMovieJson(jsonMovieResponse);

            // Notify the adapter that the movie list has changed
            mAdapter.setMoviesList(mMoviesList);


        }
    }
}
