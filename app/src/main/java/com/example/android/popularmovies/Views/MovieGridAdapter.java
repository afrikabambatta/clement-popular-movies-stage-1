package com.example.android.popularmovies.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.Models.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    final private MovieItemClickListener mOnClickListener;
    private ArrayList<Movie> mMoviesList;

    /**
     * Interface for ClickListener
     */
    public interface MovieItemClickListener {
        void onMovieItemClick(int clickedItemIndex);
    }
    /**
    * Constructor for Adapter
    *
    */
    public MovieGridAdapter(MovieItemClickListener listener){
        // I can pass in the movieList or just its size
        mOnClickListener = listener;
        mMoviesList = new ArrayList<>();
    }

    public void setMoviesList(ArrayList<Movie> moviesList){
        mMoviesList = moviesList;
        notifyDataSetChanged();
    }

    /**
     * This method basically inflates the viewholder
     *
     * @param viewGroup
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Get the context of the application
        Context context = viewGroup.getContext();
        // Create an inflater in this context
        LayoutInflater inflater = LayoutInflater.from(context);
        // Use the inflater to inflate the movieposter_item in viewGroup
        View view = inflater.inflate(R.layout.movieposter_item, viewGroup, false);

        // Create a movie view holder passing in this inflated view
        return new MovieViewHolder(view);
    }

    /**
     * Recycler view calls when new data needs to be binded to the viewholder
     *
     * @param holder
     * @param position
     */

    // When the adapter binds data to the viewholder it calls this passing it position in rv
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // bind the movie poster image
        Picasso
                .get()
                .load(mMoviesList.get(position).getPosterUri())
                .into(holder.mImage);

    }

    /**
     * Returns the total items in data. Needed by recycler view to know how many items to display.
     *
     * @return
     */

    // Recycler view needs to know how many items in the data to make space for it
    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    /**
     * Class definition for view holder. Also, implements a ClickListener for interaction
     *
     */

    // Nested viewholder class because only used within this adapter
    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView mImage;

        /**
         * Constructor that assigns movieItem xml to viewholder
         *
         * @param itemView
         */
        public MovieViewHolder(View itemView){
            super(itemView);
            mImage = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        /**
         * Clicklistener function that helps Recycler know which viewholder was clicked
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onMovieItemClick(clickedPosition);

        }
    }
}
