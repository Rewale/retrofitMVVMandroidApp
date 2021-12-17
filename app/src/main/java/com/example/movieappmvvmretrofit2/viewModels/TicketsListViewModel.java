package com.example.movieappmvvmretrofit2.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieappmvvmretrofit2.models.Airport;
import com.example.movieappmvvmretrofit2.models.Flight;
import com.example.movieappmvvmretrofit2.models.Ticket;
import com.example.movieappmvvmretrofit2.repositories.TicketRepository;

import java.util.List;

public class TicketsListViewModel extends ViewModel {
    private TicketRepository ticketRepository;

    public TicketsListViewModel(){
        ticketRepository=TicketRepository.getInstance();
    }
    public LiveData<List<Flight>> getFlights(){
        return ticketRepository.getFlightsList();
    }
    public LiveData<List<Airport>> getAirportsLiveData(){
        return ticketRepository.getAirportsList();
    }

    public void searchFlights(String airFrom, String airTo){
        ticketRepository.searchFlights(airFrom, airTo);
    }

    public void getAirports()
    {
        ticketRepository.getAirports();
    }



}
