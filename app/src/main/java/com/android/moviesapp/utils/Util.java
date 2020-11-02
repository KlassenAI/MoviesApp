package com.android.moviesapp.utils;

public final class Util {
    public static final String DB_FAVORITES_MOVIES = "favorites_movies";

    private static final String API_KEY = "23400594aacd1f278d52fb2763517318";

    public static final String JSON_ARRAY_MOVIE_RESULTS = "results";
    public static final String JSON_OBJECT_MOVIE_ID = "id";
    public static final String JSON_OBJECT_MOVIE_TITLE = "title";
    public static final String JSON_OBJECT_MOVIE_POSTER = "poster_path";
    public static final String JSON_OBJECT_MOVIE_DATE = "release_date";
    public static final String JSON_OBJECT_MOVIE_RATING = "vote_average";
    public static final String JSON_OBJECT_MOVIE_ADULT = "adult";
    public static final String JSON_ARRAY_MOVIE_GENRE_IDS = "genre_ids";
    public static final String JSON_OBJECT_MOVIE_OVERVIEW = "overview";
    public static final String JSON_OBJECT_MOVIE_VOTES = "vote_count";
    public static final String JSON_OBJECT_MOVIE_POPULARITY = "popularity";

    public static final String REQUEST_MOVIE_SEARCH =
            "https://api.themoviedb.org/3/search/movie?api_key=" + Util.API_KEY + "&query=";

    public static final String REQUEST_IMAGE = "https://image.tmdb.org/t/p/original";
    public static final String REQUEST_CURRENT_POPULAR_MOVIES =
            "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    public static final String REQUEST_UPCOMING_MOVIES =
            "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY;
    public static final String REQUEST_TOP_RATED_MOVIES =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
}
