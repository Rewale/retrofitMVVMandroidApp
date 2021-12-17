package com.example.movieappmvvmretrofit2.utlis;

import com.example.movieappmvvmretrofit2.models.Airport;
import com.example.movieappmvvmretrofit2.models.Flight;
import com.example.movieappmvvmretrofit2.response.AirportResponse;

import com.example.movieappmvvmretrofit2.response.TicketResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TicketApi {
    // Общедоступные (не требуют api key)
    @GET("/tickets/airports/")
    Call<List<Airport>> airports();

    @GET("/tickets/flights/")
    Call<List<Flight>> flights(@Query("airTo") String airTo, @Query("airFrom") String airFrom);

    @GET("/tickets/flights/")
    Call<List<Flight>> flights();

    @GET("/tickets/flights/{flight_num}")
    Call<List<Flight>> flightByNum(@Path("flight_num") String flightNum);

    @GET("/tickets/tickets/{flight_num}/{seq}")
    Call<TicketResponse> ticket(@Path("flight_num") String flight_num, @Path("seq") String seq);

    //TODO: Только авторизованным пользователям - покупка билета, просмотр личного кабинета, просмотр купленных билетов, покупка, возврат


}
