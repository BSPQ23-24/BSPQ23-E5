package com.RouteBus.client.model;

import java.util.Set;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Table
@Entity
public class Bus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@ManyToMany(mappedBy = "buses", fetch = FetchType.EAGER)
    private Set<Route> routes;
    private String driver;
    private int capacity;

    public Bus(){
        
    }

    public Bus(Set<Route> routes, String driver, int capacity) {
        this.driver = driver;
        this.capacity = capacity; 
        this.routes= routes;
    }

    public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}

	// Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", driver='" + driver +
                ", capacity=" + capacity +
                '}';
    }
    
    public boolean addRoute(Route route) {
    	return this.routes.add(route);
    }

}