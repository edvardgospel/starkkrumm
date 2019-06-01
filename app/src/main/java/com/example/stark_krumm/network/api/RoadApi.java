package com.example.stark_krumm.network.api;

import com.example.stark_krumm.model.RoadRequest;
import com.example.stark_krumm.model.RoadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RoadApi {

    @GET("/road")
    Call<List<RoadResponse>> getRoads(@Query("date") String date,
                                      @Query("carNumber") Integer carNumber);

    @POST("/road")
    Call<List<RoadResponse>> saveRoad(@Body RoadRequest roadRequest);

    @DELETE("/road")
    Call<List<RoadResponse>> deleteRoad(@Query("date") String date,
                                        @Query("carNumber") Integer carNumber,
                                        @Query("roadNumber") Integer roadNumber);

    @POST("/drive/road")
    Call<List<RoadResponse>> uploadRoad(@Query("date") String date,
                                        @Query("carNumber") Integer carNumber);
}
