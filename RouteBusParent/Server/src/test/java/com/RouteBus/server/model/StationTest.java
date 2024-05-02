package com.RouteBus.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class StationTest {

    private Station station;

    @Mock
    private Set<Route> mockRoutes;

    @Before
    public void setUp() {
        station = new Station("Central", "123 Main St");
        station.setRoutes(mockRoutes);
    }

    @Test
    public void testConstructorAndProperties() {
        assertEquals("Central", station.getName());
        assertEquals("123 Main St", station.getLocation());
    }

    @Test
    public void testSetAndGetId() {
        station.setId(1L);
        assertEquals(Long.valueOf(1), station.getId());
    }

    @Test
    public void testSetAndGetName() {
        station.setName("East Station");
        assertEquals("East Station", station.getName());
    }

    @Test
    public void testSetAndGetLocation() {
        station.setLocation("456 Elm St");
        assertEquals("456 Elm St", station.getLocation());
    }

    @Test
    public void testSetAndGetRoutes() {
        Set<Route> newRoutes = new HashSet<>();
        Route mockRoute = new Route();
        newRoutes.add(mockRoute);
        station.setRoutes(newRoutes);

        assertEquals(newRoutes, station.getRoutes());
    }
    
    @Test
    public void testConstructorWithoutParameters() {
    	Station station = new Station();
    	assertNull(station.getId());
    	assertNull(station.getLocation());
    	assertNull(station.getName());
    	assertNull(station.getRoutes());
    }
}
