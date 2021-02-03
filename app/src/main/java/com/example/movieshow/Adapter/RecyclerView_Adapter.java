package com.example.movieshow.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieshow.MovieDetails;
import com.example.movieshow.R;
import com.example.movieshow.data.BasicsLink;
import com.example.movieshow.pojo.MovieModel;

import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP=1;
    private static final int DISPLAY_SEARCH=2;

    public RecyclerView_Adapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=null;
        if (viewType==DISPLAY_SEARCH){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,
                    parent,false);
            return new MovieViewHolder(view,onMovieListener);
        }else{
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_movie_details,
                    parent,false);
            return new PopularViewHolder(view,onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType=getItemViewType(position);
        if (itemViewType==DISPLAY_SEARCH){
            //we divided to 2 cause the rate in thmbd is 10 stars but we make only 5 stars
            ((MovieViewHolder)holder).Rate.setRating ((mMovies.get(position).getVoteAverage())/2);

            //Image view: using Glide Library
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500"+mMovies.get(position).getPosterPath())
                    .into(((MovieViewHolder)holder).poster);
        }else{
            //we divided to 2 cause the rate in thmbd is 10 stars but we make only 5 stars
            ((PopularViewHolder)holder).ratingBarDetails.setRating ((mMovies.get(position).getVoteAverage())/2);

            //Image view: using Glide Library
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500"+mMovies.get(position).getPosterPath())
                    .into(((PopularViewHolder)holder).posterDetails);

        }
    }

    @Override
    public int getItemCount() {
        if (mMovies!=null){
            return mMovies.size();
        }
        return 0;
    }

    //setter
    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //getting the id on the movie clicked
    public MovieModel getSelectedMovie(int position){
        if(mMovies!=null){
            if (mMovies.size()>0){
                return mMovies.get(position);
            }
        }return null;
    }

    @Override
    public int getItemViewType(int position) {

        if (BasicsLink.POPULAR){
            return DISPLAY_POP;
        }

        return DISPLAY_SEARCH;
    }
}
