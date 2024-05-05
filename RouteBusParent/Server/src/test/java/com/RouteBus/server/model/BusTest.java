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
        assertEquals("XYZ-1234", bus.getLicensePlate());
        assertEquals(45, bus.getCapacity());
        assertEquals("Mercedes", bus.getMake());
        assertEquals("Sprinter", bus.getModel());
        logger.debug("Test constructor with parameters passed succesfully");
    }

    @Test
    public void testConstructorWithoutParameters() {
        Bus emptyBus = new Bus();
        assertNull(emptyBus.getLicensePlate());
        assertEquals(0, emptyBus.getCapacity());
        assertNull(emptyBus.getMake());
        assertNull(emptyBus.getModel());
        assertNull(emptyBus.getRoutes());
        logger.debug("Test constructor without parameters passed succesfully");
    }
    
    @Test
    public void testSetAndGetLicensePlate() {
        bus.setLicensePlate("ABCD-1234");
        assertEquals("ABCD-1234", bus.getLicensePlate());
        logger.debug("Test setLicensePlate and getLicensePlate methods passed succesfully");
    }

    @Test
    public void testSetAndGetCapacity() {
        bus.setCapacity(50);
        assertEquals(50, bus.getCapacity());
        logger.debug("Test setCapacity and getCapacity methods passed succesfully");
    }

    @Test
    public void testSetAndGetMake() {
        bus.setMake("Ford");
        assertEquals("Ford", bus.getMake());
        logger.debug("Test setMake and getMake methods passed succesfully");        
    }

    @Test
    public void testSetAndGetModel() {
        bus.setModel("Transit");
        assertEquals("Transit", bus.getModel());
        logger.debug("Test setModel and getModel methods passed succesfully");
    }

    @Test
    public void testSetAndGetRoutes() {
        Set<Route> newRoutes = new HashSet<>();
        Route route = new Route();
        newRoutes.add(route);
        bus.setRoutes(newRoutes);
        assertSame(newRoutes, bus.getRoutes());
        assertTrue(bus.getRoutes().contains(route));
        logger.debug("Test setRoutes and getRoutes methods passed succesfully");    
    }

    @Test
    public void testHashCode() {
        Bus bus1 = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        Bus bus2 = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        
        assertEquals(bus1.hashCode(), bus2.hashCode());
        logger.debug("Test testHashCode passed successfully.");
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
        
        logger.debug("Test interaction with Routes passed succesfully");
    }
}
