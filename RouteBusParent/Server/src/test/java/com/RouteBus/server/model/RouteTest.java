package com.RouteBus.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.*;

import java.util.HashSet;
import java.util.Set;

public class RouteTest {

    @InjectMocks
    private Route route;

    private Set<Station> mockStations;
    private Set<Bus> mockBuses;

    private AutoCloseable closeable;

    @SuppressWarnings("unchecked")
	@BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockStations = mock(Set.class);
        mockBuses = mock(Set.class);
        
        route = new Route("Route 66", "Start City", "End City", 100.0);
        route.setStations(mockStations);
        route.setBuses(mockBuses);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("Route 66", route.getName());
        assertEquals("Start City", route.getStartPoint());
        assertEquals("End City", route.getEndPoint());
        assertEquals(100.0, route.getTotalDistance(), 0.01);

        route.setName("New Route");
        route.setStartPoint("New Start");
        route.setEndPoint("New End");
        route.setTotalDistance(150.0);

        assertEquals("New Route", route.getName());
        assertEquals("New Start", route.getStartPoint());
        assertEquals("New End", route.getEndPoint());
        assertEquals(150.0, route.getTotalDistance(), 0.01);
    }

    @Test
    public void testSetStations() {
        Set<Station> anotherSet = new HashSet<>();
        route.setStations(anotherSet);
        assertEquals(anotherSet, route.getStations());
        verify(mockStations, never()).add(any(Station.class));  
    }

    @Test
    public void testSetBuses() {
        Set<Bus> anotherSet = new HashSet<>();
        route.setBuses(anotherSet);
        assertEquals(anotherSet, route.getBuses());
        verify(mockBuses, never()).add(any(Bus.class));   
    }
}
