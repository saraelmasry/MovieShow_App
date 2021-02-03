package com.example.movieshow.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;

public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    private int totalResults;


    @SerializedName("results")
    @Expose
    private List<MovieModel> movies ;




    public List<MovieModel> getMovies() {
        return movies;
    }


    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "results=" + movies +
                ", totalResults=" + totalResults +
                '}';
    }
}

