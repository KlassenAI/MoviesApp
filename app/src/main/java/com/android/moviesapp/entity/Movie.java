package com.android.moviesapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "favorites_movies")
public class Movie implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private String id;

    @ColumnInfo(name = "movie_title")
    private String title;

    @ColumnInfo(name = "movie_poster")
    private String poster;

    @ColumnInfo(name = "movie_date")
    private String date;

    @ColumnInfo(name = "movie_rating")
    private String rating;

    @ColumnInfo(name = "movie_adult")
    private boolean adult;

    @ColumnInfo(name = "movie_genres_ids")
    private String[] genres_ids;

    @ColumnInfo(name = "movie_overview")
    private String overview;

    @ColumnInfo(name = "movie_votes")
    private String votes;

    @ColumnInfo(name = "movie_popularity")
    private String popularity;

    @Ignore
    public Movie() {
    }

    public Movie(String id, String title, String poster, String date, String rating, boolean adult, String[] genres_ids, String overview, String votes, String popularity) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.date = date;
        this.rating = rating;
        this.adult = adult;
        this.genres_ids = genres_ids;
        this.overview = overview;
        this.votes = votes;
        this.popularity = popularity;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        poster = in.readString();
        date = in.readString();
        rating = in.readString();
        adult = in.readByte() != 0;
        genres_ids = in.createStringArray();
        overview = in.readString();
        votes = in.readString();
        popularity = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String[] getGenres_ids() {
        return genres_ids;
    }

    public void setGenres_ids(String[] genres_ids) {
        this.genres_ids = genres_ids;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public Movie getMovie() {
        return new Movie(id, title, poster, date, rating, adult, genres_ids, overview, votes, popularity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(date);
        parcel.writeString(rating);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeStringArray(genres_ids);
        parcel.writeString(overview);
        parcel.writeString(votes);
        parcel.writeString(popularity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return adult == movie.adult &&
                id.equals(movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(poster, movie.poster) &&
                Objects.equals(date, movie.date) &&
                Objects.equals(rating, movie.rating) &&
                Arrays.equals(genres_ids, movie.genres_ids) &&
                Objects.equals(overview, movie.overview) &&
                Objects.equals(votes, movie.votes) &&
                Objects.equals(popularity, movie.popularity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, poster, date, rating, adult, overview, votes, popularity);
        result = 31 * result + Arrays.hashCode(genres_ids);
        return result;
    }
}
