package com.example.stark_krumm;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.stark_krumm.model.RoadRequest;
import com.example.stark_krumm.model.RoadResponse;
import com.example.stark_krumm.network.NetworkModule;
import com.example.stark_krumm.network.api.RoadApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static java.util.Arrays.stream;

public class MainActivity extends AppCompatActivity {
    private static final String GREEN = "#4F8A10";
    private static final String ORANGE = "#FFA500";
    private static final String RED = "#D8000C";
    private static final String GRAY = "#CCCCCC";

    private static final RoadApi roadApi = NetworkModule.provideRoadApi(NetworkModule.provideRetrofit());

    private int px;
    private int check;

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

    //// TABLE ////
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = this.getResources();
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, resources.getDisplayMetrics());
        check = 0;
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

        table = findViewById(R.id.table);

        driveSpinnersChangeListener();
    }

    public void submitButtonClicked(View view) {
        if (allDataAreFilled()) {
            roadApi.saveRoad(createRoadRequest()).enqueue(getCallback(submitDot));
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
                    if (carNumber.getSelectedItem().equals(carNumberDrive.getSelectedItem()) &&
                            date.getSelectedItem().equals(dateDrive.getSelectedItem())) {
                        clearPreviousDataFromTable();
                        assert response.body() != null;
                        if (!response.body().isEmpty()) {
                            fillTable(response.body());
                        }
                    }
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

    private void driveSpinnersChangeListener() {
        spinnerChangeListener(dateDrive);
        spinnerChangeListener(carNumberDrive);
    }

    private void spinnerChangeListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (check++ > 0) {
                    String date = dateDrive.getSelectedItem().toString();
                    Integer carNumber = Integer.valueOf(carNumberDrive.getSelectedItem().toString().substring(3, 5));
                    roadApi.getRoads(date, carNumber).enqueue(new Callback<List<RoadResponse>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<RoadResponse>> call, @NonNull Response<List<RoadResponse>> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                clearPreviousDataFromTable();
                                if (!response.body().isEmpty()) {
                                    fillTable(response.body());
                                }
                            } else {
                                setVisibility(uploadDot, RED);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<RoadResponse>> call, @NonNull Throwable t) {
                            setVisibility(uploadDot, GRAY);
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private void clearPreviousDataFromTable() {
        while (table.getChildCount() > 1) {
            table.removeView(table.getChildAt(table.getChildCount() - 1));
        }
    }

    private void fillTable(List<RoadResponse> roads) {
        for (RoadResponse road : roads) {
            TableRow row = new TableRow(this);
            List<TextView> textViews = createTextViews(road);
            for (TextView textView : textViews) {
                row.addView(textView);
            }
            table.addView(row);
        }
    }

    @SuppressLint("SetTextI18n")
    private List<TextView> createTextViews(RoadResponse road) {
        List<TextView> textViews = new ArrayList<>();
        LinearLayout.LayoutParams params = new TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1);
        params.setMargins(0, 0, px, px);

        TextView textView = new TextView(this);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setText(road.getRoadNumber().toString());
        textViews.add(textView);

        TextView textView1 = new TextView(this);
        textView1.setLayoutParams(params);
        textView1.setBackgroundColor(Color.WHITE);
        textView1.setGravity(Gravity.CENTER);
        if (road.getDriverName().length() > 10) {
            textView1.setText(road.getDriverName().substring(0, 9) + "..");
        } else {
            textView1.setText(road.getDriverName());
        }
        textViews.add(textView1);

        TextView textView2 = new TextView(this);
        textView2.setLayoutParams(params);
        textView2.setBackgroundColor(Color.WHITE);
        textView2.setGravity(Gravity.CENTER);
        textView2.setText(road.getDeparture());
        textViews.add(textView2);

        TextView textView3 = new TextView(this);
        textView3.setLayoutParams(params);
        textView3.setBackgroundColor(Color.WHITE);
        textView3.setGravity(Gravity.CENTER);
        textView3.setText(road.getArrival());
        textViews.add(textView3);

        TextView textView4 = new TextView(this);
        textView4.setLayoutParams(params);
        textView4.setBackgroundColor(Color.WHITE);
        textView4.setGravity(Gravity.CENTER);
        textView4.setText(road.getDistance().toString());
        textViews.add(textView4);

        TextView textView5 = new TextView(this);
        textView5.setLayoutParams(params);
        textView5.setBackgroundColor(Color.WHITE);
        textView5.setGravity(Gravity.CENTER);
        textView5.setText(road.getConsumption().toString());
        textViews.add(textView5);

        return textViews;
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
