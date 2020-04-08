package com.example.android.popularmovies.Views;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_OVERVIEW = "extra_overview";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_VOTE_AVERAGE = "extra_vote_average";
    public static final String EXTRA_POSTER_URL = "extra_poster_url";

    private static final int DEFAULT_POSITION = -1;

    TextView titleDisplay;
    ImageView posterDisplay;
    TextView releaseDateDisplay;
    TextView voteAverageDisplay;
    TextView overviewDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleDisplay = findViewById(R.id.tv_title);
        posterDisplay = findViewById(R.id.iv_detail_poster);
        releaseDateDisplay = findViewById(R.id.tv_release_date);
        voteAverageDisplay = findViewById(R.id.tv_vote_avg);
        overviewDisplay = findViewById(R.id.tv_overview);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if(intent.hasExtra(DetailActivity.EXTRA_TITLE)) {
            String title = intent.getStringExtra(DetailActivity.EXTRA_TITLE);
            titleDisplay.setText(title);
        }
        String posterUrl = intent.getStringExtra(EXTRA_POSTER_URL);
        String releaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE);
        String voteAverage = intent.getStringExtra(EXTRA_VOTE_AVERAGE);
        String overview = intent.getStringExtra(EXTRA_OVERVIEW);


        Picasso
                .get()
                .load(posterUrl)
                .into(posterDisplay);
        releaseDateDisplay.setText(releaseDate);
        voteAverageDisplay.setText(voteAverage);
        overviewDisplay.setText(overview);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Movie data not available", Toast.LENGTH_SHORT).show(); // TODO: Update to use strings resource
    }

    private void generateDetails(Movie movie){

    }
}
