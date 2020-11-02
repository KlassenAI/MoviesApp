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
    private String genresIds;
    private String overview;
    private String votes;
    private String popularity;
    private boolean favorite;

    public ItemMovie(Movie movie, boolean favorite) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.poster = movie.getPoster();
        this.date = movie.getDate();
        this.rating = movie.getRating();
        this.adult = movie.isAdult();
        this.genresIds = movie.getGenresIds();
        this.overview = movie.getOverview();
        this.votes = movie.getVotes();
        this.popularity = movie.getPopularity();
        this.favorite = favorite;
    }

    private ItemMovie(Parcel in) {
        id = in.readString();
        title = in.readString();
        poster = in.readString();
        date = in.readString();
        rating = in.readString();
        adult = in.readByte() != 0;
        genresIds = in.readString();
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPoster() {
        return poster;
    }

    @Override
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getRating() {
        return rating;
    }

    @Override
    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean isAdult() {
        return adult;
    }

    @Override
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Override
    public String getGenresIds() {
        return genresIds;
    }

    @Override
    public void setGenresIds(String genresIds) {
        this.genresIds = genresIds;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String getVotes() {
        return votes;
    }

    @Override
    public void setVotes(String votes) {
        this.votes = votes;
    }

    @Override
    public String getPopularity() {
        return popularity;
    }

    @Override
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Movie getMovie() {
        return new Movie(id, title, poster, date, rating, adult, genresIds, overview, votes, popularity);
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
        parcel.writeString(genresIds);
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
                Objects.equals(genresIds, movie.genresIds) &&
                Objects.equals(overview, movie.overview) &&
                Objects.equals(votes, movie.votes) &&
                Objects.equals(popularity, movie.popularity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, poster, date, rating, adult, genresIds, overview, votes, popularity, favorite);
    }

    @Override
    public String toString() {
        return "ItemMovie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", date='" + date + '\'' +
                ", rating='" + rating + '\'' +
                ", adult=" + adult +
                ", genresIds='" + genresIds + '\'' +
                ", overview='" + overview + '\'' +
                ", votes='" + votes + '\'' +
                ", popularity='" + popularity + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}

