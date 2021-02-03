package com.example.movieshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieshow.pojo.MovieModel;

public class MovieDetails extends AppCompatActivity {
    private ImageView posterDetails;
    private TextView nameDetails, decDetails;
    private RatingBar ratingBarDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        posterDetails=findViewById(R.id.PosterDetails);
        nameDetails=findViewById(R.id.NameDetails);
        decDetails=findViewById(R.id.DescriptionDetails);
        ratingBarDetails=findViewById(R.id.ratingBar_details);

        GetDataFromIntent();
    }

    private void GetDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel=getIntent().getParcelableExtra("movie");
            Log.v("Tagy","incoming intent"+movieModel.getTitle());
            nameDetails.setText(movieModel.getTitle());
            decDetails.setText(movieModel.getOverview());
            ratingBarDetails.setRating((movieModel.getVoteAverage())/2);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500"+movieModel.getPosterPath())
                    .into(posterDetails);
        }
    }
}