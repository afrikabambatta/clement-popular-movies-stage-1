package com.example.android.popularmovies.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Handles the display of the movie details page
 */
public class DetailActivity extends AppCompatActivity {

    /*
     * The intent extras constants which all contain movie object information
     */
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_OVERVIEW = "extra_overview";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_VOTE_AVERAGE = "extra_vote_average";
    public static final String EXTRA_POSTER_URL = "extra_poster_url";

    /*
     * Make a reference to all the views in detail activity
     */
    @Bind(R.id.tv_title) TextView titleDisplay;
    @Bind(R.id.iv_detail_poster) ImageView posterDisplay;
    @Bind(R.id.tv_release_date) TextView releaseDateDisplay;
    @Bind(R.id.tv_vote_avg) TextView voteAverageDisplay;
    @Bind(R.id.tv_overview) TextView overviewDisplay;

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

        // If there is no intent, then something went wrong and we should close the app
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // Load movie title using intent extra
        if(intent.hasExtra(DetailActivity.EXTRA_TITLE)) {
            String title = intent.getStringExtra(DetailActivity.EXTRA_TITLE);
            titleDisplay.setText(title);
        }

        // Load movie poster or if poster path url is null, then set unavailable resource by default
        if(intent.hasExtra(DetailActivity.EXTRA_POSTER_URL)) {
            String posterUrl = intent.getStringExtra(EXTRA_POSTER_URL);
            if(posterUrl == null) {
                Picasso.get().load(R.drawable.unavailable).into(posterDisplay);
            } else {
                Picasso.get().load(posterUrl).into(posterDisplay);
            }
        }

        // Load movie release date
        if(intent.hasExtra(DetailActivity.EXTRA_RELEASE_DATE)) {
            String releaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE);
            releaseDateDisplay.setText(releaseDate);
        }

        // Load movie voting average
        if(intent.hasExtra(DetailActivity.EXTRA_VOTE_AVERAGE)) {

            String voteAverage = intent.getStringExtra(EXTRA_VOTE_AVERAGE);
            voteAverageDisplay.setText(voteAverage);
        }

        // Load movie overview
        if(intent.hasExtra(DetailActivity.EXTRA_OVERVIEW)) {
            String overview = intent.getStringExtra(EXTRA_OVERVIEW);
            overviewDisplay.setText(overview);
        }
    }

    /**
     * Error message that is used if the intent that called this activity is null
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.intent_error_msg, Toast.LENGTH_SHORT).show();
    }
}
