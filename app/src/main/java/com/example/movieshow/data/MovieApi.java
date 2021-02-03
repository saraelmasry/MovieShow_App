package com.example.movieshow.data;

import com.example.movieshow.pojo.MovieModel;
import com.example.movieshow.pojo.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //Search for movies
    //https://api.themoviedb.org/3/search/movie?api_key=b83e9a4ca9fb2f722708b24d33a2bdc8&query=Jack+Reacher

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    //Get popular Movies
    //https://api.themoviedb.org/3/movie/popular?api_key=b83e9a4ca9fb2f722708b24d33a2bdc8&language=en-US&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String api_key,
            @Query("page") int page
    );



    //Making search with ID
    //https://api.themoviedb.org/3/movie/550?api_key=b83e9a4ca9fb2f722708b24d33a2bdc8
    @GET("3/movie/{movie_id}?")
    Call<MovieModel>getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key

    );


}
