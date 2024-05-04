package com.RouteBus.server.model;

import javax.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    private String name;
    private String location;

    @ManyToMany(mappedBy = "stations", fetch = FetchType.EAGER)
    private Set<Route> routes;

    public Station() {
    }

    public Station(String name, String location) {
        this.name = name;
        this.location = location;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
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
		Station other = (Station) obj;
		return Objects.equals(name, other.name);
	}
	
}