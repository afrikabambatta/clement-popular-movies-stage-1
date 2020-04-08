package com.example.android.popularmovies.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Utils.JsonUtils;

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieItemClickListener {

    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

    private ArrayList<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // get a handle to the recycler view
        mRecyclerView = findViewById(R.id.rv_movies);
        // give the recycler default layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager
                (this, 2, RecyclerView.VERTICAL, false));
        // let the recycler all viewholders are the same size for optimization
        mRecyclerView.setHasFixedSize(true);
        // create adapter for recycler view and give it dummy data
        mAdapter = new MovieGridAdapter(this);
        // set the adapter on the recycler
        mRecyclerView.setAdapter(mAdapter);

        loadMoviesList();

    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent = putDetailExtras(intent, clickedItemIndex);
        startActivity(intent);
    }

    public Intent putDetailExtras(Intent intent, int clickedItemIndex){
        String title = mMoviesList.get(clickedItemIndex).getTitle();
        intent.putExtra(DetailActivity.EXTRA_TITLE, title);
        String posterUrl = mMoviesList.get(clickedItemIndex).getPosterUri().toString();
        intent.putExtra(DetailActivity.EXTRA_POSTER_URL, posterUrl);
        String releaseDate = mMoviesList.get(clickedItemIndex).getReleaseDate();
        intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, releaseDate);
        String voteAverage = String.valueOf(mMoviesList.get(clickedItemIndex).getVoteAverage());
        intent.putExtra(DetailActivity.EXTRA_VOTE_AVERAGE, voteAverage);
        String overview = mMoviesList.get(clickedItemIndex).getOverview();
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW, overview);
        return intent;
    }

    public void loadMoviesList() {
        // TODO: retrieve the current setting of the spinner then pass it into FetchMoviePoster
        new FetchMoviesTask().execute("popularity");

    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... filterOption) {

            URL movieListRequestUrl;

            // gets a url that will return a list of movies sorted by popularity
            if (filterOption[0].equals("popularity")) {
                movieListRequestUrl = TheMovieDB.getMoviesSortedByPopularity();
            } else if (filterOption[0].equals("vote_average")) {
                movieListRequestUrl = TheMovieDB.getMoviesSortedByVoteAvg();
            } else {
                return null; //TODO: Fix this default case, throw an exception or something
            }


            try {
                // use the url to get a json string of the movie list
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieListRequestUrl);

                return jsonMovieResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonMovieResponse) {

            // parse json string to populate movies list
            mMoviesList = JsonUtils.parseMovieJson(jsonMovieResponse);

            // pass the adapter the new movies list
            mAdapter.setMoviesList(mMoviesList);
        }
    }
}
