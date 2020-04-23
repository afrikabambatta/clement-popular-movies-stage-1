package com.example.android.popularmovies.Models;

public class Review {

    private String mAuthor;
    private String mContent;

    public Review(){

    }

    public String getAuthor(){return mAuthor;}

    public void setAuthor(String author){this.mAuthor = author;}

    public String getContent(){return mContent;}

    public void setContent(String content){this.mContent = content;}
}
