package com.example.stark_krumm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        //roadApi.saveRoad();
    }

    public void deleteButtonClicked(View view) {
    }

    public void uploadButtonClicked(View view) {
    }
}
