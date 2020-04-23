package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Data.TheMovieDB;
import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.Models.Review;
import com.example.android.popularmovies.Models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String TRAILER_NAME ="name";
    private static final String TRAILER_ID = "key";
    private static final String MOVIE_ID = "id";

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
            // Get the json object from the json string passed into this function
            JSONObject originalJsonObject = new JSONObject(movieListJsonStr);

            // Get the movie results array from the json object
            JSONArray resultsJsonArray = originalJsonObject.optJSONArray(RESULTS);

            // Extract information from the json array and use it to make movie objects
            for (int i = 0; i < resultsJsonArray.length(); i++) {

                // Add a new movie for every index in the results array
                movies.add(new Movie());

                // Get the movie at the current index and turn it into an json object
                JSONObject currentMovieObject = resultsJsonArray.optJSONObject(i);

                /*
                 * Use the json object to fill the movie with the appropriate data
                 */
                movies.get(i).setTitle(currentMovieObject.optString(TITLE));
                movies.get(i).setReleaseDate(currentMovieObject.optString(RELEASE_DATE));
                movies.get(i).setVoteAverage((currentMovieObject.optDouble(VOTE_AVERAGE)));
                movies.get(i).setOverview(currentMovieObject.optString(OVERVIEW));
                movies.get(i).setMovieId(currentMovieObject.optInt(MOVIE_ID));
                /*
                 * If there is no movie poster path, then set it to null
                 */
                if (currentMovieObject.optString(POSTER_PATH).equals("null")){
                    movies.get(i).setPosterPath(null);
                } else {
                    String posterPath =
                            TheMovieDB.buildMoviePosterPath(currentMovieObject.optString(POSTER_PATH));
                    movies.get(i).setPosterPath(posterPath);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // TODO: Parse for user reviews

    // TODO: Parse for movie trailers

    /**
     * Parse json string for movie's reviews. Reviews contain an author and content.
     *
     * @param jsonString The json string that will be parsed for reviews
     * @return List of reviews, each containing an author and content
     */
    public static ArrayList<Review> parseReviewsJson(String jsonString){
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            // Get the json object from the json string passed into this function
            JSONObject jsonObject = new JSONObject(jsonString);

            // Get the movie results array from the json object
            JSONArray reviewsJsonArray = jsonObject.optJSONArray(RESULTS);

            // Extract information from the json array and use it to make movie objects
            for (int i = 0; i < reviewsJsonArray.length(); i++) {

                // Add a new movie for every index in the results array
                reviews.add(new Review());

                // Get the movie at the current index and turn it into an json object
                JSONObject currentMovieObject = reviewsJsonArray.optJSONObject(i);


                /* Use the json object to fill the movie with the appropriate data*/

                reviews.get(i).setAuthor(currentMovieObject.optString(AUTHOR));
                reviews.get(i).setContent(currentMovieObject.optString(CONTENT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Parse json string for trailer information. Trailers contain a youtube key and name.
     *
     * @param jsonString The json string that will be parsed for trailers
     * @return List of trailers, each containing a youtube key and name
     */
    public static ArrayList<Trailer> parseTrailersJson(String jsonString){
        ArrayList<Trailer> trailers = new ArrayList<>();

        try {
            // Get the json object from the json string passed into this function
            JSONObject jsonObject = new JSONObject(jsonString);

            // Get the movie results array from the json object
            JSONArray trailersJsonArray = jsonObject.optJSONArray(RESULTS);

            // Extract information from the json array and use it to make movie objects
            for (int i = 0; i < trailersJsonArray.length(); i++) {

                // Add a new movie for every index in the results array
                trailers.add(new Trailer());

                // Get the movie at the current index and turn it into an json object
                JSONObject currentMovieObject = trailersJsonArray.optJSONObject(i);


                /* Use the json object to fill the movie with the appropriate data*/
                trailers.get(i).setName(currentMovieObject.optString(TRAILER_NAME));
                trailers.get(i).setYoutubeKey(currentMovieObject.optString(TRAILER_ID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }
}
