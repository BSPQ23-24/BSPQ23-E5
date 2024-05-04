package com.RouteBus.client.dto;

import java.util.HashSet;
import java.util.Objects;
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

    public RouteDTO(String name, String startPoint, String endPoint, double totalDistance, Set<StationDTO> stations,
			Set<BusDTO> buses) {
		super();
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
        this.stations = new HashSet<StationDTO>();
        this.buses = new HashSet<BusDTO>();
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

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
		if (getClass() != obj.getClass())
			return false;
		RouteDTO other = (RouteDTO) obj;
		return Objects.equals(name, other.name);
	}
}