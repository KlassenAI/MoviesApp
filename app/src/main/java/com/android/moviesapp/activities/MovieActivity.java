package com.android.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.android.moviesapp.adapters.GenreMovieAdapter;
import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    ImageView mPosterImageView;
    TextView mOverviewTextView;
    TextView mRatingTextView;
    TextView mPopularityTextView;
    TextView mVotesTextView;

    RecyclerView mGenresRecyclerView;
    GenreMovieAdapter mGenreMovieAdapter;

    List<String> mGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPosterImageView = findViewById(R.id.movie_poster_activity_item);
        mOverviewTextView = findViewById(R.id.movie_overview_activity_item);
        mGenresRecyclerView = findViewById(R.id.movie_genres_activity_item);
        mRatingTextView = findViewById(R.id.movie_rating_activity_item);
        mPopularityTextView = findViewById(R.id.movie_popularity_activity_item);
        mVotesTextView = findViewById(R.id.movie_votes_activity_item);



        RecyclerView.LayoutManager mGenresLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mGenresRecyclerView.setLayoutManager(mGenresLayoutManager);

        Intent intent = getIntent();
        ItemMovie movie = intent.getParcelableExtra("ItemMovie");
        Toast.makeText(this, movie.toString(), Toast.LENGTH_SHORT).show();
        setTitle(movie.getTitle());
        if (movie.getPoster() == null) {
            mPosterImageView.setImageResource(R.drawable.no_image);
        } else {
            Picasso.get().load(Util.REQUEST_IMAGE + movie.getPoster()).fit().centerInside().into(mPosterImageView);
        }
        mOverviewTextView.setText(movie.getOverview());
        if (movie.getGenresIds() == null) {
            mGenres = new ArrayList<>();
        } else {
            mGenres = Arrays.asList(movie.getGenresIds().split(","));
            Toast.makeText(this, mGenres.toString(), Toast.LENGTH_SHORT).show();
        }
        mGenreMovieAdapter = new GenreMovieAdapter(this, mGenres);
        mGenresRecyclerView.setAdapter(mGenreMovieAdapter);
        mGenreMovieAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
