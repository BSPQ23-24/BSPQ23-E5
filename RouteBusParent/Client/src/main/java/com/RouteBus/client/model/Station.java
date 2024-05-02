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

import org.apache.catalina.LifecycleState;

@Table
@Entity
public class Station {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    @ManyToMany(mappedBy = "stations",fetch = FetchType.EAGER)
    private Set<Route> routes;
    public Station(){
        
    }

    public Station( String name, String location, Set<Route> routes) {
        this.name = name;
        this.location = location;
        this.routes= routes;
        // Inicializar el atributo buses
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + 
                '}';
    }
    
    public boolean addRoute(Route route) {
    	return this.routes.add(route);
    }
}