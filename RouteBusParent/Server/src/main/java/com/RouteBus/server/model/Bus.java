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

}
