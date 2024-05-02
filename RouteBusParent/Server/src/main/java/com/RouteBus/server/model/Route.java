package com.RouteBus.server.model;

import java.util.Set;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Table
@Entity
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			  name = "RouteBuses", 
			  joinColumns = @JoinColumn(name = "route_id"), 
			  inverseJoinColumns = @JoinColumn(name = "bus_id"))
    private Set<Station> stations;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			  name = "RouteStations", 
			  joinColumns = @JoinColumn(name = "route_id"), 
			  inverseJoinColumns = @JoinColumn(name = "station_id"))
    private Set<Bus> buses;
    private double totalDistance; 

    public Route(){
        
    }

    public Route( Set<Station> stations, Set<Bus> buses, double totalDistance) {
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

    public Set<Station> getStations() {
        return stations;
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    public Set<Bus> getBuses() {
        return buses;
    }

    public void setBuses(Set<Bus> buses) {
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
    
    public boolean addStation(Station station) {
    	return this.stations.add(station);
    }
    public boolean addBus(Bus bus) {
    	return this.buses.add(bus);
    }
}