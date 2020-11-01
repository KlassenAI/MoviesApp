package com.android.moviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.moviesapp.R;
import com.android.moviesapp.entity.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieCardItemAdapter extends RecyclerView.Adapter<MovieCardItemAdapter.MovieCardItemViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;

    public MovieCardItemAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        this.mMovies = movies;
    }

    public class MovieCardItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mPosterImageView;
        TextView mTitleTextView;
        TextView mIndexTextView;
        TextView mRatingTextView;

        public MovieCardItemViewHolder(View itemView) {
            super(itemView);

            mPosterImageView = itemView.findViewById(R.id.poster_movie_card_item);
            mTitleTextView = itemView.findViewById(R.id.title_movie_card_item);
            mIndexTextView = itemView.findViewById(R.id.index_movie_card_item);
            mRatingTextView = itemView.findViewById(R.id.rating_movie_card_item);
        }
    }

    @Override
    public MovieCardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card_item,
                parent, false);
        return new MovieCardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieCardItemViewHolder holder, int position) {
        final Movie currentMovie = mMovies.get(position);

        String title = currentMovie.getTitle();
        String posterUrl = currentMovie.getPoster();
        String rating = currentMovie.getRating();

        Picasso.get().load("https://image.tmdb.org/t/p/original" + posterUrl).fit().centerInside().into(holder.mPosterImageView);
        holder.mTitleTextView.setText(title);
        holder.mIndexTextView.setText(String.valueOf(position + 1));
        holder.mRatingTextView.setText(rating);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
