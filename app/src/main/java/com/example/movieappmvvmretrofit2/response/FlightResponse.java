package com.example.movieappmvvmretrofit2.response;

import com.example.movieappmvvmretrofit2.models.Flight;
import com.google.gson.annotations.Expose;

import java.util.List;

public class FlightResponse {
    public List<Flight> getFlightList() {
        return flightList;
    }

    @Expose
    private List<Flight> flightList;

    @Override
    public String toString() {
        return "FlightResponse{" +
                "flightList=" + flightList +
                '}';
    }
}
