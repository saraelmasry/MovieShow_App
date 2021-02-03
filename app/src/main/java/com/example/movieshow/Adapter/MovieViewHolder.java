package com.example.movieshow.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshow.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //widgets
    ImageView poster;
    RatingBar Rate;

    //Click Listener
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener=onMovieListener;

        poster=itemView.findViewById(R.id.movie_img);

        Rate=itemView.findViewById(R.id.rating_stars);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
