package com.example.movieshow.ui;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshow.Adapter.OnMovieListener;
import com.example.movieshow.Adapter.RecyclerView_Adapter;
import com.example.movieshow.MovieDetails;
import com.example.movieshow.R;
import com.example.movieshow.pojo.MovieModel;
import com.example.movieshow.viewmodels.MovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private RecyclerView_Adapter recyclerView_adapter;
    //ViewModel
    private MovieListViewModel movieListViewModel;
    boolean isPopular=true;
    Toolbar toolbar;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToolBar
         toolbar=findViewById(R.id.toolBar);
         setSupportActionBar(toolbar);

       searchView =findViewById(R.id.search_ic);

        recyclerView=findViewById(R.id.MovieRecyclerView);

        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyDataChange();
        //SearchView
        SetupSearchView();
        ObservePopularMovies();

        //Getting Popular Movies
        movieListViewModel.searchMoviePop(1);
    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing any data changing
                if (movieModels!=null){
                    for(MovieModel movieModel:movieModels){
                        //getting the data in log
                        Log.v("TAG","onChanged :"+movieModel.getTitle());
                        recyclerView_adapter.setmMovies(movieModels);
                    }
                }
            }
        });

    }

    //Observing any data changing
    private void ObserveAnyDataChange(){
        movieListViewModel.getMoviesMutableLiveData().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing any data changing
                if (movieModels!=null){
                    for(MovieModel movieModel:movieModels){
                        //getting the data in log
                        Log.v("TAG","onChanged :"+movieModel.getTitle());
                        recyclerView_adapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    //5- initializing recyclerView & adding data on it
    private void ConfigureRecyclerView(){
        recyclerView_adapter=new RecyclerView_Adapter(this);
        recyclerView.setAdapter(recyclerView_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //Loading next page of api responses
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!recyclerView.canScrollVertically(1)){
                    //Here we need to display the next search results on the next page of api responses
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }


    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie",recyclerView_adapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    //get data from searchView & query the api to get the results (movies)
    private void SetupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        //the search string gets from searchView
                        query,
                        1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular=false;
            }
        });
    }





    //    private void GetRetrofitResponse() {
//        MovieApi movieApi=MovieClient.getMovieApi();
//        Call<MovieSearchResponse> responseCall= movieApi.searchMovie(
//                 BasicsLink.API_KEY,
//                "Jack Reacher",
//                1);
//
//
//      responseCall.enqueue(new Callback<MovieSearchResponse>() {
//          @Override
//          public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//              if(response.code()==200){
//                  MovieSearchResponse movies=response.body();
//                  Log.v(TAG,"Result "+ response.body().getTotalResults());
//
//              }else{
//                  try {
//                      Log.v(TAG ,"error"+response.errorBody().string());
//                  } catch (Exception e) {
//                      e.printStackTrace();
//                  }
//              }
//          }
//
//          @Override
//          public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//              Log.v(TAG ,"Movie Search failure"+t.toString());
//          }
//      });
//    }
//
//    private void GetRetrofitResponseToID(){
//        MovieApi movieApi = MovieClient.getMovieApi();
//        Call<MovieModel> movieModelCall=movieApi.getMovie(
//                550,
//                "b83e9a4ca9fb2f722708b24d33a2bdc8"
//        );
//        movieModelCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.code()==200){
//                    MovieModel movie=response.body();
//                    Log.v(TAG,"the response"+movie.getTitle());
//
//                }else{
//                    try {
//                        Log.v(TAG,"Error"+response.errorBody().string());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//                Log.v(TAG ,"Movie ID failure"+t.toString());
//            }
//        });
//    }
}