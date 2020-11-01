package com.android.moviesapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviesapp.R;
import com.android.moviesapp.adapters.MovieCardItemAdapter;
import com.android.moviesapp.entity.Movie;
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
public class HomeFragment extends Fragment {

    private RecyclerView mCurrentPopularMoviesRecyclerView;
    private MovieCardItemAdapter mCurrentPopularMoviesAdapter;
    private List<Movie> mListCurrentPopularMovies;

    private RecyclerView mUpcomingMoviesRecyclerView;
    private MovieCardItemAdapter mUpcomingMoviesAdapter;
    private List<Movie> mListUpcomingMovies;

    private RecyclerView mTopRatedMoviesRecyclerView;
    private MovieCardItemAdapter mTopRatedMoviesAdapter;
    private List<Movie> mListTopRatedMovies;

    private RequestQueue mRequestQueue;

    public HomeFragment() {
        // Required empty public constructor
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

        mRequestQueue = Volley.newRequestQueue(getActivity());

        if (mListCurrentPopularMovies == null) {
            mListCurrentPopularMovies = new ArrayList<>();
            getCurrentPopularMovies();
        }

        mCurrentPopularMoviesAdapter = new MovieCardItemAdapter(getActivity(), mListCurrentPopularMovies);
        mCurrentPopularMoviesRecyclerView.setAdapter(mCurrentPopularMoviesAdapter);

        RecyclerView.LayoutManager mCurrentPopularMoviesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCurrentPopularMoviesRecyclerView.setLayoutManager(mCurrentPopularMoviesLayoutManager);

        if (mListUpcomingMovies == null) {
            mListUpcomingMovies = new ArrayList<>();
            getUpcomingMovies();
        }

        mUpcomingMoviesAdapter = new MovieCardItemAdapter(getActivity(), mListUpcomingMovies);
        mUpcomingMoviesRecyclerView.setAdapter(mUpcomingMoviesAdapter);

        RecyclerView.LayoutManager mUpcomingMoviesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mUpcomingMoviesRecyclerView.setLayoutManager(mUpcomingMoviesLayoutManager);

        if (mListTopRatedMovies == null) {
            mListTopRatedMovies = new ArrayList<>();
            getTopRatedMovies();
        }

        mTopRatedMoviesAdapter = new MovieCardItemAdapter(getActivity(), mListTopRatedMovies);
        mTopRatedMoviesRecyclerView.setAdapter(mTopRatedMoviesAdapter);

        RecyclerView.LayoutManager mTopRatedMoviesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTopRatedMoviesRecyclerView.setLayoutManager(mTopRatedMoviesLayoutManager);

        return rootView;
    }

    private void getCurrentPopularMovies() {
        String url = Util.REQUEST_CURRENT_POPULAR_MOVIES;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(Util.JSON_ARRAY_MOVIE_RESULTS);

                    mListCurrentPopularMovies.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString(Util.JSON_OBJECT_MOVIE_ID);
                        String title = jsonObject.getString(Util.JSON_OBJECT_MOVIE_TITLE);
                        String image = jsonObject.getString(Util.JSON_OBJECT_MOVIE_POSTER);
                        String date = jsonObject.getString(Util.JSON_OBJECT_MOVIE_DATE);
                        String rating = jsonObject.getString(Util.JSON_OBJECT_MOVIE_RATING);

                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setPoster(image);
                        movie.setDate(date);
                        movie.setRating(rating);

                        mListCurrentPopularMovies.add(movie);
                    }

                    mCurrentPopularMoviesAdapter.notifyDataSetChanged();

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

    private void getUpcomingMovies() {
        String url = Util.REQUEST_UPCOMING_MOVIES;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(Util.JSON_ARRAY_MOVIE_RESULTS);

                    mListUpcomingMovies.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString(Util.JSON_OBJECT_MOVIE_ID);
                        String title = jsonObject.getString(Util.JSON_OBJECT_MOVIE_TITLE);
                        String image = jsonObject.getString(Util.JSON_OBJECT_MOVIE_POSTER);
                        String date = jsonObject.getString(Util.JSON_OBJECT_MOVIE_DATE);
                        String rating = jsonObject.getString(Util.JSON_OBJECT_MOVIE_RATING);

                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setPoster(image);
                        movie.setDate(date);
                        movie.setRating(rating);

                        mListUpcomingMovies.add(movie);
                    }

                    mUpcomingMoviesAdapter.notifyDataSetChanged();

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

    private void getTopRatedMovies() {
        String url = Util.REQUEST_TOP_RATED_MOVIES;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(Util.JSON_ARRAY_MOVIE_RESULTS);

                    mListTopRatedMovies.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString(Util.JSON_OBJECT_MOVIE_ID);
                        String title = jsonObject.getString(Util.JSON_OBJECT_MOVIE_TITLE);
                        String image = jsonObject.getString(Util.JSON_OBJECT_MOVIE_POSTER);
                        String date = jsonObject.getString(Util.JSON_OBJECT_MOVIE_DATE);
                        String rating = jsonObject.getString(Util.JSON_OBJECT_MOVIE_RATING);

                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setPoster(image);
                        movie.setDate(date);
                        movie.setRating(rating);

                        mListTopRatedMovies.add(movie);
                    }

                    mTopRatedMoviesAdapter.notifyDataSetChanged();

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
