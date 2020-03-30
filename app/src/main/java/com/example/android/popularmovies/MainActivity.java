package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieItemClickListener{

    private final LinkedList<String> mDummyMovieData = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 100; i++){
            mDummyMovieData.add("Movie #" + (i+1));
        }

        // get a handle to the recycler view
        mRecyclerView = findViewById(R.id.rv_movies);
        // give the recycler default layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // let the recycler all viewholders are the same size for optimization
        mRecyclerView.setHasFixedSize(true);
        // create adapter for recycler view and give it dummy data
        mAdapter = new MovieGridAdapter(mDummyMovieData, this);
        // set the adapter on the recycler
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        String toastMessage = "Item #" + (clickedItemIndex + 1) + " clicked.";
        if(mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }
}
