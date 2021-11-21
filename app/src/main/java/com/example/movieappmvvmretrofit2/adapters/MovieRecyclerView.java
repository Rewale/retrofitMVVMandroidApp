package com.example.movieappmvvmretrofit2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappmvvmretrofit2.R;
import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.utlis.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> movies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MovieViewHolder)holder).title.setText(movies.get(position).getTitle());
        ((MovieViewHolder)holder).category.setText(movies.get(position).getRelease_date());
        ((MovieViewHolder)holder).duration.setText(movies.get(position).getOriginal_language());

        ((MovieViewHolder)holder).ratingBar.setRating((movies.get(position).getVote_average())/2);

        // ImageView using Glide libary
        Glide.with(holder.itemView.getContext())
                .load(Credentials.BASE_URL_IMAGE +movies.get(position).getBackdrop_path())
                .into(((MovieViewHolder) holder).imageView);
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(movies !=null)
            return movies.size();
        else
            return 0;
    }
    public MovieModel getSelectedMovie(int pos)
    {
        if (movies != null){
            if(movies.size()>0){
                return movies.get(pos);
            }
        }
        return null;
    }
}
