package com.example.movieshow.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshow.R;

public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    ImageView posterDetails;
     RatingBar ratingBarDetails;

    OnMovieListener onMovieListener;

    public PopularViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;
        posterDetails=itemView.findViewById(R.id.PosterDetails);
        ratingBarDetails=itemView.findViewById(R.id.ratingBar_details);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //onMovieListener.onMovieClick(getAdapterPosition());
    }
}
