package com.example.android.popularmovies.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;

import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieItemClickListener{

    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

    private

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

    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        String toastMessage = "Item #" + (clickedItemIndex + 1) + " clicked.";
        if(mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }

    public class FetchMoviePosterTask extends AsyncTask<String, Void, String[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
//            if (weatherData != null) {
//                showWeatherDataView();
//                mForecastAdapter.setWeatherData(weatherData);
//            } else {
//                showErrorMessage();
//            }
        }
    }
}
