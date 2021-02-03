package com.example.movieshow.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MovieModel implements Parcelable {

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("video")
    @Expose
    private Boolean video;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("original_language")
    @Expose
    private String original_language;

// we will use release_date instead of category

    public MovieModel(Parcel in) {
        backdropPath = in.readString();
        id = in.readInt();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        voteAverage=in.readFloat();
        original_language=in.readString();
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

    //Getters
    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOriginal_language() {
        return original_language;
    }

    //constructor


    public MovieModel(String backdropPath, int id, String overview, String posterPath, String releaseDate, String title,
                      Boolean video, float voteAverage, String original_language) {
        this.backdropPath = backdropPath;
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.original_language = original_language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdropPath);
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(title);
        dest.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
        dest.writeFloat(voteAverage);
        dest.writeString(original_language);
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "backdropPath='" + backdropPath + '\'' +
                ", id=" + id +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", original_language='" + original_language + '\'' +
                '}';
    }
}
