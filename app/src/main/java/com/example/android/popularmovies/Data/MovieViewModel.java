package com.example.android.popularmovies.Data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.android.popularmovies.Models.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    private LiveData<List<Movie>> mMovies;

    private MutableLiveData<String> mFilter;

    public MovieViewModel (Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mMovies = mRepository.getMovies(TheMovieDB.POPULARITY_DESCENDING); // default to popularity
        mFilter = mRepository.getFilter();
    }

    public void setFilter(String filter) {
        this.mFilter.setValue(filter);
    }

    public LiveData<List<Movie>> getMovies() {
        // when mFilter changes, get all movies will be called passing in mFilter as a non live data
        mMovies = Transformations.switchMap(mFilter, filter->mRepository.getMovies(filter));
        return mMovies;
    }



    //TODO: Add transformation filter in here


    public void insert(Movie movie) {
        mRepository.insert(movie);
    }
}
