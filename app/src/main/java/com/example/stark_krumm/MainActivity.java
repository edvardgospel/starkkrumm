package com.example.stark_krumm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stark_krumm.model.RoadRequest;
import com.example.stark_krumm.model.factory.RoadRequestFactory;
import com.example.stark_krumm.network.NetworkModule;
import com.example.stark_krumm.network.api.RoadApi;

public class MainActivity extends AppCompatActivity {

    private final RoadApi roadApi = NetworkModule.provideRoadApi(NetworkModule.provideRetrofit());
    private final RoadRequestFactory roadRequestFactory = new RoadRequestFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitButtonClicked(View view) {
        RoadRequest roadRequest = roadRequestFactory.createRoadRequest();
        roadApi.saveRoad(roadRequest);

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
}
