package com.example.android.popularmovies.Models;

import android.net.Uri;
import android.widget.ImageView;

public class Movie {

    // Movie title in english
    private String mTitle;

    // Release date is stored as a string in the api (ex. 2014-10-22)
    private String mReleaseDate;

    // An image of the movie poster in size w185
    private ImageView mMoviePoster;

    // The poster
    private Uri mPosterUri;

    // The average rating of the movie between 1-10
    private int mVoteAverage; //TODO: Should be a float

    // The movie's plot as a string
    private String mOverview;

    public Movie(){

    }

    public String getTitle() { return mTitle; }

    public void setTitle(String title) { this.mTitle = title; }

    public String getReleaseDate() { return mReleaseDate; }

    public void setReleaseDate(String releaseDate) { this.mReleaseDate = releaseDate; }

    public ImageView getMoviePoster() { return mMoviePoster; }

    public void setMoviePoster(ImageView moviePoster) { this.mMoviePoster = moviePoster; }

    public Uri getPosterUri(){return mPosterUri;}

    public void setPosterUri(Uri posterUri){ this.mPosterUri = posterUri; }

    public int getVoteAverage() { return mVoteAverage; }

    public void setVoteAverage(int voteAverage) { this.mVoteAverage = voteAverage; }

    public String getOverview() { return mOverview; }

    public void setOverview(String overview) { this.mOverview = overview; }

}
