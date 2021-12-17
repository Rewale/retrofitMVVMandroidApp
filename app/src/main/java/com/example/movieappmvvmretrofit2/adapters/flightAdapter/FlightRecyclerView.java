package com.example.movieappmvvmretrofit2.adapters.flightAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieappmvvmretrofit2.R;
import com.example.movieappmvvmretrofit2.adapters.MovieViewHolder;
import com.example.movieappmvvmretrofit2.models.Flight;
import com.example.movieappmvvmretrofit2.models.MovieModel;
import com.example.movieappmvvmretrofit2.utlis.Credentials;

import java.util.List;

public class FlightRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Flight> flightList;
    private OnFlightListener onFlightListener;

    public FlightRecyclerView(OnFlightListener onFlightListener){
        this.onFlightListener = onFlightListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item, parent, false);

        return new FlightViewHolder(view, onFlightListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Flight currentFlight= flightList.get(position);
        ((FlightViewHolder)holder).flightNum.setText(currentFlight.getFlight_ID());
        ((FlightViewHolder)holder).textView4.setText(currentFlight.getMinCost());
        // TODO: нормальный вывод даты
        ((FlightViewHolder)holder).dateOut.setText(currentFlight.getDateFrom().toString());
        ((FlightViewHolder)holder).fromToAirports.setText(currentFlight.fromToAirports());

        // ImageView using Glide libary
        Glide.with(holder.itemView.getContext())
                .load(currentFlight.getCompany().getIcon())
                .into(((FlightViewHolder) holder).companyImage);
    }

    @Override
    public int getItemCount() {
        if(flightList != null)
            return  flightList.size();
        else
            return 0;
    }

    public void setFlightList(List<Flight> flightList)
    {
        this.flightList = flightList;
        notifyDataSetChanged();
    }

    public Flight getSelectedFlight(int pos)
    {
        if (flightList != null){
            if(flightList.size()>0){
                return flightList.get(pos);
            }
        }
        return null;
    }
}
