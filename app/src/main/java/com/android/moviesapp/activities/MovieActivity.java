package com.android.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.moviesapp.R;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    ImageView mPosterImageView;
    TextView mOverviewTextView;
    RecyclerView mGenresRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPosterImageView = findViewById(R.id.movie_poster_activity_item);
        mOverviewTextView = findViewById(R.id.movie_overview_activity_item);

        Intent intent = getIntent();
        if (intent != null) {
            ItemMovie movie = intent.getParcelableExtra(ItemMovie.class.getSimpleName());
            setTitle(movie.getTitle());
            if (movie.getPoster() == null) {
                mPosterImageView.setImageResource(R.drawable.no_image);
            } else {
                Picasso.get().load(Util.REQUEST_IMAGE + movie.getPoster()).fit().centerInside().into(mPosterImageView);
            }
            mOverviewTextView.setText(movie.getOverview());
            String[] genres = movie.getGenresIds().split(",");

        }

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
