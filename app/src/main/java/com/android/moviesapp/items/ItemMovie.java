package com.android.moviesapp.items;

import android.os.Parcel;

import com.android.moviesapp.entity.Movie;

import java.util.Objects;

public class ItemMovie extends Movie {

    private String id;
    private String title;
    private String image;
    private String date;
    private String rating;
    private boolean favorite;

    public ItemMovie(String id, String title, String image, String date, String rating, boolean favorite) {
        super(id, title, image, date, rating);
        this.favorite = favorite;
    }

    public ItemMovie(Movie movie, boolean favorite) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.image = movie.getImage();
        this.date = movie.getDate();
        this.rating = movie.getRating();
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(date);
        parcel.writeString(rating);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ItemMovie itemMovie = (ItemMovie) o;
        return favorite == itemMovie.favorite &&
                Objects.equals(id, itemMovie.id) &&
                Objects.equals(title, itemMovie.title) &&
                Objects.equals(image, itemMovie.image) &&
                Objects.equals(date, itemMovie.date) &&
                Objects.equals(rating, itemMovie.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, image, date, rating, favorite);
    }

    public ItemMovie(Parcel in) {
        id = in.readString();
        title = in.readString();
        image = in.readString();
        date = in.readString();
        rating = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new ItemMovie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new ItemMovie[size];
        }
    };
}

