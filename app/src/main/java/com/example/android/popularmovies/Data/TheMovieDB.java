package com.example.android.popularmovies.Data;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.Utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Data class used to query TheMovieDB API.
 */
public class TheMovieDB {

    // log cat tag for debugging
    private static final String TAG = TheMovieDB.class.getSimpleName();

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
//    private static final String SIZE = "w185";
    private static final String SIZE = "w342";

    /**
     * API Key used to access The Movie API. MUST REMOVE BEFORE SHARING PROJECT
     */
    private static final String API_KEY = Constants.API_KEY;

    /**
     * Params
     */
    private static final String API_PARAM = "api_key";
    private static final String SORT_BY_PARAM = "sort_by";

    /**
     * Call this function to get a list of movies sorted by popularity in descending order
     * @return
     */
    public static URL getMoviesSortedByPopularity(){
        return buildMovieListUrl(POPULARITY_DESCENDING);
    }

    /**
     * Call this function to get a list of movies sorted by vote avg in descending order
     * @return
     */
    public static URL getMoviesSortedByVoteAvg(){
        return buildMovieListUrl(VOTE_AVG_DESCENDING);
    }

    /**
     * Specify a sort order and this function will return a list of movies accordingly
     * @param sortOrder determines the order that the list of movies returned
     *                 from the url will be sorted
     * @return a url that can query a json array of movies
     */
    private static URL buildMovieListUrl(String sortOrder){
        Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendEncodedPath(DISCOVER_MOVIE)
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

    public static Uri getMoviePosterPath(String posterPath){

        // for some reason the poster path includes '/' so we need to get rid of it to build the uri
        posterPath = posterPath.substring(1);

        Uri posterImageUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(SIZE)
                .appendEncodedPath(posterPath)
                .build();

        return posterImageUri;
    }

}
