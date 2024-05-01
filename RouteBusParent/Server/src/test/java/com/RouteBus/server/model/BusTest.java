package com.RouteBus.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusTest {
    private Bus bus;
    private Set<Route> mockRoutes;

    @SuppressWarnings("unchecked")
	@BeforeEach
    void setUp() {
        bus = new Bus("XYZ-1234", 45, "Mercedes", "Sprinter");

        mockRoutes = Mockito.mock(Set.class);
        bus.setRoutes(mockRoutes);
    }

    @Test
    void testBusProperties() {
        assertEquals("XYZ-1234", bus.getLicensePlate());
        assertEquals(45, bus.getCapacity());
        assertEquals("Mercedes", bus.getMake());
        assertEquals("Sprinter", bus.getModel());
    }

    @Test
    void testSetRoutes() {
        Route mockRoute = mock(Route.class);
        HashSet<Route> routes = new HashSet<>();
        routes.add(mockRoute);
        bus.setRoutes(routes);

        assertTrue(bus.getRoutes().contains(mockRoute));
    }

    @Test
    void testInteractionWithRoutes() {
        bus.getRoutes().clear();
        verify(mockRoutes).clear();

        Route testRoute = new Route();
        bus.getRoutes().add(testRoute);
        verify(mockRoutes).add(testRoute);
    }
}
