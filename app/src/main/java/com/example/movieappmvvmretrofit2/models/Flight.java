package com.example.movieappmvvmretrofit2.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Flight {
    private String flight_ID;
    private Date dateFrom;
    private Date dateTo;

    private String airFrom;
    private String airTo;

    private Company company;
    @SerializedName("free_tickets")
    private List<Ticket> freeTickets;



}
