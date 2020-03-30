package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<String> mDummyMovieData = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 100; i++){
            mDummyMovieData.add("Movie #" + (i+1));
        }

        // NOTE: get a handle to the recycler view
        mRecyclerView = findViewById(R.id.rv_movies);
        // NOTE: give the recycler default layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // NOTE: let the recycler all viewholders are the same size for optimization
        mRecyclerView.setHasFixedSize(true);
        // NOTE: create adapter for recycler view and give it dummy data
        mAdapter = new MovieGridAdapter(mDummyMovieData);
        // NOTE: set the adapter on the recycler
        mRecyclerView.setAdapter(mAdapter);



    }


}
