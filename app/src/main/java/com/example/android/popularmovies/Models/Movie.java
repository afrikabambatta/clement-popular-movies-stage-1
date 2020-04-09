package com.example.android.popularmovies.Models;

import android.widget.ImageView;

/**
 * The movie class contains information that will be used to populate the views on Detail Activity
 */
public class Movie {

    // Movie title in english
    private String mTitle;

    // Release date is stored as a string in the API (ex. 2014-10-22)
    private String mReleaseDate;

    // An image of the movie poster in size w185
    private ImageView mMoviePoster;

    // The poster path as a string
    private String mPosterPath;

    // The average rating of the movie as double between 1-10
    private double mVoteAverage;

    // The movie's plot
    private String mOverview;

    /**
     * Empty default constructor
     */
    public Movie(){

    }

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
    public String getPosterPath(){return mPosterPath;}

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
