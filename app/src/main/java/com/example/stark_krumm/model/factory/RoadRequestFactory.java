package com.example.stark_krumm.model.factory;

import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stark_krumm.R;
import com.example.stark_krumm.model.RoadRequest;

public class RoadRequestFactory extends AppCompatActivity {
    public RoadRequest createRoadRequest() {
        RoadRequest roadRequest = new RoadRequest();
        TextView roadNumber = findViewById(R.id.roadNumber);
        Spinner carNumber = findViewById(R.id.carNumber);
        Spinner driverName = findViewById(R.id.driverName);
        TextView departure = findViewById(R.id.departure);
        TextView arrival = findViewById(R.id.arrival);
        Spinner date = findViewById(R.id.date);
        TextView distanceBig = findViewById(R.id.distanceBig);
        TextView distanceSmall = findViewById(R.id.distanceSmall);
        TextView consumption1 = findViewById(R.id.consumption1);
        TextView consumption2 = findViewById(R.id.consumption2);
        TextView consumption3 = findViewById(R.id.consumption3);

        roadRequest.setRoadNumber(Integer.valueOf(roadNumber.getText().toString()));
        roadRequest.setCarNumber(Integer.valueOf(carNumber.getSelectedItem().toString().substring(3, 5)));
        roadRequest.setDriverName(driverName.getSelectedItem().toString());
        roadRequest.setDeparture(departure.getText().toString());
        roadRequest.setArrival(arrival.getText().toString());
        roadRequest.setDate(date.getSelectedItem().toString());
        roadRequest.setDistanceBig(Integer.valueOf(distanceBig.getText().toString()));
        roadRequest.setDistanceSmall(Integer.valueOf(distanceSmall.getText().toString()));
        roadRequest.setConsumption1(Double.valueOf(consumption1.getText().toString()));
        roadRequest.setConsumption2(Double.valueOf(consumption2.getText().toString()));
        roadRequest.setConsumption3(Double.valueOf(consumption3.getText().toString()));
        return roadRequest;
    }
}


