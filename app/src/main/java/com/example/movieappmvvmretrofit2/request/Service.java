package com.example.movieappmvvmretrofit2.request;

import com.example.movieappmvvmretrofit2.utlis.Credentials;
import com.example.movieappmvvmretrofit2.utlis.LoginApi;
import com.example.movieappmvvmretrofit2.utlis.MovieApi;
import com.example.movieappmvvmretrofit2.utlis.TicketApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// singleton pattern
public class Service {

    public static String tagForLogin = "loginLog";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    private static LoginApi loginApi = retrofit.create(LoginApi.class);

    public static TicketApi getTicketApi() {
        return ticketApi;
    }

    private static TicketApi ticketApi = retrofit.create(TicketApi.class);

    public static MovieApi getMovieApi()
    {
        return movieApi;
    }

    public static LoginApi getLoginApi() {
        return loginApi;
    }
}
