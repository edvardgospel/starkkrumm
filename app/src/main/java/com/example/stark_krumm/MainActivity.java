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

    private RoadApi roadApi = NetworkModule.provideRoadApi(NetworkModule.provideRetrofit());

    //// SUBMIT ////
    private EditText roadNumber;
    private Spinner carNumber;
    private Spinner driverName;
    private EditText departure;
    private EditText arrival;
    private Spinner date;
    private EditText distanceBig;
    private EditText distanceSmall;
    private EditText consumption1;
    private EditText consumption2;
    private EditText consumption3;

    //// DELETE ////
    private Spinner dateDelete;
    private Spinner carNumberDelete;
    private EditText roadNumberDelete;

    //// UPLOAD ////
    private Spinner dateDrive;
    private Spinner carNumberDrive;

    //// DOT ////
    private TextView submitDot;
    private TextView deleteDot;
    private TextView uploadDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roadNumber = findViewById(R.id.roadNumber);
        carNumber = findViewById(R.id.carNumber);
        driverName = findViewById(R.id.driverName);
        departure = findViewById(R.id.departure);
        arrival = findViewById(R.id.arrival);
        date = findViewById(R.id.date);
        distanceBig = findViewById(R.id.distanceBig);
        distanceSmall = findViewById(R.id.distanceSmall);
        consumption1 = findViewById(R.id.consumption1);
        consumption2 = findViewById(R.id.consumption2);
        consumption3 = findViewById(R.id.consumption3);

        dateDelete = findViewById(R.id.dateDelete);
        carNumberDelete = findViewById(R.id.carNumberDelete);
        roadNumberDelete = findViewById(R.id.roadNumberDelete);

        dateDrive = findViewById(R.id.dateDrive);
        carNumberDrive = findViewById(R.id.carNumberDrive);

        submitDot = findViewById(R.id.successSubmit);
        deleteDot = findViewById(R.id.successDelete);
        uploadDot = findViewById(R.id.successUpload);
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

        return areFilled(roadNumberString, departureString,
                arrivalString, distanceBigString,
                distanceSmallString, consumption1String,
                consumption2String);
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
        if (consumption3.getText().toString().isEmpty()) {
            roadRequest.setConsumption3(0d);
        } else {
            roadRequest.setConsumption3(Double.valueOf(consumption3.getText().toString()));
        }
        return roadRequest;
    }

    private Callback<List<RoadResponse>> getCallback(TextView dot) {
        return new Callback<List<RoadResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<RoadResponse>> call, @NonNull Response<List<RoadResponse>> response) {
                if (response.isSuccessful()) {
                    setVisibility(dot, GREEN);
                    clearInputTexts();
                } else {
                    setVisibility(dot, RED);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RoadResponse>> call, @NonNull Throwable t) {
                setVisibility(dot, GRAY);
            }

            private void clearInputTexts() {
                roadNumber.getText().clear();
                departure.getText().clear();
                arrival.getText().clear();
                distanceBig.getText().clear();
                distanceSmall.getText().clear();
                consumption1.getText().clear();
                consumption2.getText().clear();
                consumption3.getText().clear();
                roadNumberDelete.getText().clear();
            }
        };
    }

    private void setVisibility(TextView dot, String color) {
        dot.setVisibility(View.VISIBLE);
        dot.setTextColor(Color.parseColor(color));
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
