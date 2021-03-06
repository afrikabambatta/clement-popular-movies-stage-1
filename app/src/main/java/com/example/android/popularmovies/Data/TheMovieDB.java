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

    // Param to sort movies by popularity in descending order
    public static final String POPULARITY_DESCENDING = "popularity.desc";

    // Param to sort movies by ratings in descending order
    public static final String VOTE_AVG_DESCENDING = "vote_average.desc";

    // Param to display movies that have been favorited
    public static final String FAVORITES = "favorites";

    // Log cat tag for debugging
    private static final String TAG = TheMovieDB.class.getSimpleName();

    // The movie api website url
    private static final String MOVIE_API_URL = "https://api.themoviedb.org/3/";

    // QUESTION: How to go about naming these static finals so that I can build URL
    // The url component to access movies
    private static final String MOVIE_URL = "movie/";

    // The url component to access videos
    private static final String VIDEO = "videos";

    // The url component to access reviews
    private static final String REVIEW = "reviews";


    // The url component to access movies by popularity
    private static final String DISCOVER_MOVIE = "discover/movie";

    /**
     * base_url - the first component of the query
     * the url to access the movie database api
     */
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    /**
     * size - the second component of the query
     * the size of the movie poster image, we will be using w185
     */
    private static final String SIZE = "w185";

    /**
     * API Key used to access The Movie API. Appended to the end of the query
     * MUST REMOVE BEFORE SHARING PROJECT
     */
    private static final String API_KEY = Constants.API_KEY;

    /**
     * The param argument used to insert your api key
     */
    private static final String API_PARAM = "api_key";

    /**
     * The param argument used to sort the movie list by popularity or vote average
     */
    private static final String SORT_BY_PARAM = "sort_by";

    /**
     * Specify a sort order and this function will return a list of movies accordingly
     *
     * @param sortOrder Determines the order that the list of movies returned
     *                  from the url will be sorted. Currently only POPULARITY_DESCENDING and
     *                  VOTE_AVG_DESCENDING is supported.
     * @return A URL that can query a json array of movies
     */
    public static URL buildMovieListUrl(String sortOrder){

        // Build the URI
        Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendEncodedPath(DISCOVER_MOVIE)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(SORT_BY_PARAM, sortOrder)
                .build();

        // Convert the URI to a URL
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This function is used to build the movie poster path. In this project it is needed for
     * Picasso to get the poster path and load it into the image view.
     *
     * @param posterPath The poster path associated with a movie object
     * @return A string url that links to an image of a movie poster
     */
    public static String buildMoviePosterPath(String posterPath){

        /*
         * For some reason the poster path is prefixed with '/'
         * so we need to get rid of it in order to build the URI in the correct format
         */
        posterPath = posterPath.substring(1);

        Uri posterImageUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendEncodedPath(SIZE)
                .appendEncodedPath(posterPath)
                .build();

        return posterImageUri.toString();
    }

    /**
     * Build the url to get movie trailers
     *
     * @param movieId Id of the movie you wish to get trailer information from
     * @return Json object containing the movie's trailer information
     */
    public static String buildMovieTrailersListUrl(String movieId){
        Uri movieTrailersListUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendEncodedPath(MOVIE_URL)
                .appendEncodedPath(movieId)
                .appendEncodedPath(VIDEO)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        return movieTrailersListUri.toString();
    }

    /**
     * Build url to retrieve a movie's reviews as a json string
     *
     * @param movieId This movie id is used to get all reviews associated with that movie
     * @return A URL that when queried will return a json string containing review information
     */
    public static URL buildReviewsListUrl(Integer movieId){
        Uri movieReviewsUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendEncodedPath(MOVIE_URL)
                .appendEncodedPath(movieId.toString())
                .appendEncodedPath(REVIEW)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        // Convert the URI to a URL
        URL url = null;
        try {
            url = new URL(movieReviewsUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Build url to retrieve a movie's reviews as a json string
     *
     * @param movieId This movie id is used to get all reviews associated with that movie
     * @return A URL that when queried will return a json string containing review information
     */
    public static URL buildTrailersListUrl(Integer movieId){
        Uri movieReviewsUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendEncodedPath(MOVIE_URL)
                .appendEncodedPath(movieId.toString())
                .appendEncodedPath(VIDEO)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        // Convert the URI to a URL
        URL url = null;
        try {
            url = new URL(movieReviewsUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }
}
