package com.example.android.popularmovies.Models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * The movie class contains information that will be used to populate the views on Detail Activity
 */
@Entity(tableName="favorites")
public class Movie {

    // Movie id for database
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    private int mMovieId;

    // Movie title in english
    @ColumnInfo(name="title")
    private String mTitle;

    // Release date is stored as a string in the API (ex. 2014-10-22)
    @ColumnInfo(name="release_date")
    private String mReleaseDate;

    // An image of the movie poster in size w185
    @Ignore
    private ImageView mMoviePoster;

    // Poster path as a string
    @ColumnInfo(name="poster_path")
    private String mPosterPath;

    // Average rating of the movie as double between 1-10
    @ColumnInfo(name="vote_average")
    private double mVoteAverage;

    // Movie's plot
    @ColumnInfo(name="overview")
    private String mOverview;

    //QUESTION: I think I need a field in here that tells me whether this movie is favorited or not

    /**
     * Empty default constructor
     */
    public Movie(){

    }

    public Movie(int movieId, String title, String releaseDate, String posterPath, double voteAverage, String overview){
        this.mMovieId = movieId;
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mPosterPath = posterPath;
        this.mVoteAverage = voteAverage;
        this.mOverview = overview;
    }

    /**
     * @return Movie id in database
     */
    public int getMovieId() { return mMovieId; }

    /**
     * @param movieId Movie id in database
     */
    public void setMovieId(int movieId) { this.mMovieId = movieId; }

    /**
     * @return Title of the movie
     */
    public String getTitle() { return mTitle; }

    /**
     * @param title Title of the movie
     */
    public void setTitle(String title) { this.mTitle = title; }

    /**
     * @return Movie release date as a string formatted as follows: year-month-day
     */
    public String getReleaseDate() { return mReleaseDate; }

    /**
     * @param releaseDate Movie releae date as a string formatted as follows: year-month-day
     */
    public void setReleaseDate(String releaseDate) { this.mReleaseDate = releaseDate; }

    /**
     * @return Movie poster url as a string that Picasso can use to apply to an image view
     */
    public String getPosterPath(){
        if (mPosterPath != null) {
            return mPosterPath.replaceFirst("http", "https");
        } else {
            return mPosterPath;
        }
    }

    /**
     * @param posterPath Movie poster url as a string that Picasso can use to apply to an image view
     */
    public void setPosterPath(String posterPath){ this.mPosterPath = posterPath; }

    /**
     * @return Average movie rating returned as a double containing 1 decimal place
     */
    public double getVoteAverage() { return mVoteAverage; }

    /**
     * @param voteAverage Average movie rating returned as a double containing 1 decimal place
     */
    public void setVoteAverage(double voteAverage) { this.mVoteAverage = voteAverage; }

    /**
     * @return A short overview/synopsis of the movie
     */
    public String getOverview() { return mOverview; }

    /**
     * @param overview A short overview/synopsis of the movie
     */
    public void setOverview(String overview) { this.mOverview = overview; }

}
