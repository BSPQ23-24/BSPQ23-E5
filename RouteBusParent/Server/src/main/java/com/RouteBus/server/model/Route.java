package com.RouteBus.server.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String startPoint;
    private String endPoint;
    private double totalDistance;

    @ManyToMany
    @JoinTable(
        name = "route_stations",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    private Set<Station> stations;

    @ManyToMany
    @JoinTable(
        name = "route_buses",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    private Set<Bus> buses;

    public Route() {
    }

    public Route(String name, String startPoint, String endPoint, double totalDistance) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalDistance = totalDistance;
    }

	public Long getId() {
		return id;
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

	public Set<Station> getStations() {
		return stations;
	}

	public Set<Bus> getBuses() {
		return buses;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}

	public void setBuses(Set<Bus> buses) {
		this.buses = buses;
	}
	public boolean addBus(Bus bus) {
		return this.buses.add(bus);
	}
	public boolean addStation(Station station) {
		return this.stations.add(station);
	}
}