package com.example.movieappmvvmretrofit2.adapters.flightAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappmvvmretrofit2.R;

public class FlightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //widgets
    TextView flightNum, dateOut, textView4, fromToAirports;
    ImageView companyImage;

    OnFlightListener onFlightListener;

    public FlightViewHolder(@NonNull View itemView, OnFlightListener onFlightListener) {
        super(itemView);

        flightNum = itemView.findViewById(R.id.fligtNum);
        dateOut = itemView.findViewById(R.id.DateOut);
        textView4 = itemView.findViewById(R.id.price);
        fromToAirports = itemView.findViewById(R.id.fromToAirports);
        companyImage = itemView.findViewById(R.id.companyImage);
        this.onFlightListener = onFlightListener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onFlightListener.onFlightClick(getAdapterPosition());
    }


}
