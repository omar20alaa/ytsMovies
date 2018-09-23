package com.yts_movies.ytsmovies.data.bus;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable {

    // vars
    private long movieId;
    private String movieName;
    private String youtubeCode;

    public MovieData(long movieId, String movieName, String youtubeCode) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.youtubeCode = youtubeCode;
    } // constructor

    private MovieData(Parcel in) {
        movieId = in.readLong();
        movieName = in.readString();
        youtubeCode = in.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    // getter
    public long getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getYoutubeCode() {
        return youtubeCode;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(movieName);
        dest.writeString(youtubeCode);
    }

}
