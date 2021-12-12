package com.example.movieappmvvmretrofit2.utlis;

import com.example.movieappmvvmretrofit2.response.AirportResponse;
import com.example.movieappmvvmretrofit2.response.FlightResponse;
import com.example.movieappmvvmretrofit2.response.TicketResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TicketApi {
    // Общедоступные (не требуют api key)
    @GET("/tickets/airports/")
    Call<AirportResponse> airports();

    @GET("/tickets/tickets/flights/")
    Call<FlightResponse> flights(@Query("airTo") String airTo, @Query("airFrom") String airFrom);


    @GET("/tickets/flights/{flight_num}")
    Call<FlightResponse> flightByNum(@Path("flight_num") String flightNum);

    @GET("/tickets/tickets/{flight_num}/{seq}")
    Call<TicketResponse> ticket(@Path("flight_num") String flight_num, @Path("seq") String seq);

    //TODO: Только авторизованным пользователям - покупка билета, просмотр личного кабинета, просмотр купленных билетов, покупка, возврат


}
