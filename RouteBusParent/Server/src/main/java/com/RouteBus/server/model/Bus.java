package com.RouteBus.server.model;

import javax.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    private String licensePlate;
    private int capacity;
    private String make;
    private String model;

    @ManyToMany(mappedBy = "buses", fetch = FetchType.EAGER)
    private Set<Route> routes;

    public Bus() {
    }

    public Bus(String licensePlate, int capacity, String make, String model) {
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.make = make;
        this.model = model;
        this.routes = null;
    }
    
	public Bus(String licensePlate, int capacity, String make, String model, Set<Route> routes) {
		super();
		this.licensePlate = licensePlate;
		this.capacity = capacity;
		this.make = make;
		this.model = model;
		this.routes = routes;
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

	public Set<Route> getRoutes() {
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

	public void setRoutes(Set<Route> routes) {
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
		Bus other = (Bus) obj;
		return Objects.equals(licensePlate, other.licensePlate);
	}
}
