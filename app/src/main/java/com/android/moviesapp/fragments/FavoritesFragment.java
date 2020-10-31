package com.android.moviesapp.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

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
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MovieListItemAdapter mMovieListItemAdapter;
    private List<ItemMovie> mItemMovieList;
    private List<Movie> mMovieList;
    private RequestQueue mRequestQueue;

    private AppDatabase mMovieAppDatabase = App.getAppDatabase();

    public FavoritesFragment() {
        // Required empty public constructor
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

        if (mItemMovieList == null) mItemMovieList = new ArrayList<>();
        mMovieList = new ArrayList<>();
        mMovieListItemAdapter = new MovieListItemAdapter(getActivity(), mItemMovieList, mMovieAppDatabase,
                MovieListItemAdapter.MovieListItemType.FAVORITE);
        mRecyclerView.setAdapter(mMovieListItemAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRequestQueue = Volley.newRequestQueue(getActivity());

        setHasOptionsMenu(true);

        new GetAllWordsAsyncTask().execute();

        return rootView;
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
            mMovieListItemAdapter.notifyDataSetChanged();
        }
    }
}
