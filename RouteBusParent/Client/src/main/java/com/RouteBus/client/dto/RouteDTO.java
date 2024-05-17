package com.RouteBus.client.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class RouteDTO {

    private String name;
    private String startPoint;
    private String endPoint;
    private double totalDistance;
    private Set<StationDTO> stations = new HashSet<>();
    private Set<BusDTO> buses = new HashSet<>();
    private Set<ScheduleDTO> schedules = new HashSet<>();

    public RouteDTO() {
    }

    public RouteDTO(String name, String startPoint, String endPoint, double totalDistance, Set<StationDTO> stations, Set<BusDTO> buses) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalDistance = totalDistance;
        this.stations = stations;
        this.buses = buses;
    }

    public RouteDTO(String name, String startPoint, String endPoint, double totalDistance) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalDistance = totalDistance;
        this.stations = new HashSet<>();
        this.buses = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Set<StationDTO> getStations() {
        return stations;
    }

    public void setStations(Set<StationDTO> stations) {
        this.stations = stations;
    }

    public Set<BusDTO> getBuses() {
        return buses;
    }

    public void setBuses(Set<BusDTO> buses) {
        this.buses = buses;
    }

    public Set<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RouteDTO other = (RouteDTO) obj;
        return Objects.equals(name, other.name);
    }
}
