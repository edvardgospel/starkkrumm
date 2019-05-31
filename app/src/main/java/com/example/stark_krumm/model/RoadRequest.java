package com.example.stark_krumm.model;

public class RoadRequest {
    private Integer roadNumber;
    private Integer carNumber;
    private String driverName;
    private String departure;
    private String arrival;
    private String date;
    private Integer distanceBig;
    private Integer distanceSmall;
    private Double consumption1;
    private Double consumption2;
    private Double consumption3;

    public Integer getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(Integer roadNumber) {
        this.roadNumber = roadNumber;
    }

    public Integer getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDistanceBig() {
        return distanceBig;
    }

    public void setDistanceBig(Integer distanceBig) {
        this.distanceBig = distanceBig;
    }

    public Integer getDistanceSmall() {
        return distanceSmall;
    }

    public void setDistanceSmall(Integer distanceSmall) {
        this.distanceSmall = distanceSmall;
    }

    public Double getConsumption1() {
        return consumption1;
    }

    public void setConsumption1(Double consumption1) {
        this.consumption1 = consumption1;
    }

    public Double getConsumption2() {
        return consumption2;
    }

    public void setConsumption2(Double consumption2) {
        this.consumption2 = consumption2;
    }

    public Double getConsumption3() {
        return consumption3;
    }

    public void setConsumption3(Double consumption3) {
        this.consumption3 = consumption3;
    }
}
