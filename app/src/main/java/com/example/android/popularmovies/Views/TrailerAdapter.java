package com.example.android.popularmovies.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.Models.Trailer;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    final private TrailerItemClickListener mOnClickListener;

    // Contains the movie's trailers
    private ArrayList<Trailer> mTrailers;

    /**
     * Called when trailer is clicked
     */
    public interface TrailerItemClickListener {
        void onTrailerItemClick(int clickedItemIndex);
    }

    /**
     4* The constructor which initializes the listener
     *
     * @param listener
     */
    public TrailerAdapter(TrailerItemClickListener listener){
        mOnClickListener = listener;
        mTrailers = new ArrayList<>();
    }

    /**
     * Called when the trailer list changes. Let's the recycler view know how to
     * display the new data set.
     *
     * @param trailers A list of trailer ids for a particular movie
     */
    public void setTrailerList(ArrayList<Trailer> trailers){
        mTrailers.clear();
        mTrailers.addAll(trailers);
        notifyDataSetChanged();
    }

    /**
     * This function creates the each viewholder using the layout trailer_item
     *
     * @param viewGroup The view group that called this function
     * @param viewType ?
     * @return A trailer viewholder which in this case simply contains the trailer name
     */

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // Get the context of the application
        Context context = viewGroup.getContext();

        // Create an inflater in this context
        LayoutInflater inflater = LayoutInflater.from(context);

        // Use the inflater to inflate the movieposter_item in viewGroup
        View view = inflater.inflate(R.layout.trailer_item, viewGroup, false);

        // Create a movie view holder passing in this inflated view
        return new TrailerViewHolder(view);
    }

    /**
     * The Recycler class calls this to bind data to the viewholder as they get recycled
     *
     * @param holder The movie viewholder that will be binded with data
     * @param position The index of the trailer in mTrailers that will supply the data to bind
     */

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        // Bind the name of the trailer
        holder.mTrailerName.setText(mTrailers.get(position).getName());
    }

    /**
     * Returns the total number of items the recycler view needs to display given the list of
     * movies returned from TheMovieDB API.
     *
     * @return The total number of trailers for the movie
     */
    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    /**
     * This nested class defines a trailer viewholder that is only compatible with this adapter.
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{


        private TextView mTrailerName;

        /**
         * Constructor that assigns the image view to viewholder
         *
         * @param itemView The trailer viewholder view
         */
        public TrailerViewHolder(View itemView){
            super(itemView);
            mTrailerName = itemView.findViewById(R.id.tv_trailer_name);
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
            mOnClickListener.onTrailerItemClick(clickedPosition);
        }
    }
}
