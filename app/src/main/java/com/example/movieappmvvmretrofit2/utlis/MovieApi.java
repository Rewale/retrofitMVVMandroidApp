package com.example.movieappmvvmretrofit2.utlis;

import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // Вставляем параметры в запрос
    @GET("/3/search/movie?language=ru")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("/3/movie/{movie_id}?language=ru")
    Call<MovieModel> getMovie(
      @Path("movie_id") int movie_id,
      @Query("api_key") String api_key
    );

    // Get popular movie
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );
}
