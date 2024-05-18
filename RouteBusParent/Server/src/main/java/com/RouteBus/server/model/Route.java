package com.RouteBus.server.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "routes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Route {

    @Id
    private String name;
    private String startPoint;
    private String endPoint;
    private double totalDistance;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "route_stations",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    private Set<Station> stations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "route_buses",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    private Set<Bus> buses;
    
    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Schedule> schedules;

    public Route() {
    }

    
    
    public Route(String name, String startPoint, String endPoint, double totalDistance, Set<Station> stations, Set<Bus> buses) {
		this.name = name;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.totalDistance = totalDistance;
		this.stations = stations;
		this.buses = buses;
		this.schedules = new HashSet<Schedule>();
	}

	public Route(String name, String startPoint, String endPoint, double totalDistance) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.totalDistance = totalDistance;
        this.stations = new HashSet<Station>();
        this.buses = new HashSet<Bus>();
        this.schedules = new HashSet<Schedule>();
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

	public Set<Schedule> getSchedules() {
		return schedules;
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

	public void setSchedules(Set<Schedule> schedules) {
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
		Route other = (Route) obj;
		return Objects.equals(name, other.name);
	}
}