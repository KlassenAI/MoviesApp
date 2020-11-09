package com.android.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviesapp.R;
import com.android.moviesapp.activities.MovieActivity;
import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.entity.Movie;
import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class MovieCardItemAdapter extends RecyclerView.Adapter<MovieCardItemAdapter.MovieCardItemViewHolder> {

    private Context mContext;
    private List<ItemMovie> mItemMovies;
    private AppDatabase mAppDatabase;
    private Fragment mFragment;

    public MovieCardItemAdapter(Context context, List<ItemMovie> itemMovies, AppDatabase appDatabase, Fragment fragment) {
        mContext = context;
        mItemMovies = itemMovies;
        mAppDatabase = appDatabase;
        mFragment = fragment;
    }

    public class MovieCardItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mPosterImageView;
        TextView mTitleTextView;
        TextView mIndexTextView;
        TextView mRatingTextView;
        ImageView mDetailMarkImageView;
        ImageButton mBookmarkImageButton;

        public MovieCardItemViewHolder(View itemView) {
            super(itemView);

            mPosterImageView = itemView.findViewById(R.id.poster_movie_card_item);
            mTitleTextView = itemView.findViewById(R.id.title_movie_card_item);
            mIndexTextView = itemView.findViewById(R.id.index_movie_card_item);
            mRatingTextView = itemView.findViewById(R.id.rating_movie_card_item);
            mDetailMarkImageView = itemView.findViewById(R.id.detail_mark_movie_card_item);
            mBookmarkImageButton = itemView.findViewById(R.id.bookmark_movie_card_item);
        }
    }

    @Override
    public MovieCardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card_item,
                parent, false);
        return new MovieCardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieCardItemViewHolder holder, final int position) {
        final ItemMovie currentItemMovie = mItemMovies.get(position);

        final String title = currentItemMovie.getTitle();
        String posterUrl = currentItemMovie.getPoster();
        String rating = currentItemMovie.getRating();
        final boolean favorite = currentItemMovie.isFavorite();

        holder.mIndexTextView.setText(String.format(Locale.getDefault(), "#%d", position + 1));
        if (posterUrl == null) {
            holder.mPosterImageView.setImageResource(R.drawable.no_image);
        } else {
            Picasso.get().load(Util.REQUEST_IMAGE + posterUrl).fit().centerInside().into(holder.mPosterImageView);
        }
        holder.mTitleTextView.setText(title);
        holder.mRatingTextView.setText(rating);
        if (favorite) {
            holder.mBookmarkImageButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
            holder.mDetailMarkImageView.setImageResource(R.drawable.ic_done_black_24dp);
        } else {
            holder.mBookmarkImageButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            holder.mDetailMarkImageView.setImageResource(R.drawable.ic_add_black_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("ItemMovie", currentItemMovie);
                intent.putExtra("Index", position);
                mFragment.startActivityForResult(intent, Util.SEARCH_FRAGMENT_REQUEST_CODE);
            }
        });

        final Movie movie = currentItemMovie.getMovie();

        holder.mBookmarkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite) {
                    Toast.makeText(mContext, "Film " + title + " was deleted", Toast.LENGTH_SHORT).show();
                    new DeleteMovieAsyncTask().execute(movie);
                } else {
                    Toast.makeText(mContext, "Film " + title + " was added", Toast.LENGTH_SHORT).show();
                    new InsertMovieAsyncTask().execute(movie);
                }
                mItemMovies.get(position).setFavorite(!favorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemMovies.size();
    }

    private class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            mAppDatabase.getWordDao().insertMovie(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }

    private class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            mAppDatabase.getWordDao().deleteMovie(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }
}
