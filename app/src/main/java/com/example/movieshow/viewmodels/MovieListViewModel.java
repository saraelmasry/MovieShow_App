package com.example.movieshow.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshow.pojo.MovieModel;
import com.example.movieshow.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    //this class is used for ViewModel

   private MovieRepository movieRepository;

   //Constructor
    public MovieListViewModel() {
        movieRepository=MovieRepository.getINSTANCE();
    }

    //Getter
    public MutableLiveData<List<MovieModel>> getMoviesMutableLiveData() {
        return movieRepository.getMoviesMutableLiveData();
    }
    public MutableLiveData<List<MovieModel>> getPop() {
        return movieRepository.getPop();
    }

    //3-calling the method ViewModel
    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }
    public void searchMoviePop(int pageNumber){
        movieRepository.searchMoviePop(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }

}
