package com.example.android.popularmovies.Data;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.Utils.NetworkUtils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Data class used to query TheMovieDB API.
 */
public class TheMovieDbApi {

    private static final String TAG = TheMovieDbApi.class.getSimpleName();

    // the url to access the api
    private static final String MOVIE_API_URL = "https://api.themoviedb.org/3/";

    // the url to access movies by popularity
    private static final String DISCOVER_MOVIE = "discover/movie";

    // sort movies by popularity in descending order
    private static final String POPULARITY_DESCENDING = "popularity.desc";

    // sort movies by ratings in descending order
    private static final String VOTE_AVG_DESCENDING = "vote_average.desc";

    /**
     * base_url - the first component of the query
     * the url access the movie database api
     */
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    /**
     * size - the second component of the query
     * the size of the movie poster image, we will be using w185
     */
    private static final String SIZE = "w185";

    /**
     * API Key used to access The Movie API. MUST REMOVE BEFORE SHARING PROJECT
     */
    private static final String API_KEY = "2f3abf24b6f86fb6dfbc22b56358cb04";

    /**
     * Params
     */
    private static final String API_PARAM = "api_key";
    private static final String SORT_BY_PARAM = "sort_by";

    private static URL buildUrl(String sortOrder){
        Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendPath(DISCOVER_MOVIE)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(SORT_BY_PARAM, sortOrder)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getMoviePosterPath(String posterPath){

        Uri.Builder builder = new Uri.Builder();
        String imageUrl = builder.appendPath(IMAGE_BASE_URL)
                .appendPath(SIZE)
                .appendPath(posterPath)
                .build()
                .toString();

        return imageUrl;
    }

}
