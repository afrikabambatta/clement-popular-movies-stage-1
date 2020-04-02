package com.example.android.popularmovies.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Utils.JsonUtils;

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieItemClickListener{

    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

    private ArrayList<Movie> mMoviesList;

    private Toast mToast; // TODO: Delete toasts from the app

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
        //mRecyclerView.setHasFixedSize(true);
        // create adapter for recycler view and give it dummy data
        mAdapter = new MovieGridAdapter(this);
        // set the adapter on the recycler
        mRecyclerView.setAdapter(mAdapter);
        loadMoviesList();
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        String toastMessage = "Item #" + (clickedItemIndex + 1) + " clicked.";
        if(mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }

    public void loadMoviesList(){
        // TODO: retrieve the current setting of the spinner then pass it into FetchMoviePoster
        new FetchMoviesTask().execute();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Void> {

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }

        @Override
        protected Void doInBackground(String... filterOption) {

            URL movieListRequestUrl;

            // gets a url that will return a list of movies sorted by popularity
            if(filterOption.equals("popularity")){
                movieListRequestUrl = TheMovieDB.getMoviesSortedByPopularity();
            } else if(filterOption.equals("vote_average")){
                movieListRequestUrl = TheMovieDB.getMoviesSortedByVoteAvg();
            } else {
                return null; //TODO: Fix this default case, throw an exception or something
            }


            try {
                // use the url to get a json string of the movie list
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieListRequestUrl);

                // now take the json string and make a lot of movies out of it

                mMoviesList = JsonUtils.parseMovieJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(String[] weatherData) {
//
//        }
    }
}
