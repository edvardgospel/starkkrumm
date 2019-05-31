package com.example.stark_krumm.model;

public class RoadResponse {
    private Integer roadNumber;
    private String driverName;
    private String departure;
    private String arrival;
    private Integer distance;
    private Double consumption;

    public Integer getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(Integer roadNumber) {
        this.roadNumber = roadNumber;
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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}
