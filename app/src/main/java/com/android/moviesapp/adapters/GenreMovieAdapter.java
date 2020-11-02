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
        View view = LayoutInflater.from(mContext).inflate(R.layout.genre_item,
                parent, false);
        return new GenreMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreMovieViewHolder holder, int position) {
        String genre = getGenre(mGenres.get(position));

    }

    @Override
    public int getItemCount() {
        return mGenres.size();
    }

    public String getGenre(String id) {
        switch (id) {
            case "12": return "Adventure";
            case "14": return "Fantasy";
            case "16": return "Animation";
            case "18": return "Drama";
            case "27": return "Horror";
            case "28": return "Action";
            case "35": return "Comedy";
            case "36": return "History";
            case "37": return "Western";
            case "53": return "Thriller";
            case "80": return "Crime";
            case "99": return "Documentary";
            case "878": return "Science Fiction";
            case "9648": return "Mystery";
            case "10402": return "Music";
            case "10749": return "Romance";
            case "10751": return "Family";
            case "10752": return "War";
            case "10770": return "TV Movie";
            default: return "No name";
        }
    }
}
