package com.example.movieappmvvmretrofit2.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappmvvmretrofit2.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


    // Widgets
    TextView title, duration, category;
    ImageView imageView;
    RatingBar ratingBar;

    // Click Listener
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        title = itemView.findViewById(R.id.movie_title);
        category = itemView.findViewById(R.id.movie_category);
        duration = itemView.findViewById(R.id.movie_duration);

        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);

        this.onMovieListener = onMovieListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
