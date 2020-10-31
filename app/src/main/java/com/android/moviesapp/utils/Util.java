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

    public static final String REQUEST_MOVIE_SEARCH =
            "https://api.themoviedb.org/3/search/movie?api_key=" + Util.API_KEY + "&query=";

    public static final String REQUEST_IMAGE = "https://image.tmdb.org/t/p/original";
    public static final String REQUEST_CURRENT_POPULAR_MOVIES =
            "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    public static final String REQUEST_UPCOMING_MOVIES =
            "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY;
    public static final String REQUEST_TOP_RATED_MOVIES =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;

    public static final String JSON_OBJECT_GENRE_ACTION = "28";
    public static final String JSON_OBJECT_GENRE_ADVENTURE = "12";
    public static final String JSON_OBJECT_GENRE_ANIMATION = "16";
    public static final String JSON_OBJECT_GENRE_COMEDY = "35";
    public static final String JSON_OBJECT_GENRE_CRIME = "80";
    public static final String JSON_OBJECT_GENRE_DOCUMENTARY = "99";
    public static final String JSON_OBJECT_GENRE_DRAMA = "18";
    public static final String JSON_OBJECT_GENRE_FAMILY = "10751";
    public static final String JSON_OBJECT_GENRE_FANTASY = "14";
    public static final String JSON_OBJECT_GENRE_HISTORY = "36";
    public static final String JSON_OBJECT_GENRE_HORROR = "27";
    public static final String JSON_OBJECT_GENRE_MUSIC = "10402";
    public static final String JSON_OBJECT_GENRE_MYSTERY = "9648";
    public static final String JSON_OBJECT_GENRE_ROMANCE = "10749";
    public static final String JSON_OBJECT_GENRE_SCIENCE_FICTION = "878";
    public static final String JSON_OBJECT_GENRE_TV_MOVIE = "10770";
    public static final String JSON_OBJECT_GENRE_THRILLER = "53";
    public static final String JSON_OBJECT_GENRE_WAR = "10752";
    public static final String JSON_OBJECT_GENRE_WESTERN = "37";
}
