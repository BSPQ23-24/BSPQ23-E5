package com.RouteBus.server.information;

import java.util.ArrayList;
@Table
@Entity
public class Route {
	@Id
    private int id;
	@ManyToMany(mappedby="routes")
    private ArrayList<Station> RouteStations;
	@ManyToMany(mappedby="stations")
    private ArrayList<Bus> buses;
    private double totalDistance; 

    public Route(){
        
    }

    public Route(int id, ArrayList<Station> stations, ArrayList<Bus> buses, double totalDistance) {
        this.id = id;
        this.RouteStations = stations;
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
        return RouteStations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.RouteStations = stations;
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
                ", stations=" + RouteStations +
                ", buses=" + buses +
                ", totalDistance=" + totalDistance +
                '}';
    }
}