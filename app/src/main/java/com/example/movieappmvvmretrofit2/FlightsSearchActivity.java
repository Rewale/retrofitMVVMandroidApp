package com.example.movieappmvvmretrofit2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.movieappmvvmretrofit2.adapters.MovieRecyclerView;
import com.example.movieappmvvmretrofit2.adapters.flightAdapter.FlightRecyclerView;
import com.example.movieappmvvmretrofit2.adapters.flightAdapter.OnFlightListener;
import com.example.movieappmvvmretrofit2.models.Airport;
import com.example.movieappmvvmretrofit2.models.Flight;
import com.example.movieappmvvmretrofit2.viewModels.MovieListViewModel;
import com.example.movieappmvvmretrofit2.viewModels.TicketsListViewModel;

import java.util.List;

public class FlightsSearchActivity extends AppCompatActivity implements OnFlightListener {

    //RecyclerView
    private RecyclerView recyclerView;
    private FlightRecyclerView adapter;

    private Spinner spinnerFrom;
    private Spinner spinnerTo;

    //ViewModel
    private TicketsListViewModel ticketsListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_search);
        recyclerView = findViewById(R.id.fligtsView);
        spinnerFrom = findViewById(R.id.spinnerFromAirports);
        spinnerTo = findViewById(R.id.spinnerToAirports);
        ticketsListViewModel = new ViewModelProvider(this).get(TicketsListViewModel.class);


        ConfigureRecyclerView();

        // Calling the observer
        ObserverAnyChange();

        searchFlightApi(null,null);

    }

    private void searchFlightApi(String airFrom, String airTo){
        ticketsListViewModel.searchFlights(airFrom, airTo);
    }

    private void ObserverAnyChange() {
        ticketsListViewModel.getFlights().observe(this, new Observer<List<Flight>>() {
            @Override
            public void onChanged(List<Flight> flights) {
                if(flights!=null){
                    adapter.setFlightList(flights);
                }
            }
        });
        ticketsListViewModel.getAirportsLiveData().observe(this, new Observer<List<Airport>>() {
            @Override
            public void onChanged(List<Airport> airports) {

            }
        });

    }

    private void ConfigureRecyclerView() {
        adapter = new FlightRecyclerView(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFlightClick(int position) {
        // TODO: переход на активити покупки/брони
    }
}