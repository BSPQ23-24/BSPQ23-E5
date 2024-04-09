package com.RouteBus.server.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



public class Bus {

    private int id;
    private List<Station> stations;
    private String driver;
    private int capacity;

    public Bus(){
        
    }

    public Bus(int id, List<Station> stations, String driver, int capacity) {
        this.id = id;
        this.stations = stations;
        this.driver = driver;
        this.capacity = capacity; 
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", stations=" + stations +
                ", driver='" + driver +
                ", capacity=" + capacity +
                '}';
    }

}