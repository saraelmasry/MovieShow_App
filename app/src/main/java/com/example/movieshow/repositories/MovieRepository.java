package com.example.movieshow.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.movieshow.data.MovieClient;
import com.example.movieshow.pojo.MovieModel;

import java.util.List;

public class MovieRepository {
    //this class is acting as repositories

    private static MovieRepository INSTANCE;

    private MovieClient movieClient;

    private String NextQuery;
    private int NextPageNumber;

    //Method Instance
    public static MovieRepository getINSTANCE() {
        if(null==INSTANCE){
            INSTANCE=new MovieRepository();
        }
        return INSTANCE;
    }

    //Constructor
    private MovieRepository() {
        movieClient=MovieClient.getINSTANCE();
    }

    //Getter
    public MutableLiveData<List<MovieModel>> getMoviesMutableLiveData() {
        return movieClient.getMovies();
    }
    public MutableLiveData<List<MovieModel>> getPop() {
        return movieClient.getMoviesPop();
    }


    //2-calling the method repository
    public void searchMovieApi(String query,int pageNumber){
        NextQuery=query;
        NextPageNumber=pageNumber;
        movieClient.SearchMovieApi(query,pageNumber);
    }


    public void searchMoviePop(int pageNumber){
        NextPageNumber=pageNumber;
        movieClient.SearchMoviePop(pageNumber);
    }


    public void searchNextPage(){
        searchMovieApi(NextQuery,NextPageNumber+1);
    }
}
