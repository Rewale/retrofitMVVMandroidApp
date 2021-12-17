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

    public String fromToAirports(){
        return airFrom+" - "+airTo;
    }
    public String getMinCost(){
        if(freeTickets.size() == 0)
            return "Нет билетов";
        int cost=freeTickets.get(0).getCost();
        for (Ticket ticket:freeTickets) {
            if(ticket.getCost()<cost)
                cost=ticket.getCost();
        }

        return cost+"";
    }
    public String getFlight_ID() {
        return flight_ID;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getAirFrom() {
        return airFrom;
    }

    public String getAirTo() {
        return airTo;
    }

    public Company getCompany() {
        return company;
    }

    public List<Ticket> getFreeTickets() {
        return freeTickets;
    }
}
