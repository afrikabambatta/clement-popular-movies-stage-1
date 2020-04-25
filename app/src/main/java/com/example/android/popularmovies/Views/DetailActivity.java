package com.example.android.popularmovies.Views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.Models.Review;
import com.example.android.popularmovies.Models.Trailer;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Handles the display of the movie details page
 */
public class DetailActivity extends AppCompatActivity
        implements TrailerAdapter.TrailerItemClickListener, View.OnClickListener{

    RecyclerView mTrailerRecyclerView;
    TrailerAdapter mTrailerAdapter;
    RecyclerView mReviewRecyclerView;
    ReviewAdapter mReviewAdapter;
    ArrayList<Review> mReviews;
    ArrayList<Trailer> mTrailers;
    ImageView mFavoriteIcon;

    /*
     * The intent extras constants which all contain movie object information
     */
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_OVERVIEW = "extra_overview";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_VOTE_AVERAGE = "extra_vote_average";
    public static final String EXTRA_POSTER_URL = "extra_poster_url";
    public static final String EXTRA_TRAILER_IDS = "extra_trailer_ids";
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    /*
     * Make a reference to all the views in detail activity
     */
    @Bind(R.id.tv_title)
    TextView titleDisplay;
    @Bind(R.id.iv_detail_poster)
    ImageView posterDisplay;
    @Bind(R.id.tv_release_date)
    TextView releaseDateDisplay;
    @Bind(R.id.tv_vote_avg)
    TextView voteAverageDisplay;
    @Bind(R.id.tv_overview)
    TextView overviewDisplay;

    /**
     * Populates the detail activity view with movie information passed through by the intent
     *
     * @param savedInstanceState Not required in this project
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Set click listener for button
        mFavoriteIcon = findViewById(R.id.iv_favorite);
        mFavoriteIcon.setOnClickListener(this);

        // If there is no intent, then something went wrong and we should close the app
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // Load movie title using intent extra
        if (intent.hasExtra(DetailActivity.EXTRA_TITLE)) {
            String title = intent.getStringExtra(DetailActivity.EXTRA_TITLE);
            titleDisplay.setText(title);
        }

        // Load movie poster or if poster path url is null, then set unavailable resource by default
        if (intent.hasExtra(DetailActivity.EXTRA_POSTER_URL)) {
            String posterUrl = intent.getStringExtra(EXTRA_POSTER_URL);
            if (posterUrl == null) {
                Picasso.get().load(R.drawable.unavailable).into(posterDisplay);
            } else {
                Picasso.get().load(posterUrl).into(posterDisplay);
            }
        }

        // Load movie release date
        if (intent.hasExtra(DetailActivity.EXTRA_RELEASE_DATE)) {
            String releaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE);
            releaseDateDisplay.setText(releaseDate);
        }

        // Load movie voting average
        if (intent.hasExtra(DetailActivity.EXTRA_VOTE_AVERAGE)) {

            String voteAverage = intent.getStringExtra(EXTRA_VOTE_AVERAGE);
            voteAverageDisplay.setText(voteAverage);
        }

        // Load movie overview
        if (intent.hasExtra(DetailActivity.EXTRA_OVERVIEW)) {
            String overview = intent.getStringExtra(EXTRA_OVERVIEW);
            overviewDisplay.setText(overview);
        }

        /* Loading Recycler View for Trailers*/

        // Get a handle to the recycler view
        mTrailerRecyclerView = findViewById(R.id.rv_trailers);

        // Apply horizontal layout manager
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));

        // Create a trailer adapter for the recycler view
        mTrailerAdapter = new TrailerAdapter(this);

        // Set the trailer adapter on the recycler view
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        /* Loading Recycler View for Reviews*/

        // Get a handle to the recycler view
        mReviewRecyclerView = findViewById(R.id.rv_reviews);

        // Apply a 2 column grid layout manager to the recycler view
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a movie adapter for the recycler view
        mReviewAdapter = new ReviewAdapter();

        // Set the movie adapter on the recycler view
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        // Get the movie id and use it to query the movie's reviews in the background
        if (intent.hasExtra(DetailActivity.EXTRA_MOVIE_ID)) {
            int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0);
            new FetchReviewsTask().execute(movieId);
            new FetchTrailersTask().execute(movieId);
        }


    }

    /**
     * Error message that is used if the intent that called this activity is null
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.intent_error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_favorite:
                mFavoriteIcon.setImageResource(R.drawable.favorite_icon_on);
                Log.v("TEST", "The Star was Clicked!");
                // TODO: Add movie into favorite zoom table
                break;
            default:
        }
    }

    @Override
    public void onTrailerItemClick(int clickedItemIndex) {
        String trailerId = String.valueOf(mTrailers.get(clickedItemIndex).getYoutubeKey());


        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + trailerId));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    // TODO:
    //  - using the movie id, use the movie db to build list of movie trailers
    //  - from the movie trailers, extract the name of the trailer
    //  - from the movie trailers, extract the video key
    //  - create an image view of youtube icon, and put the trailer name below it, use recycle view

    /**
     * AsyncTask to get the reviews of the movie passed into details activity
     */
    public class FetchReviewsTask extends AsyncTask<Integer, Void, String> {

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
         * @param movieId This id of the movie you wish to retrieve reviews for
         * @return A string representing a json array that holds data to a sorted list of movies
         */
        @Override
        protected String doInBackground(Integer... movieId) {

            //QUESTION: Is query the right name for this?
            URL reviewQuery;

            // Use the movie id to build the correct query
            reviewQuery = TheMovieDB.buildReviewsListUrl(movieId[0]);


            // Query movie review endpoint and get json string containing reviews information
            try {
                String reviewsJson = NetworkUtils.getResponseFromHttpUrl(reviewQuery);
                return reviewsJson;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        /**
         * Parse the json containing review information to create the reviews list that
         * will be used in the review recycler view
         *
         * @param reviewsJson The json string produced as a result of the background task
         */
        @Override
        protected void onPostExecute(String reviewsJson) {

            // Parse json string to create reviews list
            mReviews = JsonUtils.parseReviewsJson(reviewsJson);

            // Notify the adapter that the review list has changed
            mReviewAdapter.setReviewsList(mReviews);


        }
    }

    /**
     * AsyncTask to get the trailers of the movie passed into details activity
     */
    public class FetchTrailersTask extends AsyncTask<Integer, Void, String> {

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
         * @param movieId This id of the movie you wish to retrieve reviews for
         * @return A string representing a json array that holds data to a sorted list of movies
         */
        @Override
        protected String doInBackground(Integer... movieId) {

            //QUESTION: Is query the right name for this?
            URL trailerQuery;

            // Use the movie id to build the correct query
            trailerQuery = TheMovieDB.buildTrailersListUrl(movieId[0]);


            // Query movie review endpoint and get json string containing reviews information
            try {
                String trailersJson = NetworkUtils.getResponseFromHttpUrl(trailerQuery);
                return trailersJson;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        /**
         * Parse the json containing review information to create the reviews list that
         * will be used in the review recycler view
         *
         * @param trailersJson The json string produced as a result of the background task
         */
        @Override
        protected void onPostExecute(String trailersJson) {

            // Parse json string to create reviews list
            mTrailers = JsonUtils.parseTrailersJson(trailersJson);

            // Notify the adapter that the review list has changed
            mTrailerAdapter.setTrailerList(mTrailers);


        }
    }


    // TEST: REMOVE THIS TESTING CODE
    public void watchYoutubeVideo(View view) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "P6AaSMfXHbA"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + "P6AaSMfXHbA"));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
