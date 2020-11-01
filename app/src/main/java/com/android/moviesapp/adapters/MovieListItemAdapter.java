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

import androidx.recyclerview.widget.RecyclerView;

import com.android.moviesapp.R;
import com.android.moviesapp.activities.MovieActivity;
import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.entity.Movie;
import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListItemAdapter extends RecyclerView.Adapter<MovieListItemAdapter.MovieListItemViewHolder> {

    private Context mContext;
    private List<ItemMovie> mMovies;
    private AppDatabase mAppDatabase;
    private MovieListItemType mMovieListItemType;

    public MovieListItemAdapter(Context context, List<ItemMovie> movies, AppDatabase appDatabase, MovieListItemType type) {
        mContext = context;
        mMovies = movies;
        mAppDatabase = appDatabase;
        mMovieListItemType = type;
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
        final ItemMovie currentMovie = mMovies.get(position);

        String id = currentMovie.getId();
        final String title = currentMovie.getTitle();
        String posterUrl = currentMovie.getPoster();
        String date = currentMovie.getDate();
        String rating = currentMovie.getRating();
        final boolean favorite = currentMovie.isFavorite();

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

        final Movie movie = new Movie(id, title, posterUrl, date, rating);

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
                        mMovies.get(position).setFavorite(!favorite);
                    }
                });
                break;
            case FAVORITE:
                holder.mBookmarkImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Film " + title + " was deleted", Toast.LENGTH_SHORT).show();
                        new DeleteMovieAsyncTask().execute(movie);
                        mMovies.remove(position);
                    }
                });
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra(ItemMovie.class.getSimpleName(), currentMovie);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
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
