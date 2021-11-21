package com.example.movieappmvvmretrofit2.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MovieModel implements Parcelable {
    // Model class
    private int movie_id;
    private String title;
    private String poster_path;


    private String backdrop_path;
    private String release_date;
    private float vote_average;
    @SerializedName("overview")
    private String movie_overview;
    private String original_language;

    public String getMovie_overview() {
        return movie_overview;
    }


    public String getOriginal_language() {
        return original_language;
    }

    protected MovieModel(Parcel in) {
        movie_id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        release_date=in.readString();
        vote_average = in.readFloat();
        movie_overview = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return movie_overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }






    public MovieModel(int movie_id,
                      String title,
                      String poster_path,
                      String release_date,
                      float vote_average,
                      String overview,
                      String original_language,
                      String backdrop_path) {
        this.movie_id = movie_id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.movie_overview = overview;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        movie_id = in.readInt();
//        title = in.readString();
//        poster_path = in.readString();
//        release_date=in.readString();
//        vote_average = in.readFloat();
//        movie_overview = in.readString();
//        runtime = in.readInt();
        dest.writeInt(movie_id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeFloat(vote_average);
        dest.writeString(movie_overview);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);

    }
}
