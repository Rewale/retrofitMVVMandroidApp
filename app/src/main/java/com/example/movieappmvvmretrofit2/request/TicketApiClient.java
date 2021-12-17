package com.example.movieappmvvmretrofit2.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieappmvvmretrofit2.AppExecutors;
import com.example.movieappmvvmretrofit2.models.Airport;
import com.example.movieappmvvmretrofit2.models.Flight;
import com.example.movieappmvvmretrofit2.response.MovieSearchResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class TicketApiClient {



    private static TicketApiClient instance;
    private MutableLiveData<List<Flight>> mflights;
    private MutableLiveData<List<Airport>> mAirports;
    private RetriveFlightsRunnable retriveFlightsRunnableRunnable;
    private RetriveAirportsRunnable retriveAirportsRunnable;


    private TicketApiClient() {
        mflights = new MutableLiveData<>();
        mAirports = new MutableLiveData<>();
    }

    public LiveData<List<Flight>> getFlightsList()
    {
        return mflights;
    }
    public LiveData<List<Airport>> getAirports(){
        return mAirports;
    }

    public static TicketApiClient getInstance() {
        if(instance==null){
            instance=new TicketApiClient();
        }
        return instance;
    }
    public void searchFlightsApi(String airTo, String airFrom)
    {
        if(retriveFlightsRunnableRunnable != null)
        {
            retriveFlightsRunnableRunnable = null;
        }
        retriveFlightsRunnableRunnable = new RetriveFlightsRunnable(airFrom, airTo);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveFlightsRunnableRunnable);

//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // Cancelling the retrofit call
//                myHandler.cancel(true);
//            }
//        },3000, TimeUnit.MILLISECONDS);
    }

    public void getAllAirports()
    {
        if(retriveAirportsRunnable != null)
        {
            retriveAirportsRunnable = null;
        }
        retriveAirportsRunnable = new RetriveAirportsRunnable();
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveAirportsRunnable);

//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // Cancelling the retrofit call
//                myHandler.cancel(true);
//            }
//        },3000, TimeUnit.MILLISECONDS);
    }



    private class RetriveFlightsRunnable implements Runnable {

        private String airFromCode;
        private String airToCode;

        boolean cancelRequest;

        public RetriveFlightsRunnable(String airFromCode, String airToCode){
            this.airFromCode=airFromCode;
            this.airToCode=airToCode;

            cancelRequest=false;
        }

        @Override
        public void run() {
            try {
                Response response = getFlights().execute();
                if (cancelRequest)
                    return;
                if (response.code() == 200) {
                    // TODO: нет пагинации, да и пофек
                    Log.v(Service.tagForLogin, "Список билетов пришел!");
                    List<Flight> list = new ArrayList<>((List<Flight>) response.body());
                    mflights.postValue(list);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.v(Service.tagForLogin, "da ne mogu ya");
                mflights.postValue(null);
            }
        }


        // Search Method query
        private Call<List<Flight>> getFlights ()
        {
            if(airToCode!=null && airFromCode!=null)
                return Service.getTicketApi().flights(airToCode,airFromCode);

            return  Service.getTicketApi().flights();
        }
        private void cancelRequest(){
            Log.v(Service.tagForLogin, "Cancelling request");
            cancelRequest=true;
        }
    }


    private class RetriveAirportsRunnable implements Runnable {



        boolean cancelRequest;

        public RetriveAirportsRunnable(){

            cancelRequest=false;
        }

        @Override
        public void run() {
            try {
                Response response = getAirports().execute();
                if (cancelRequest)
                    return;
                if (response.code() == 200) {
                    // TODO: нет пагинации, да и пофек
                    Log.v(Service.tagForLogin, "Список аэропортов пришел!");
                    List<Airport> list = new ArrayList<>((List<Airport>) response.body());
                    mAirports.postValue(list);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.v(Service.tagForLogin, "da ne mogu ya");
                mflights.postValue(null);
            }
        }


        // Search Method query
        private Call<List<Airport>> getAirports ()
        {
            return  Service.getTicketApi().airports();
        }
        private void cancelRequest(){
            Log.v(Service.tagForLogin, "Cancelling request");
            cancelRequest=true;
        }
    }
}
