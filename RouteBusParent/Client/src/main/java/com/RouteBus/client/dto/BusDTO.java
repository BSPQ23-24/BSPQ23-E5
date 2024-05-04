package com.RouteBus.client.dto;

import java.util.Objects;
import java.util.Set;

public class BusDTO {

    private String licensePlate;
    private int capacity;
    private String make;
    private String model;
    private Set<RouteDTO> routes;

    public BusDTO() {
    }

    public BusDTO(String licensePlate, int capacity, String make, String model) {
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.make = make;
        this.model = model;
    }

	public String getLicensePlate() {
		return licensePlate;
	}

	public int getCapacity() {
		return capacity;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public Set<RouteDTO> getRoutes() {
		return routes;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setRoutes(Set<RouteDTO> routes) {
		this.routes = routes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(licensePlate);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
		if (getClass() != obj.getClass())
			return false;
		BusDTO other = (BusDTO) obj;
		return Objects.equals(licensePlate, other.licensePlate);
	}
	
	
}
