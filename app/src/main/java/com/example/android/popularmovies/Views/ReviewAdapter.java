package com.example.android.popularmovies.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.Models.Review;
import com.example.android.popularmovies.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    ArrayList<Review> mReviews;

    public ReviewAdapter(){
        mReviews = new ArrayList<>();
    }

    public void setReviewsList(ArrayList<Review> reviews){
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // Get the context of the application
        Context context = viewGroup.getContext();

        // Create an inflater in this context
        LayoutInflater inflater = LayoutInflater.from(context);

        // Use the inflater to inflate the movieposter_item in viewGroup
        View view = inflater.inflate(R.layout.review_item, viewGroup, false);

        // Create a movie view holder passing in this inflated view
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        // Bind the review's author and content
        holder.authorTextView.setText(mReviews.get(position).getAuthor());
        holder.contentTextView.setText(mReviews.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView authorTextView;
        private TextView contentTextView;

        /**
         * Constructor that assigns the image view to viewholder
         *
         * @param itemView The movie viewholder view
         */
        public ReviewViewHolder(View itemView){
            super(itemView);
            authorTextView = itemView.findViewById(R.id.tv_author);
            contentTextView = itemView.findViewById(R.id.tv_content);
        }
    }
}
