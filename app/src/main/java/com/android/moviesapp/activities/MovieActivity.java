package com.android.moviesapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.moviesapp.App;
import com.android.moviesapp.adapters.GenreMovieAdapter;
import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.entity.Movie;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    ImageView mPosterImageView;
    TextView mTitleTextView;
    TextView mOverviewTextView;
    TextView mRatingTextView;
    TextView mPopularityTextView;
    TextView mVotesTextView;

    RecyclerView mGenresRecyclerView;
    GenreMovieAdapter mGenreMovieAdapter;
    FloatingActionButton mFloatingActionButton;

    List<String> mGenres;
    ItemMovie mItemMovie;
    boolean mChangeFavoriteFlag;
    int position;

    private AppDatabase mAppDatabase = App.getAppDatabase();

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

        mChangeFavoriteFlag = false;

        RecyclerView.LayoutManager mGenresLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mGenresRecyclerView.setLayoutManager(mGenresLayoutManager);

        Intent intent = getIntent();
        position = intent.getIntExtra("Index", 0);
        mItemMovie = intent.getParcelableExtra("ItemMovie");
        setTitle(mItemMovie.getTitle());
        if (mItemMovie.getPoster() == null) {
            mPosterImageView.setImageResource(R.drawable.no_image);
        } else {
            Picasso.get().load(Util.REQUEST_IMAGE + mItemMovie.getPoster()).fit().centerInside().into(mPosterImageView);
        }
        mOverviewTextView.setText(mItemMovie.getOverview());
        if (mItemMovie.getGenresIds() == null) {
            mGenres = new ArrayList<>();
        } else {
            mGenres = Arrays.asList(mItemMovie.getGenresIds().split(","));
            if (mGenres.size() == 0) {
                mGenresRecyclerView.setVisibility(View.GONE);
            }
        }
        mGenreMovieAdapter = new GenreMovieAdapter(this, mGenres);
        mGenresRecyclerView.setAdapter(mGenreMovieAdapter);
        mGenreMovieAdapter.notifyDataSetChanged();
        mRatingTextView.setText(mItemMovie.getRating());
        mPopularityTextView.setText(mItemMovie.getPopularity());
        mVotesTextView.setText(mItemMovie.getVotes());

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.bookmark_movie_activity_item);
        if (mItemMovie.isFavorite()) {
            mFloatingActionButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemMovie.isFavorite()) {
                    mFloatingActionButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    mItemMovie.setFavorite(false);
                    new DeleteMovieAsyncTask().execute(mItemMovie.getMovie());
                } else {
                    mFloatingActionButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    mItemMovie.setFavorite(true);
                    new InsertMovieAsyncTask().execute(mItemMovie.getMovie());
                }
                mChangeFavoriteFlag = !mChangeFavoriteFlag; // пользователь может добавить и убрать фильм
            }
        });
    }

    private class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            mAppDatabase.getWordDao().insertMovie(movies[0]);
            return null;
        }
    }

    private class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            mAppDatabase.getWordDao().deleteMovie(movies[0]);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent data = new Intent();
                data.putExtra("Favorite", mItemMovie.isFavorite());
                data.putExtra("Index", position);
                setResult(RESULT_OK, data);
                finish();
            }
        }.execute();
    }
}
