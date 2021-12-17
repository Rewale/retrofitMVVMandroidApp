package com.example.movieappmvvmretrofit2.repositories;

import androidx.lifecycle.LiveData;

import com.example.movieappmvvmretrofit2.models.Airport;
import com.example.movieappmvvmretrofit2.models.Flight;
import com.example.movieappmvvmretrofit2.request.TicketApiClient;

import java.util.List;

public class TicketRepository {
    private static TicketRepository instance;

    private TicketApiClient ticketApiClient;

    private String airFrom;
    private String airTo;
    private int pageNum;

    public static TicketRepository getInstance(){
        if(instance==null)
            instance=new TicketRepository();
        return instance;
    }

    private TicketRepository(){
        ticketApiClient = TicketApiClient.getInstance();
    }
    public LiveData<List<Flight>> getFlightsList(){
        return ticketApiClient.getFlightsList();
    }

    public LiveData<List<Airport>> getAirportsList(){
        return ticketApiClient.getAirports();
    }

    public void searchFlights(String airFrom, String airTo)
    {
        this.airFrom=airFrom;
        this.airTo = airTo;
        ticketApiClient.searchFlightsApi(airTo,airFrom);
    }

    public  void getAirports()
    {
        ticketApiClient.getAirports();
    }
    //Todo: пагинация
    //public void searchNextPage(){
//        searchMovieApi(mQuery, mPageNumber+1);
//    }
}
