package com.android.moviesapp.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviesapp.App;
import com.android.moviesapp.R;
import com.android.moviesapp.adapters.MovieListItemAdapter;
import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.entity.Movie;
import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MovieListItemAdapter mItemMovieListAdapter;
    private List<ItemMovie> mItemMovieList;
    private List<Movie> mMovieList;

    private AppDatabase mMovieAppDatabase = App.getAppDatabase();

    public FavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        mRecyclerView = rootView.findViewById(R.id.favorites_movies_recycler_view);

        if (mItemMovieList == null) {
            mItemMovieList = new ArrayList<>();
        }
        mMovieList = new ArrayList<>();
        new GetAllWordsAsyncTask().execute();
        mItemMovieListAdapter = new MovieListItemAdapter(getActivity(), mItemMovieList, mMovieAppDatabase,
                MovieListItemAdapter.MovieListItemType.FAVORITE, FavoritesFragment.this);
        mRecyclerView.setAdapter(mItemMovieListAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.FAVORITES_FRAGMENT_REQUEST_CODE & resultCode == RESULT_OK) {
            boolean favorite = data.getBooleanExtra("Favorite", false);
            if (!favorite) {
                mItemMovieList.remove(data.getIntExtra("Index", 0));
                mItemMovieListAdapter.notifyDataSetChanged();
            }
        }
    }

    private class GetAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mItemMovieList.clear();
            mMovieList.clear();
            mMovieList.addAll(mMovieAppDatabase.getWordDao().getAllFavoritesMovies());
            for (int i = 0; i < mMovieList.size(); i++) {
                mItemMovieList.add(new ItemMovie(mMovieList.get(i), true));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mItemMovieListAdapter.notifyDataSetChanged();
        }
    }
}
