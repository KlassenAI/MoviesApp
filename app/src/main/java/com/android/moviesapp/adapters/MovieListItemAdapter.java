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
import com.android.moviesapp.activities.MainActivity;
import com.android.moviesapp.activities.MovieActivity;
import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.entity.Movie;
import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListItemAdapter extends RecyclerView.Adapter<MovieListItemAdapter.MovieListItemViewHolder> {

    private Context mContext;
    private List<ItemMovie> mItemMovies;
    private AppDatabase mFavoritesAppDatabase;
    private MovieListItemType mMovieListItemType;
    private Fragment mFragment;

    public MovieListItemAdapter(Context context, List<ItemMovie> itemMovies, AppDatabase favoritesAppDatabase, MovieListItemType type, Fragment fragment) {
        mContext = context;
        mItemMovies = itemMovies;
        mFavoritesAppDatabase = favoritesAppDatabase;
        mMovieListItemType = type;
        mFragment = fragment;
    }

    public class MovieListItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mPosterImageView;
        TextView mTitleTextView;
        TextView mDateTextView;
        TextView mRatingTextView;
        ImageButton mBookmarkImageButton;

        public MovieListItemViewHolder(View itemView) {
            super(itemView);

            mPosterImageView = itemView.findViewById(R.id.poster_movie_list_item);
            mTitleTextView = itemView.findViewById(R.id.title_movie_list_item);
            mDateTextView = itemView.findViewById(R.id.date_movie_list_item);
            mRatingTextView = itemView.findViewById(R.id.rating_movie_list_item);
            mBookmarkImageButton = itemView.findViewById(R.id.bookmark_movie_list_item);
        }
    }

    public enum MovieListItemType {
        SEARCH,
        FAVORITE
    }

    @Override
    public MovieListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,
                parent, false);
        return new MovieListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieListItemViewHolder holder, final int position) {
        final ItemMovie currentItemMovie = mItemMovies.get(position);

        final String title = currentItemMovie.getTitle();
        String posterUrl = currentItemMovie.getPoster();
        String date = currentItemMovie.getDate();
        String rating = currentItemMovie.getRating();
        final boolean favorite = currentItemMovie.isFavorite();

        if (posterUrl == null) {
            holder.mPosterImageView.setImageResource(R.drawable.no_image);
        } else {
            Picasso.get().load(Util.REQUEST_IMAGE + posterUrl).fit().centerInside().into(holder.mPosterImageView);
        }
        holder.mTitleTextView.setText(title);
        holder.mDateTextView.setText(date);
        holder.mRatingTextView.setText(rating);
        if (favorite) {
            holder.mBookmarkImageButton.setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            holder.mBookmarkImageButton.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("ItemMovie", currentItemMovie);
                intent.putExtra("Index", position);
                switch (mMovieListItemType) {
                    case SEARCH:
                        mFragment.startActivityForResult(intent, Util.SEARCH_FRAGMENT_REQUEST_CODE);
                        break;
                    case FAVORITE:
                        mFragment.startActivityForResult(intent, Util.FAVORITES_FRAGMENT_REQUEST_CODE);
                        break;
                }

            }
        });

        final Movie movie = currentItemMovie.getMovie();

        switch (mMovieListItemType) {
            case SEARCH:
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
                break;
            case FAVORITE:
                holder.mBookmarkImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Film " + title + " was deleted", Toast.LENGTH_SHORT).show();
                        new DeleteMovieAsyncTask().execute(movie);
                        mItemMovies.remove(position);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemMovies.size();
    }

    private class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            mFavoritesAppDatabase.getWordDao().insertMovie(movies[0]);
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
            mFavoritesAppDatabase.getWordDao().deleteMovie(movies[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }
}
