package com.RouteBus.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class RouteTest {

    private Route route;
    private Route route1;

    @Before
    public void setUp() {
    	route1 = new Route();
        route = new Route(new HashSet<Station>(), new HashSet<Bus>(), 28);
    }
    
    @Test
    public void testGetAndSetId() {
        int id = 7;
        route.setId(id);
        assertEquals(id, route.getId());
    }

    @Test
    public void testGetAndSetStations() {
        Station station1 = new Station();
        Station station2 = new Station();
        route.setStations(Set.of(station1, station2));
        assertEquals(2, route.getStations().size());
        assertTrue(route.getStations().contains(station1));
        assertTrue(route.getStations().contains(station2));
    }

    @Test
    public void testGetAndSetBuses() {
        Bus bus1 = new Bus();
        Bus bus2 = new Bus();
        route.setBuses(Set.of(bus1, bus2));
        assertEquals(2, route.getBuses().size());
        assertTrue(route.getBuses().contains(bus1));
        assertTrue(route.getBuses().contains(bus2));
      
    }

    @Test
    public void testGetAndSetTotalDistance() {
        double totalDistance = 150.0;
        route.setTotalDistance(totalDistance);
        assertEquals(totalDistance, route.getTotalDistance(), 0.001);
    }

    @Test
    public void testAddStation() {
        Station station = new Station();
        assertTrue(route.addStation(station));
        assertEquals(1, route.getStations().size());
        assertTrue(route.getStations().contains(station));
    }

    @Test
    public void testAddBus() {
        Bus bus = new Bus();
        assertTrue(route.addBus(bus));
        assertEquals(1, route.getBuses().size());
        assertTrue(route.getBuses().contains(bus));
    }

    @Test
    public void testTotalDistance() {
        double totalDistance = 100.0;
        route.setTotalDistance(totalDistance);
        assertEquals(totalDistance, route.getTotalDistance(), 0.001);
    }

    @Test
    public void testToString() {
        Set<Station> stations = new HashSet<>();
        stations.add(new Station( "Intermodal", "Bilbao", null));
        stations.add(new Station("Avenida America", "Madrid", null));

        Set<Bus> buses = new HashSet<>();
        buses.add(new Bus(new HashSet<Route>(), "Bus 1", 55));
        buses.add(new Bus(new HashSet<Route>(), "Bus 2", 60));

        double totalDistance = 150.0;

        route = new Route(stations, buses, totalDistance);

        String expected = "Route{id=0, stations=" + stations.toString() + ", buses=" + buses.toString()
                + ", totalDistance=" + totalDistance + '}';
        assertEquals(expected, route.toString());
    }
}
