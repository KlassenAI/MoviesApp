package com.android.moviesapp.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviesapp.R;
import com.android.moviesapp.adapters.MovieListItemAdapter;
import com.android.moviesapp.db.AppDatabase;
import com.android.moviesapp.entity.Movie;
import com.android.moviesapp.items.ItemMovie;
import com.android.moviesapp.utils.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MovieListItemAdapter mMovieListItemAdapter;
    private List<ItemMovie> mItemMovieList;
    private List<Movie> mMovieListFromDB;
    private RequestQueue mRequestQueue;
    private AppDatabase mAppDatabase;

    public SearchFragment(AppDatabase appDatabase) {
        // Required empty public constructor
        mAppDatabase = appDatabase;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mRecyclerView = rootView.findViewById(R.id.search_movies_recycler_view);

        if (mItemMovieList == null) mItemMovieList = new ArrayList<>();

        mMovieListFromDB = new ArrayList<>();
        new GettingAllFavoritesMovies().execute();

        mMovieListItemAdapter = new MovieListItemAdapter(getActivity(), mItemMovieList, mAppDatabase,
                MovieListItemAdapter.MovieListItemType.SEARCH);
        mRecyclerView.setAdapter(mMovieListItemAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRequestQueue = Volley.newRequestQueue(getActivity());

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getMovies(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private class GettingAllFavoritesMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mMovieListFromDB = mAppDatabase.getWordDao().getAllFavoritesMovies();
            return null;
        }
    }

    private void getMovies(String request) {
        String url = Util.REQUEST_MOVIE_SEARCH + request;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(Util.JSON_ARRAY_MOVIE_RESULTS);

                    mItemMovieList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString(Util.JSON_OBJECT_MOVIE_ID);
                        String title = jsonObject.getString(Util.JSON_OBJECT_MOVIE_TITLE);
                        String image = jsonObject.getString(Util.JSON_OBJECT_MOVIE_POSTER);
                        String date = jsonObject.getString(Util.JSON_OBJECT_MOVIE_DATE);
                        String rating = jsonObject.getString(Util.JSON_OBJECT_MOVIE_RATING);
                        boolean adult = jsonObject.getBoolean(Util.JSON_OBJECT_MOVIE_ADULT);

                        JSONArray jsonArrayGenresIds = jsonObject.getJSONArray(Util.JSON_ARRAY_MOVIE_GENRE_IDS);
                        StringBuilder builder = new StringBuilder();
                        for (int j = 0; j < jsonArrayGenresIds.length(); j++) {
                            builder.append(jsonArrayGenresIds.getString(j));
                            if (j != jsonArrayGenresIds.length() - 1) builder.append(",");
                        }
                        String genresIds = builder.toString();
                        String overview = jsonObject.getString(Util.JSON_OBJECT_MOVIE_OVERVIEW);
                        String votes = jsonObject.getString(Util.JSON_OBJECT_MOVIE_VOTES);
                        String popularity = jsonObject.getString(Util.JSON_OBJECT_MOVIE_POPULARITY);
                        Movie movie = new Movie(id, title, image, date, rating, adult, genresIds, overview, votes, popularity);

                        if (mMovieListFromDB.contains(movie)) {
                            mItemMovieList.add(new ItemMovie(movie, true));
                        } else {
                            mItemMovieList.add(new ItemMovie(movie, false));
                        }
                    }

                    mMovieListItemAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }
}
