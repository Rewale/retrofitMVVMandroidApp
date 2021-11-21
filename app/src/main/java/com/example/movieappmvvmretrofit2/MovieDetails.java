package com.example.movieappmvvmretrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieappmvvmretrofit2.adapters.MovieViewHolder;
import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.utlis.Credentials;

public class MovieDetails extends AppCompatActivity {

    // Widgets
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mDescription;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Init widgets
        mTitle = findViewById(R.id.title_details);
        mDescription = findViewById(R.id.description_details);
        mRatingBar = findViewById(R.id.ratingBarDetails);
        mPoster = findViewById(R.id.poster_details);

        Bundle args = getIntent().getExtras();
        MovieModel movieModel = (MovieModel) args.get("movie");

        mTitle.setText(movieModel.getTitle());
        mDescription.setText(movieModel.getOverview());
        mRatingBar.setRating(movieModel.getVote_average());

        // load poster
        Glide.with(this)
                .load(Credentials.BASE_URL_IMAGE +movieModel.getPoster_path())
                .into(mPoster);
    }
}