package com.example.movieappmvvmretrofit2.response;

import com.example.movieappmvvmretrofit2.models.Airport;
import com.google.gson.annotations.Expose;

import java.util.List;

public class AirportResponse {
    public List<Airport> getAirportList() {
        return airportList;
    }

    @Expose
    List<Airport> airportList;

    @Override
    public String toString() {
        return "AirportResponse{" +
                "airportList=" + airportList +
                '}';
    }
}
