package com.example.movieappmvvmretrofit2.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){
        if(instance == null)
            instance = new MovieRepository();
        return instance;
    }

    private MovieRepository(){

        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    // 2 - Calling the method
    public void searchMovieApi(String query, int pageNumber){

        mQuery=query;
        mPageNumber=pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }


}

