package com.RouteBus.client.dto;

import java.util.Objects;
import java.util.Set;

public class StationDTO {

    private String name;
    private String location;
    private Set<RouteDTO> routes;
    public StationDTO() {
    }

    public StationDTO(String name, String location) {
        this.name = name;
        this.location = location;
    }

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Set<RouteDTO> getRoutes() {
		return routes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRoutes(Set<RouteDTO> routes) {
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
		StationDTO other = (StationDTO) obj;
		return Objects.equals(name, other.name);
	}
	
}