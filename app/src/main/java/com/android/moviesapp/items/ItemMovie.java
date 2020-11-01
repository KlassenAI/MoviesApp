package com.android.moviesapp.items;

import android.os.Parcel;

import com.android.moviesapp.entity.Movie;

import java.util.Arrays;
import java.util.Objects;

public class ItemMovie extends Movie {

    private String id;
    private String title;
    private String poster;
    private String date;
    private String rating;
    private boolean adult;
    private String[] genres_ids;
    private String overview;
    private String votes;
    private String popularity;
    private boolean favorite;

    public ItemMovie(String id, String title, String poster, String date, String rating, boolean adult, String[] genres_ids, String overview, String votes, String popularity, boolean favorite) {
        super(id, title, poster, date, rating, adult, genres_ids, overview, votes, popularity);
        this.favorite = favorite;
    }

    private ItemMovie(Parcel in) {
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
        favorite = in.readByte() != 0;
    }

    public static final Creator<ItemMovie> CREATOR = new Creator<ItemMovie>() {
        @Override
        public ItemMovie createFromParcel(Parcel in) {
            return new ItemMovie(in);
        }

        @Override
        public ItemMovie[] newArray(int size) {
            return new ItemMovie[size];
        }
    };

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ItemMovie movie = (ItemMovie) o;
        return adult == movie.adult &&
                favorite == movie.favorite &&
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
        int result = Objects.hash(super.hashCode(), id, title, poster, date, rating, adult, overview, votes, popularity, favorite);
        result = 31 * result + Arrays.hashCode(genres_ids);
        return result;
    }
}

