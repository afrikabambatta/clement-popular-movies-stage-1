package com.example.android.popularmovies.Models;

import android.widget.ImageView;

public class Movie {

    // Movie title in english
    private String mTitle;

    // Release date is stored as a string in the api (ex. 2014-10-22)
    private String mReleaseDate;

    // An image of the movie poster in size w185
    private ImageView mMoviePoster;

    // The average rating of the movie between 1-10
    private int mVoteAverage;

    // The movie's plot as a string
    private String mPlot;

    public Movie(){}

    public Movie(String title, String releaseDate, ImageView moviePoster, int voteAverage, String plot){
        mTitle = title;
        mReleaseDate = releaseDate;
        mMoviePoster = moviePoster;
        mVoteAverage = voteAverage;
        mPlot = plot;
    }

    public String getTitle() { return mTitle; }

    public void setTitle(String mTitle) { this.mTitle = mTitle; }

    public String getReleaseDate() { return mReleaseDate; }

    public void setReleaseDate(String mReleaseDate) { this.mReleaseDate = mReleaseDate; }

    public ImageView getMoviePoster() { return mMoviePoster; }

    public void setMoviePoster(ImageView mMoviePoster) { this.mMoviePoster = mMoviePoster; }

    public int getVoteAverage() { return mVoteAverage; }

    public void setVoteAverage(int mVoteAverage) { this.mVoteAverage = mVoteAverage; }

    public String getPlot() { return mPlot; }

    public void setPlot(String mPlot) { this.mPlot = mPlot; }

}
