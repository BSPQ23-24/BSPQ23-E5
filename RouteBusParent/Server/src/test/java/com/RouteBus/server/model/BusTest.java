package com.RouteBus.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusTest {
    private Bus bus;

    @Mock
    private Set<Route> mockRoutes;

    @Before
    public void setUp() {
        bus = new Bus("XYZ-1234", 45, "Mercedes", "Sprinter");
        bus.setRoutes(mockRoutes);
    }

    @Test
    public void testConstructorWithParameters() {
        assertEquals("XYZ-1234", bus.getLicensePlate());
        assertEquals(45, bus.getCapacity());
        assertEquals("Mercedes", bus.getMake());
        assertEquals("Sprinter", bus.getModel());
    }

    @Test
    public void testConstructorWithoutParameters() {
        Bus emptyBus = new Bus();
        assertNull(emptyBus.getLicensePlate());
        assertEquals(0, emptyBus.getCapacity());
        assertNull(emptyBus.getMake());
        assertNull(emptyBus.getModel());
        assertNull(emptyBus.getRoutes());
    }

    @Test
    public void testSetAndGetId() {
        bus.setId(10L);
        assertEquals(Long.valueOf(10), bus.getId());
    }

    @Test
    public void testSetAndGetLicensePlate() {
        bus.setLicensePlate("ABCD-1234");
        assertEquals("ABCD-1234", bus.getLicensePlate());
    }

    @Test
    public void testSetAndGetCapacity() {
        bus.setCapacity(50);
        assertEquals(50, bus.getCapacity());
    }

    @Test
    public void testSetAndGetMake() {
        bus.setMake("Ford");
        assertEquals("Ford", bus.getMake());
    }

    @Test
    public void testSetAndGetModel() {
        bus.setModel("Transit");
        assertEquals("Transit", bus.getModel());
    }

    @Test
    public void testSetAndGetRoutes() {
        Set<Route> newRoutes = new HashSet<>();
        Route route = new Route();
        newRoutes.add(route);
        bus.setRoutes(newRoutes);
        assertSame(newRoutes, bus.getRoutes());
        assertTrue(bus.getRoutes().contains(route));
    }

    @Test
    public void testInteractionWithRoutes() {
        Route mockRoute = mock(Route.class);
        bus.getRoutes().add(mockRoute);
        verify(mockRoutes).add(mockRoute);

        bus.getRoutes().remove(mockRoute);
        verify(mockRoutes).remove(mockRoute);

        bus.getRoutes().clear();
        verify(mockRoutes).clear();
    }
}
