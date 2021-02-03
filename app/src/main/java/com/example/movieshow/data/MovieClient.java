package com.example.movieshow.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.movieshow.AppExecutors;
import com.example.movieshow.pojo.MovieModel;
import com.example.movieshow.pojo.MovieSearchResponse;
import com.example.movieshow.repositories.MovieRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {
   // MutableLiveData for search
    private MutableLiveData<List<MovieModel>> MoviesMutableLiveData;
    private static MovieClient INSTANCE;

    //making Global Runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    // MutableLiveData for Popular movies
    private MutableLiveData<List<MovieModel>> mMoviesPop;

    //making Popular Runnable
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;


    //Retrofit && REST APIs
    private static Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BasicsLink.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    private static  MovieApi movieApi =retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }

    //Method Instance
    public static MovieClient getINSTANCE() {
        if(null==INSTANCE){
            INSTANCE=new MovieClient();
        }
        return INSTANCE;
    }

    //constructor
    public MovieClient() {
        MoviesMutableLiveData=new MutableLiveData<>();
        mMoviesPop=new MutableLiveData<>();
    }


    //Getters
    public MutableLiveData<List<MovieModel>> getMovies() {
        return MoviesMutableLiveData;
    }

    public MutableLiveData<List<MovieModel>> getMoviesPop() {
        return mMoviesPop;
    }


    //1-this method that we are going to call through the classes
    public void SearchMovieApi(String query, int pageNumber){
        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable=null;
        }

        retrieveMoviesRunnable= new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler= AppExecutors.getInstance().getNetworkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling Retrofit Call
                myHandler.cancel(true);
       }},3000, TimeUnit.MILLISECONDS);
    }


    public void SearchMoviePop( int pageNumber){
        if (retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop=null;
        }

        retrieveMoviesRunnablePop= new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2= AppExecutors.getInstance().getNetworkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling Retrofit Call
                myHandler2.cancel(true);
            }},1000, TimeUnit.MILLISECONDS);
    }




//_______________________________________________________________________________________________________________
//Retrieving data from REST Api and Runnable class
//we have 2 types of Queries : ID & Search Query
 class RetrieveMoviesRunnable implements Runnable {
    private String query;
    private int pageNumber;
    boolean cancelRequest;

    public RetrieveMoviesRunnable(String query, int pageNumber) {
        this.query = query;
        this.pageNumber = pageNumber;
        cancelRequest=false;
    }

    @Override
    public void run() {

        //Getting response objects
        try{
            Response response=getMovie(query,pageNumber).execute();

            if (cancelRequest){
                return;
            }

            if (response.code()==200){
                List<MovieModel> listMovie=new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                if (pageNumber==1){
                    //sending data to live data
                    //post value using for background thread
                    MoviesMutableLiveData.postValue(listMovie);
                }else{
                    List<MovieModel> currentMovies=MoviesMutableLiveData.getValue();
                    currentMovies.addAll(listMovie);
                    MoviesMutableLiveData.postValue(currentMovies);
                }
            }
            else {
                String error=response.errorBody().string();
                Log.v("TAG","Error"+ error);
                MoviesMutableLiveData.postValue(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            MoviesMutableLiveData.postValue(null);
        }
    }

        //Search Method / Query
        private Call<MovieSearchResponse>  getMovie(String query,int pageNumber)
        {
           return MovieClient.getMovieApi().searchMovie(
                    BasicsLink.API_KEY,
                    query,
                    pageNumber
            );
        }
       private void CancelRequest(){
            Log.v("TAG","cancelling search request");
            cancelRequest=true;
        }
}

class RetrieveMoviesRunnablePop implements Runnable {
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() {

            //Getting response objects
            try{
                Response response2=getPop(pageNumber).execute();

                if (cancelRequest){
                    return;
                }

                if (response2.code()==200){
                    List<MovieModel> listMovie=new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());

                    if (pageNumber==1){
                        //sending data to live data
                        //post value using for background thread
                        mMoviesPop.postValue(listMovie);
                    }else{
                        List<MovieModel> currentMovies=mMoviesPop.getValue();
                        currentMovies.addAll(listMovie);
                        mMoviesPop.postValue(currentMovies);
                    }
                }
                else {
                    String error=response2.errorBody().string();
                    Log.v("TAG","Error"+ error);
                    mMoviesPop.postValue(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }
        }

        //Search Method / Query
        private Call<MovieSearchResponse>  getPop(int pageNumber)
        {
            return MovieClient.getMovieApi().getPopular(
                    BasicsLink.API_KEY,
                    pageNumber
            );
        }
        private void CancelRequest(){
            Log.v("TAG","cancelling search request");
            cancelRequest=true;
        }
    }
}
