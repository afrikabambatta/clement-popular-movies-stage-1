package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Json utility class dedicated to parsing the json string returned from TheMovieDB API. It will
 * parse the json and return a list of movies and their information.
 */
public class JsonUtils {

    /*
     * List of constants for TheMovieDB API name value pairs
     */
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String POSTER_PATH = "poster_path";

    /**
     * Parses the json string received from the http request to TheMovieDB API. Will produce an
     * array list of movies resulted form the json string.
     *
     * @param movieListJsonStr
     * @return
     */
    public static ArrayList<Movie> parseMovieJson(String movieListJsonStr) {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            // get the json object from the json string passed into this function
            JSONObject originalJsonObject = new JSONObject(movieListJsonStr);

            // get the movie results array from the json object
            JSONArray resultsJsonArray = originalJsonObject.optJSONArray(RESULTS);

            // extract information from the json array and use it to make movie objects
            for (int i = 0; i < resultsJsonArray.length(); i++) {

                // add a new movie for every index in the results array
                movies.add(new Movie());

                // get the movie at the current index and turn it into an json object
                JSONObject currentMovieObject = resultsJsonArray.optJSONObject(i);

                // use the json object to fill the movie with the appropriate data
                movies.get(i).setTitle(currentMovieObject.optString(TITLE));
                movies.get(i).setReleaseDate(currentMovieObject.optString(RELEASE_DATE));
                movies.get(i).setVoteAverage((currentMovieObject.optDouble(VOTE_AVERAGE)));
                movies.get(i).setOverview(currentMovieObject.optString(OVERVIEW));
                String posterPath =
                        TheMovieDB.buildMoviePosterPath(currentMovieObject.optString(POSTER_PATH));
                movies.get(i).setPosterPath(posterPath);
            }
        } catch (JSONException e){ //
            e.printStackTrace();
        }
        return movies;
    }
}
