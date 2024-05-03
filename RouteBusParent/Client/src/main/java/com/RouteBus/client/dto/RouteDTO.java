package com.RouteBus.client.dto;

import java.util.Set;

public class RouteDTO {

    private String name;
    private String startPoint;
    private String endPoint;
    private double totalDistance;
    private Set<StationDTO> stations;
    private Set<BusDTO> buses;

    public RouteDTO() {
    }

    public RouteDTO(String name, String startPoint, String endPoint, double totalDistance) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalDistance = totalDistance;
    }

	public String getName() {
		return name;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public Set<StationDTO> getStations() {
		return stations;
	}

	public Set<BusDTO> getBuses() {
		return buses;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public void setStations(Set<StationDTO> stations) {
		this.stations = stations;
	}

	public void setBuses(Set<BusDTO> buses) {
		this.buses = buses;
	}
}