package com.RouteBus.server.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}