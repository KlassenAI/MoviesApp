package com.android.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

import com.android.moviesapp.R;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = findViewById(R.id.poster_movie_activity_item);

        Intent intent = getIntent();
        if (intent != null) {
            ItemMovie movie = intent.getParcelableExtra(ItemMovie.class.getSimpleName());
            setTitle(movie.getTitle());
            Picasso.get().load(Util.REQUEST_IMAGE + movie.getPoster()).fit().centerInside().into(mImageView);
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
