package com.android.moviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.moviesapp.R;

import java.util.List;

public class GenreMovieAdapter extends RecyclerView.Adapter<GenreMovieAdapter.GenreMovieViewHolder> {

    private Context mContext;
    private List<String> mGenres;

    public GenreMovieAdapter(Context context, List<String> genres) {
        this.mContext = context;
        this.mGenres = genres;
    }

    public class GenreMovieViewHolder extends RecyclerView.ViewHolder {

        TextView mGenreTextView;

        public GenreMovieViewHolder(View itemView) {
            super(itemView);

            mGenreTextView = itemView.findViewById(R.id.genre_movie_text_view);
        }
    }

    @Override
    public GenreMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.genre_movie,
                parent, false);
        return new GenreMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreMovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mGenres.size();
    }
}
