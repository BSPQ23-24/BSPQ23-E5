package com.RouteBus.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class StationTest {

    private Station station;
    private Route route;
    @Before
    public void setUp() {
        station = new Station("Intermodal", "Bilbao", new HashSet<Route>());
        route = new Route(new HashSet<Station>(), new HashSet<Bus>(), 28);
    }

    @Test
    public void testGetAndSetId() {
        int id = 2;
        station.setId(id);
        assertEquals(id, station.getId());
    }

    @Test
    public void testGetAndSetName() {
        String name = "Intermodal";
        station.setName(name);
        assertEquals(name, station.getName());
    }

    @Test
    public void testGetAndSetLocation() {
        String location = "Bilbao";
        station.setLocation(location);
        assertEquals(location, station.getLocation());
    }

    @Test
    public void testToString() {
        String name = "Intermodal";
        String location = "Bilbao";
        station = new Station(name, location, null);

        String expected = "Station{id=" + station.getId() + ", name='" + name + "', location='" + location + "}";
        assertEquals(expected, station.toString());
    }

    @Test
    public void testAddRoute() {
        Route route = new Route();
        assertTrue(station.addRoute(route));
    }
}
