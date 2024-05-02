package com.RouteBus.server.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private int capacity;
    private String make;
    private String model;

    @ManyToMany(mappedBy = "buses")
    private Set<Route> routes;

    public Bus() {
    }

    public Bus(String licensePlate, int capacity, String make, String model) {
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.make = make;
        this.model = model;
    }

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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
}
