package com.example.android.popularmovies.Models;

public class Trailer {

    // Used to reference the trailer on youtube
    private String mYoutubeKey;

    // Trailer name
    private String mName;

    public Trailer(){

    }

    public String getYoutubeKey(){return mYoutubeKey;}

    public void setYoutubeKey(String youtubeKey){this.mYoutubeKey = youtubeKey;}

    public String getName(){return mName;}

    public void setName(String name){this.mName = name;}
}
