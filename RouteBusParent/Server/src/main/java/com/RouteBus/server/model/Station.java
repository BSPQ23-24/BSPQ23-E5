package com.RouteBus.server.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;

    @ManyToMany(mappedBy = "stations")
    private Set<Route> routes;

    public Station() {
    }

    public Station(String name, String location) {
        this.name = name;
        this.location = location;
    }

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Set<Route> getRoutes() {
		return routes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}
}