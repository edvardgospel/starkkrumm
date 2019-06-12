package com.example.stark_krumm.network;

import com.example.stark_krumm.network.api.RoadApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {
    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(NetworkConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RoadApi provideRoadApi(Retrofit retrofit) {
        return retrofit.create(RoadApi.class);
    }
}
