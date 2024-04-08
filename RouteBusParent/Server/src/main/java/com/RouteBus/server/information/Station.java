package com.RouteBus.server.information;
import java.util.List;

public class Station {
    private int id;
    private String name;
    private String location;
    private List<Bus> buses; // Nuevo atributo para la lista de buses

    public Station(){
        
    }

    public Station(int id, String name, String location, List<Bus> buses) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.buses = buses; // Inicializar el atributo buses
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

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", buses=" + buses +
                '}';
    }
}