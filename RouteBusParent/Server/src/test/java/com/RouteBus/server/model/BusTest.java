package com.RouteBus.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusTest {
    private static final Logger logger = Logger.getLogger(BusTest.class);

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
        logger.debug("Testing constructor with parameters");
        assertEquals("XYZ-1234", bus.getLicensePlate());
        assertEquals(45, bus.getCapacity());
        assertEquals("Mercedes", bus.getMake());
        assertEquals("Sprinter", bus.getModel());
    }

    @Test
    public void testConstructorWithoutParameters() {
        logger.debug("Testing constructor without parameters");
        Bus emptyBus = new Bus();
        assertNull(emptyBus.getLicensePlate());
        assertEquals(0, emptyBus.getCapacity());
        assertNull(emptyBus.getMake());
        assertNull(emptyBus.getModel());
        assertNull(emptyBus.getRoutes());
    }

    @Test
    public void testSetAndGetId() {
        logger.debug("Testing setId and getId methods");
        bus.setId(10L);
        assertEquals(Long.valueOf(10), bus.getId());
    }

    @Test
    public void testSetAndGetLicensePlate() {
        logger.debug("Testing setLicensePlate and getLicensePlate methods");
        bus.setLicensePlate("ABCD-1234");
        assertEquals("ABCD-1234", bus.getLicensePlate());
    }

    @Test
    public void testSetAndGetCapacity() {
        logger.debug("Testing setCapacity and getCapacity methods");
        bus.setCapacity(50);
        assertEquals(50, bus.getCapacity());
    }

    @Test
    public void testSetAndGetMake() {
        logger.debug("Testing setMake and getMake methods");
        bus.setMake("Ford");
        assertEquals("Ford", bus.getMake());
    }

    @Test
    public void testSetAndGetModel() {
        logger.debug("Testing setModel and getModel methods");
        bus.setModel("Transit");
        assertEquals("Transit", bus.getModel());
    }

    @Test
    public void testSetAndGetRoutes() {
        logger.debug("Testing setRoutes and getRoutes methods");
        Set<Route> newRoutes = new HashSet<>();
        Route route = new Route();
        newRoutes.add(route);
        bus.setRoutes(newRoutes);
        assertSame(newRoutes, bus.getRoutes());
        assertTrue(bus.getRoutes().contains(route));
    }

    @Test
    public void testInteractionWithRoutes() {
        logger.debug("Testing interaction with Routes");
        Route mockRoute = mock(Route.class);
        bus.getRoutes().add(mockRoute);
        verify(mockRoutes).add(mockRoute);

        bus.getRoutes().remove(mockRoute);
        verify(mockRoutes).remove(mockRoute);

        bus.getRoutes().clear();
        verify(mockRoutes).clear();
    }
}
