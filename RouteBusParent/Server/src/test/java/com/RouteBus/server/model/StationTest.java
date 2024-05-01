package com.RouteBus.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashSet;
import java.util.Set;

public class StationTest {

    @InjectMocks
    private Station station;

    @Mock
    private Set<Route> mockRoutes;

    @BeforeEach
    public void setUp() {
        station = new Station("Central", "Downtown");
        station.setRoutes(mockRoutes);  
    }

    @Test
    public void testStationCreation() {
        Station newStation = new Station("New Station", "New Location");
        assertEquals("New Station", newStation.getName());
        assertEquals("New Location", newStation.getLocation());
        newStation.setRoutes(new HashSet<>());  
        assertEquals(0, newStation.getRoutes().size());
    }

    @Test
    public void testGettersAndSetters() {
        station.setName("East Side");
        assertEquals("East Side", station.getName());

        station.setLocation("Uptown");
        assertEquals("Uptown", station.getLocation());

        Set<Route> newRoutes = new HashSet<>();
        station.setRoutes(newRoutes);
        assertEquals(newRoutes, station.getRoutes());
    }

    @Test
    public void testInteractionWithRoutes() {
        station.getRoutes();
        verify(mockRoutes, times(1)).iterator();  
        
        Route mockRoute = mock(Route.class);
        station.getRoutes().add(mockRoute);
        verify(mockRoutes).add(mockRoute); 
        station.getRoutes().remove(mockRoute);
        verify(mockRoutes).remove(mockRoute);  
    }
}
