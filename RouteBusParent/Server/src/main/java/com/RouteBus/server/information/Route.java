package com.RouteBus.server.information;

import java.util.ArrayList;

public class Route {
    private int id;
    private ArrayList<Station> stations;
    private ArrayList<Bus> buses;
    private double totalDistance; 

    public Route(){
        
    }

    public Route(int id, ArrayList<Station> stations, ArrayList<Bus> buses, double totalDistance) {
        this.id = id;
        this.stations = stations;
        this.buses = buses;
        this.totalDistance = totalDistance;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public ArrayList<Bus> getBuses() {
        return buses;
    }

    public void setBuses(ArrayList<Bus> buses) {
        this.buses = buses;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", stations=" + stations +
                ", buses=" + buses +
                ", totalDistance=" + totalDistance +
                '}';
    }
}