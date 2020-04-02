package com.example.android.popularmovies.Utils;

import android.net.Uri;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.Models.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    /**
     * The Movie API name value pairs
     */
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String PLOT = "plot";
    private static final String POSTER_PATH = "poster_path";

    public static ArrayList<Movie> parseMovieJson(String movieListJsonStr){

        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            // make the json string we passed into the function into a json object
            JSONObject originalJson = new JSONObject(movieListJsonStr);

            // get the results array from the json object
            JSONArray resultsJsonArray = originalJson.optJSONArray(RESULTS);

            // make movie objects equal to the size of the results array
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                // add a new movie for every index in the results array
                movies.add(new Movie());

                // get the movie at the current index and turn it into an json object
                JSONObject currentMovieObject = resultsJsonArray.optJSONObject(i);

                // from that json object fill the movie with the appropriate data
                movies.get(i).setTitle(currentMovieObject.optString(TITLE));
                movies.get(i).setReleaseDate(currentMovieObject.optString(RELEASE_DATE));
                movies.get(i).setVoteAverage(currentMovieObject.optInt(VOTE_AVERAGE));
                movies.get(i).setPlot(currentMovieObject.optString(PLOT));

                // build the poster path then use picasso to load it into movie poster
                Uri posterPath =
                        TheMovieDB.getMoviePosterPath(currentMovieObject.optString(POSTER_PATH));

                Picasso
                        .get()
                        .load(posterPath)
                        .into(movies.get(i).getMoviePoster());
            }
        } catch (JSONException e){ //
            e.printStackTrace();
        }
        return movies;
    }
}
