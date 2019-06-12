package com.example.stark_krumm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stark_krumm.model.RoadRequest;
import com.example.stark_krumm.model.RoadResponse;
import com.example.stark_krumm.network.NetworkModule;
import com.example.stark_krumm.network.api.RoadApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RoadApi roadApi = NetworkModule.provideRoadApi(NetworkModule.provideRetrofit());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitButtonClicked(View view) {
        RoadRequest roadRequest = createRoadRequest();
        Call<List<RoadResponse>> listCall = roadApi.saveRoad(roadRequest);
        listCall.enqueue(new Callback<List<RoadResponse>>() {
            @Override
            public void onResponse(Call<List<RoadResponse>> call, Response<List<RoadResponse>> response) {
                System.out.println("OK: " + response.body());
            }

            @Override
            public void onFailure(Call<List<RoadResponse>> call, Throwable t) {
                System.out.println("BAD");
            }
        });

    }

    public void deleteButtonClicked(View view) {
        Spinner dateSpinner = findViewById(R.id.dateDelete);
        String date = dateSpinner.getSelectedItem().toString();
        Spinner carNumberSpinner = findViewById(R.id.carNumberDelete);
        Integer carNumber = Integer.valueOf(carNumberSpinner.getSelectedItem().toString());
        TextView roadNumberTextView = findViewById(R.id.roadNumberDelete);
        Integer roadNumber = Integer.valueOf(roadNumberTextView.getText().toString());
        roadApi.deleteRoad(date, carNumber, roadNumber);
    }

    public void uploadButtonClicked(View view) {
        Spinner dateSpinner = findViewById(R.id.dateDrive);
        String date = dateSpinner.getSelectedItem().toString();
        Spinner carNumberSpinner = findViewById(R.id.carNumberDrive);
        Integer carNumber = Integer.valueOf(carNumberSpinner.getSelectedItem().toString());
        roadApi.uploadRoad(date, carNumber);
    }

    private RoadRequest createRoadRequest() {
        System.out.println(findViewById(R.id.roadNumber));
        RoadRequest roadRequest = new RoadRequest();
        EditText roadNumber = findViewById(R.id.roadNumber);
        Spinner carNumber = findViewById(R.id.carNumber);
        Spinner driverName = findViewById(R.id.driverName);
        EditText departure = findViewById(R.id.departure);
        EditText arrival = findViewById(R.id.arrival);
        Spinner date = findViewById(R.id.date);
        EditText distanceBig = findViewById(R.id.distanceBig);
        EditText distanceSmall = findViewById(R.id.distanceSmall);
        EditText consumption1 = findViewById(R.id.consumption1);
        EditText consumption2 = findViewById(R.id.consumption2);
        EditText consumption3 = findViewById(R.id.consumption3);

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
