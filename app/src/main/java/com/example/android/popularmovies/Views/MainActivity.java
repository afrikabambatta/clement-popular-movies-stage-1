package com.example.android.popularmovies.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmovies.Data.MovieViewModel;
import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main Activity is a 2 column grid of movie posters queried from TheMovieDB API.
 * By default movie posters are sorted by popularity.
 * Clicking on a movie poster will take the user to a details page with more movie information.
 */
public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieItemClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;
    private ArrayList<Movie> mMoviesList;
    private MovieViewModel mMovieViewModel;
    private String mFilter = TheMovieDB.POPULARITY_DESCENDING; // Defaults to popularity

    /**
     * On program boot up this will create the main activity.
     *
     * @param savedInstanceState Null because this project doesn't require saving the state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup movie poster grid recycler view
        mRecyclerView = findViewById(R.id.rv_movies);
        mRecyclerView.setLayoutManager(new GridLayoutManager
                (this, 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieGridAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        // Set up the view model
        setupViewModel();
    }

    public void setupViewModel() {
        // Retrieve movie view model from view model class
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        mMovieViewModel.getMovies().observe(this, new Observer<List<Movie>>() { // QUESTION: How do you change this to lambda expression
            @Override
            public void onChanged(@Nullable final List<Movie> movies) {
                mAdapter.setMoviesList(movies);
            }
        });
    }

    /**
     * Setup the options menu
     *
     * @param menu The menu to be created
     * @return Returns true if options menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Return true so that the menu is displayed in the Toolbar
        return true;
    }

    /**
     * Called when an option item is selected
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_by_popularity:
                setTitle(R.string.title_popular);
                mMovieViewModel.setFilter(TheMovieDB.POPULARITY_DESCENDING);
                break;
            case R.id.action_sort_by_rating:
                setTitle(R.string.title_vote_average);
                mMovieViewModel.setFilter(TheMovieDB.VOTE_AVG_DESCENDING);
                break;
            case R.id.action_sort_by_favorites:
                setTitle("Favorites"); //TODO: Turn to resource string
                mMovieViewModel.setFilter(TheMovieDB.FAVORITES);
                break;
            default: // Default to popularity sort in case none was specified
                mMovieViewModel.setFilter(TheMovieDB.VOTE_AVG_DESCENDING);
                Log.d(TAG, "Issue with sort order");
                break;
        }

        // Make the app scroll back to the top of the list every time sort order is changed
        mRecyclerView.smoothScrollToPosition(0);

        return super.onOptionsItemSelected(item);
    }

    /**
     * This listener triggers when a movie viewholder is clicked. It retrieves the clicked movie's
     * index which is used to pass in that movie's information when this function sends an intent
     * to start detail activity.
     *
     * @param clickedItemIndex The index of the clicked viewholder, used so the detail activity
     *                         receives information from the correct movie in the movie list
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
     * @param intent           The intent that this function will put extras into
     * @param clickedItemIndex The index used to get data from the correct movie from mMovieList
     * @return The intent that will be used to start detail activity
     */
    public Intent putDetailExtras(Intent intent, int clickedItemIndex) {

        // Put the title
        intent.putExtra(DetailActivity.EXTRA_TITLE,
                mMoviesList.get(clickedItemIndex).getTitle());

        // Put the poster url
        intent.putExtra(DetailActivity.EXTRA_POSTER_URL,
                mMoviesList.get(clickedItemIndex).getPosterPath());

        // Put the release date
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE,
                mMoviesList.get(clickedItemIndex).getReleaseDate());

        // Put the vote average, converted to string because text views require strings
        intent.putExtra(DetailActivity.EXTRA_VOTE_AVERAGE,
                String.valueOf(mMoviesList.get(clickedItemIndex).getVoteAverage()));

        // Put the overview
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW,
                mMoviesList.get(clickedItemIndex).getOverview());

        // Put movie id
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID,
                mMoviesList.get(clickedItemIndex).getMovieId());

        return intent;
    }
}
