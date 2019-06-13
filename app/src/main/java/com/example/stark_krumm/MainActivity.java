package com.example.stark_krumm;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
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

import static java.util.Arrays.stream;

public class MainActivity extends AppCompatActivity {
    public static final String GREEN = "#4F8A10";
    public static final String ORANGE = "#FFA500";
    public static final String RED = "#D8000C";
    public static final String GRAY = "#CCCCCC";

    //// SUBMIT ////
    private RoadApi roadApi = NetworkModule.provideRoadApi(NetworkModule.provideRetrofit());
    private final EditText roadNumber = findViewById(R.id.roadNumber);
    private final Spinner carNumber = findViewById(R.id.carNumber);
    private final Spinner driverName = findViewById(R.id.driverName);
    private final EditText departure = findViewById(R.id.departure);
    private final EditText arrival = findViewById(R.id.arrival);
    private final Spinner date = findViewById(R.id.date);
    private final EditText distanceBig = findViewById(R.id.distanceBig);
    private final EditText distanceSmall = findViewById(R.id.distanceSmall);
    private final EditText consumption1 = findViewById(R.id.consumption1);
    private final EditText consumption2 = findViewById(R.id.consumption2);
    private final EditText consumption3 = findViewById(R.id.consumption3);

    //// DELETE ////
    private final Spinner dateDelete = findViewById(R.id.dateDelete);
    private final Spinner carNumberDelete = findViewById(R.id.carNumberDelete);
    private final TextView roadNumberDelete = findViewById(R.id.roadNumberDelete);

    //// UPLOAD ////
    private final Spinner dateDrive = findViewById(R.id.dateDrive);
    private final Spinner carNumberDrive = findViewById(R.id.carNumberDrive);

    //// DOT ////
    private final TextView submitDot = findViewById(R.id.successSubmit);
    private final TextView deleteDot = findViewById(R.id.successDelete);
    private final TextView uploadDot = findViewById(R.id.successUpload);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitButtonClicked(View view) {
        if (allDataAreFilled()) {
            RoadRequest roadRequest = createRoadRequest();
            System.out.println(roadRequest.toString());
            roadApi.saveRoad(roadRequest).enqueue(getCallback(submitDot));
        } else {
            setVisibility(submitDot, ORANGE);
        }
    }

    public void deleteButtonClicked(View view) {
        String roadNumber = roadNumberDelete.getText().toString();
        if (!roadNumber.isEmpty()) {
            String date = dateDelete.getSelectedItem().toString();
            Integer carNumber = Integer.valueOf(carNumberDelete.getSelectedItem().toString().substring(3, 5));
            roadApi.deleteRoad(date, carNumber, Integer.valueOf(roadNumber)).enqueue(getCallback(deleteDot));
        } else {
            setVisibility(deleteDot, ORANGE);
        }
    }

    public void uploadButtonClicked(View view) {
        String date = dateDrive.getSelectedItem().toString();
        Integer carNumber = Integer.valueOf(carNumberDrive.getSelectedItem().toString().substring(3, 5));
        roadApi.uploadRoad(date, carNumber).enqueue(getCallback(uploadDot));
    }

    private boolean allDataAreFilled() {
        String roadNumberString = roadNumber.getText().toString();
        String departureString = departure.getText().toString();
        String arrivalString = arrival.getText().toString();
        String distanceBigString = distanceBig.getText().toString();
        String distanceSmallString = distanceSmall.getText().toString();
        String consumption1String = consumption1.getText().toString();
        String consumption2String = consumption2.getText().toString();
        String consumption3String = consumption3.getText().toString();

        return areFilled(roadNumberString, departureString,
                arrivalString, distanceBigString,
                distanceSmallString, consumption1String,
                consumption2String, consumption3String);
    }

    private boolean areFilled(String... values) {
        return stream(values).noneMatch(String::isEmpty);

    }

    private RoadRequest createRoadRequest() {
        RoadRequest roadRequest = new RoadRequest();
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

    private Callback<List<RoadResponse>> getCallback(TextView dot) {
        return new Callback<List<RoadResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<RoadResponse>> call, @NonNull Response<List<RoadResponse>> response) {
                if (response.isSuccessful()) {
                    setVisibility(dot, GREEN);
                } else {
                    setVisibility(dot, RED);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RoadResponse>> call, @NonNull Throwable t) {
                setVisibility(dot, GRAY);
            }
        };
    }

    private void setVisibility(TextView dot, String color) {
        submitDot.setVisibility(View.VISIBLE);
        submitDot.setTextColor(Color.parseColor(color));
        resetVisibility(dot);
    }

    private void resetVisibility(TextView dot) {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                dot.setVisibility(View.INVISIBLE);
            }
        }.start();
    }
}
