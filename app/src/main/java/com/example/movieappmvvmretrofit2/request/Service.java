package com.example.movieappmvvmretrofit2.request;

import com.example.movieappmvvmretrofit2.utlis.Credentials;
import com.example.movieappmvvmretrofit2.utlis.MovieApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// singleton pattern
public class Service {


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi()
    {
        return movieApi;
    }
}
