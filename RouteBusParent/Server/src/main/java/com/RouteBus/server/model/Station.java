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
}