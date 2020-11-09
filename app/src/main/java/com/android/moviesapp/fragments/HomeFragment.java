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
import com.android.moviesapp.adapters.MovieCardItemAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView mCurrentPopularMoviesRecyclerView;
    private MovieCardItemAdapter mCurrentPopularMoviesAdapter;
    private List<ItemMovie> mCurrentPopularMoviesList;

    private RecyclerView mUpcomingMoviesRecyclerView;
    private MovieCardItemAdapter mUpcomingMoviesAdapter;
    private List<ItemMovie> mUpcomingMoviesList;

    private RecyclerView mTopRatedMoviesRecyclerView;
    private MovieCardItemAdapter mTopRatedMoviesAdapter;
    private List<ItemMovie> mTopRatedMoviesList;

    private AppDatabase mAppDatabase = App.getAppDatabase();
    private List<Movie> mMovieList = new ArrayList<>();

    private Calendar mTodayCalendar;
    private String mTodayDate;

    private RequestQueue mRequestQueue;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mCurrentPopularMoviesRecyclerView = rootView.findViewById(R.id.current_popular_movies_recycler_view);
        mUpcomingMoviesRecyclerView = rootView.findViewById(R.id.upcoming_movies_recycler_view);
        mTopRatedMoviesRecyclerView = rootView.findViewById(R.id.top_rated_movies_recycler_view);

        new GettingAllFavoritesMovies().execute();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mTodayCalendar = new GregorianCalendar();
        mTodayDate = dateFormat.format(mTodayCalendar.getTime());

        mRequestQueue = Volley.newRequestQueue(getActivity());

        if (mCurrentPopularMoviesList == null) {
            mCurrentPopularMoviesList = new ArrayList<>();
            getMovies(Util.REQUEST_CURRENT_POPULAR_MOVIES, MovieType.CURRENT_POPULAR);
        }
        mCurrentPopularMoviesAdapter = new MovieCardItemAdapter(getActivity(), mCurrentPopularMoviesList, mAppDatabase, HomeFragment.this);
        mCurrentPopularMoviesRecyclerView.setAdapter(mCurrentPopularMoviesAdapter);
        RecyclerView.LayoutManager mCurrentPopularMoviesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCurrentPopularMoviesRecyclerView.setLayoutManager(mCurrentPopularMoviesLayoutManager);

        if (mUpcomingMoviesList == null) {
            mUpcomingMoviesList = new ArrayList<>();
            getMovies(Util.REQUEST_UPCOMING_MOVIES + mTodayDate, MovieType.UPCOMING);
        }
        mUpcomingMoviesAdapter = new MovieCardItemAdapter(getActivity(), mUpcomingMoviesList, mAppDatabase, HomeFragment.this);
        mUpcomingMoviesRecyclerView.setAdapter(mUpcomingMoviesAdapter);
        RecyclerView.LayoutManager mUpcomingMoviesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mUpcomingMoviesRecyclerView.setLayoutManager(mUpcomingMoviesLayoutManager);

        if (mTopRatedMoviesList == null) {
            mTopRatedMoviesList = new ArrayList<>();
            getMovies(Util.REQUEST_TOP_RATED_MOVIES, MovieType.TOP_RATED);
        }
        mTopRatedMoviesAdapter = new MovieCardItemAdapter(getActivity(), mTopRatedMoviesList, mAppDatabase, HomeFragment.this);
        mTopRatedMoviesRecyclerView.setAdapter(mTopRatedMoviesAdapter);
        RecyclerView.LayoutManager mTopRatedMoviesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTopRatedMoviesRecyclerView.setLayoutManager(mTopRatedMoviesLayoutManager);

        return rootView;
    }

    private void getMovies(String url, final MovieType type) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(Util.JSON_ARRAY_MOVIE_RESULTS);

                    switch (type) {
                        case CURRENT_POPULAR: mCurrentPopularMoviesList.clear();
                            break;
                        case UPCOMING: mUpcomingMoviesList.clear();
                            break;
                        case TOP_RATED: mTopRatedMoviesList.clear();
                            break;
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString(Util.JSON_OBJECT_MOVIE_ID);
                        String title = jsonObject.getString(Util.JSON_OBJECT_MOVIE_TITLE);
                        String date = jsonObject.getString(Util.JSON_OBJECT_MOVIE_DATE);
                        String image = jsonObject.getString(Util.JSON_OBJECT_MOVIE_POSTER);
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

                        switch (type) {
                            case CURRENT_POPULAR:
                                if (mMovieList.contains(movie)) {
                                    mCurrentPopularMoviesList.add(new ItemMovie(movie, true));
                                } else {
                                    mCurrentPopularMoviesList.add(new ItemMovie(movie, false));
                                }
                                break;
                            case UPCOMING:
                                if (mMovieList.contains(movie)) {
                                    mUpcomingMoviesList.add(new ItemMovie(movie, true));
                                } else {
                                    mUpcomingMoviesList.add(new ItemMovie(movie, false));
                                }
                                break;
                            case TOP_RATED:
                                if (mMovieList.contains(movie)) {
                                    mTopRatedMoviesList.add(new ItemMovie(movie, true));
                                } else {
                                    mTopRatedMoviesList.add(new ItemMovie(movie, false));
                                }
                                break;
                        }
                    }

                    switch (type) {
                        case CURRENT_POPULAR: mCurrentPopularMoviesAdapter.notifyDataSetChanged();
                            break;
                        case UPCOMING: mUpcomingMoviesAdapter.notifyDataSetChanged();
                            break;
                        case TOP_RATED: mTopRatedMoviesAdapter.notifyDataSetChanged();
                            break;
                    }

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

    private enum MovieType {
        CURRENT_POPULAR,
        UPCOMING,
        TOP_RATED
    }

    private class GettingAllFavoritesMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mMovieList.clear();
            mMovieList = mAppDatabase.getWordDao().getAllFavoritesMovies();
            if (mCurrentPopularMoviesList != null) {
                for (int i = 0; i < mCurrentPopularMoviesList.size(); i++) {
                    if (mMovieList.contains(mCurrentPopularMoviesList.get(i).getMovie())) {
                        mCurrentPopularMoviesList.get(i).setFavorite(true);
                    } else {
                        mCurrentPopularMoviesList.get(i).setFavorite(false);
                    }
                }
            }
            if (mUpcomingMoviesList != null) {
                for (int i = 0; i < mUpcomingMoviesList.size(); i++) {
                    if (mMovieList.contains(mUpcomingMoviesList.get(i).getMovie())) {
                        mUpcomingMoviesList.get(i).setFavorite(true);
                    } else {
                        mUpcomingMoviesList.get(i).setFavorite(false);
                    }
                }
            }
            if (mTopRatedMoviesList != null) {
                for (int i = 0; i < mTopRatedMoviesList.size(); i++) {
                    if (mMovieList.contains(mTopRatedMoviesList.get(i).getMovie())) {
                        mTopRatedMoviesList.get(i).setFavorite(true);
                    } else {
                        mTopRatedMoviesList.get(i).setFavorite(false);
                    }
                }
            }
            return null;
        }
    }
}
