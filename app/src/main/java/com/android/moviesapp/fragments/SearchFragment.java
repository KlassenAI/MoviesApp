package com.android.moviesapp.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviesapp.App;
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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private MovieListItemAdapter mMovieListItemAdapter;
    private List<ItemMovie> mItemMovieList = new ArrayList<>();
    private RequestQueue mRequestQueue;

    private AppDatabase mAppDatabase = App.getAppDatabase();
    private List<Movie> mMovieList = new ArrayList<>();

    public SearchFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.SEARCH_FRAGMENT_REQUEST_CODE & resultCode == RESULT_OK) {
            boolean favorite = data.getBooleanExtra("Favorite", false);
            int index = data.getIntExtra("Index", 0);
            mItemMovieList.get(index).setFavorite(favorite);
            mMovieListItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.search_movies_recycler_view);

        new GettingAllFavoritesMovies().execute();

        mMovieListItemAdapter = new MovieListItemAdapter(getActivity(), mItemMovieList, mAppDatabase,
                MovieListItemAdapter.MovieListItemType.SEARCH, SearchFragment.this);
        recyclerView.setAdapter(mMovieListItemAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));

        recyclerView.addItemDecoration(dividerItemDecoration);

        mRequestQueue = Volley.newRequestQueue(getActivity());

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) item.getActionView();
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

    private void getMovies(final String request) {
        String url = Util.REQUEST_MOVIE_SEARCH + request;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayMovieResults = response.getJSONArray(Util.JSON_ARRAY_MOVIE_RESULTS);

                    mItemMovieList.clear();

                    for (int i = 0; i < jsonArrayMovieResults.length(); i++) {

                        JSONObject jsonObject = jsonArrayMovieResults.getJSONObject(i);

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

                        if (mMovieList.contains(movie)) {
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

    private class GettingAllFavoritesMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mMovieList = mAppDatabase.getWordDao().getAllFavoritesMovies();
            if (mItemMovieList != null) {
                for (int i = 0; i < mItemMovieList.size(); i++) {
                    if (mMovieList.contains(mItemMovieList.get(i).getMovie())) {
                        mItemMovieList.get(i).setFavorite(true);
                    } else {
                        mItemMovieList.get(i).setFavorite(false);
                    }
                }
            }
            return null;
        }

    }
}
