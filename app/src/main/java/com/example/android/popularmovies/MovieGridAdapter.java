package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    private final LinkedList<String> mMovieList;

    public MovieGridAdapter(LinkedList<String> movieList){
        // NOTE: I can pass in the movieList or just its size
        this.mMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // NOTE: Get the context of the application
        Context context = viewGroup.getContext();
        // NOTE: Create an inflater in this context
        LayoutInflater inflater = LayoutInflater.from(context);
        // NOTE?: Use the inflater to inflate the movieposter_item in viewGroup
        View view = inflater.inflate(R.layout.movieposter_item, viewGroup, false);

        // NOTE: Create a movie view holder passing in this inflated view
        return new MovieViewHolder(view);
    }

    // NOTE: When the adapter binds data to the viewholder it calls this passing it position in rv
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // NOTE: pass in the position to retrieve movie name at position
        String mCurrent = mMovieList.get(position);
        // NOTE: recycler takes the holder and gives it new text
        holder.movieItemView.setText(mCurrent);
    }

    // NOTE: Recycler view needs to know how many items in the data to make space for it
    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    // NOTE: Nested viewholder class because only used within this adapter
    class MovieViewHolder extends RecyclerView.ViewHolder{
        private final TextView movieItemView;

        public MovieViewHolder(View itemView){
            super(itemView);
            // NOTE: This viewholder is made up of a text view, for now
            movieItemView  = itemView.findViewById(R.id.movie_poster);
        }
    }
}
