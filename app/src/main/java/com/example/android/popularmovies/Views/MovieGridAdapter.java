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
import java.util.List;

/**
 * This class is needed to create the grid recycler view in MainActivity. It adapts information
 * from an array list of movies, using it to create viewholders each containg an image view of a
 * movie poster.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    final private MovieItemClickListener mOnClickListener;

    // This private movie list variable will contain the movie list extract from TheMovieDB
    private List<Movie> mMoviesList;

    /**
     * Interface for ClickListener
     */
    public interface MovieItemClickListener {
        void onMovieItemClick(int clickedItemIndex);
    }

    /**
     4* The constructor which initializes the listener and movie list
     *
     * @param listener
     */
    public MovieGridAdapter(MovieItemClickListener listener){
        mOnClickListener = listener;
    }

    /**
     * Must be called when the movie list changes. Let's the recycler view know how to
     * display the new data set.
     *
     * @param moviesList A list of movies containing basic information
     */
    public void setMoviesList(List<Movie> moviesList){
        mMoviesList.clear();
        mMoviesList.addAll(moviesList);
        notifyDataSetChanged();
    }

    /**
     * This function creates the each viewholder using the layout movieposter_item
     *
     * @param viewGroup The view group that called this function
     * @param viewType ?
     * @return A movie viewholder which in this case is an image view containing a movie poster
     */

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

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
     * The Recycler class calls this to bind data to the viewholder as they get recycled
     *
     * @param holder The movie viewholder that will be binded with data
     * @param position The index of the movie in the mMoviesList that will supply the data to bind
     */

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        /*
         * Use picasso to bind a movie poster to the image view in the viewholder
         * If the movie does not contain a poster path, use the default poster in drawables
         */
        if(mMoviesList.get(position).getPosterPath() == null
                || mMoviesList.get(position).getPosterPath().isEmpty()){
            Picasso
                    .get()
                    .load(R.drawable.unavailable)
                    .into(holder.mMoviePoster);
        }
        else {
            Picasso
                    .get()
                    .load(mMoviesList.get(position).getPosterPath())
                    .into(holder.mMoviePoster);
        }
    }

    /**
     * Returns the total number of items the recycler view needs to display given the list of
     * movies returned from TheMovieDB API.
     *
     * @return The total number of movies in the movie list returned from TheMovieDB API
     */
    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    /**
     * This nested class defines a movie viewholder that is only compatible with this adapter.
     */
    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{


        private ImageView mMoviePoster;

        /**
         * Constructor that assigns the image view to viewholder
         *
         * @param itemView The movie viewholder view
         */
        public MovieViewHolder(View itemView){
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        /**
         * Clicklistener function that helps Recycler know which viewholder was clicked by
         * using the position returned by the adapter when it's clicked
         *
         * @param view The viewholder that was clicked
         */
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onMovieItemClick(clickedPosition);
        }
    }
}
