package com.example.movieappmvvmretrofit2.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieappmvvmretrofit2.AppExecutors;
import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.response.MovieSearchResponse;
import com.example.movieappmvvmretrofit2.utlis.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    // LiveData
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    public LiveData<List<MovieModel>> getmPopularMovies() {
        return mPopularMovies;
    }

    private  MutableLiveData<List<MovieModel>> mPopularMovies;
    private RetriveMoviesRunnable retrivePopularMoviesRunnable;

    // making global RUNNABLE
    private RetriveMoviesRunnable retriveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if(instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }
    // TODO: сложно
    // 1 - Calling this method
    public void searchMoviesApi(String query, int pageNumber){

        if(retriveMoviesRunnable != null){
            retriveMoviesRunnable = null;
        }

        retriveMoviesRunnable = new RetriveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnable);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);

    }

    

    // Retriving data from RESTapi by runnable class
    // Два типа запроса: поиск по айди или по имени
    private class RetriveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {


            // Getting the response objects
            try{

                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if(response.code() == 200)
                {
                    Log.v("Tag", "Данные пришли: 200");
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        // TODO: загрузка данных в liveData
                        // Sending data to live data
                        // PostValue: used for background thread
                        // setValue: not for background thread
                        mMovies.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    Log.v("Tag", "Данные пришли: 400 ");
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error "+error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.v("Tag", "da ne mogu ya");
                mMovies.postValue(null);
            }


        }

        // Search Method query
        private Call<MovieSearchResponse> getMovies (String query, int pageNumber)
        {
            return Service.getMovieApi().searchMovie(
                    //Credentials.API_KEY,
                    "123123",
                    query,
                    pageNumber);
        }
        private void cancelRequest(){
            Log.v("Tag", "Cancelling request");
            cancelRequest=true;
        }


    }




}
