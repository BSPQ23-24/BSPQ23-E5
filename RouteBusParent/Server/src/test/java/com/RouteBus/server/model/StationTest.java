package com.RouteBus.server.model;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class StationTest {

    private final Logger logger = Logger.getLogger(StationTest.class);

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
        logger.debug("Test testConstructorAndProperties passed successfully.");
    }

    @Test
    public void testSetAndGetName() {
        station.setName("East Station");
        assertEquals("East Station", station.getName());
        logger.debug("Test testSetAndGetName passed successfully.");
    }

    @Test
    public void testSetAndGetLocation() {
        station.setLocation("456 Elm St");
        assertEquals("456 Elm St", station.getLocation());
        logger.debug("Test testSetAndGetLocation passed successfully.");
    }

    @Test
    public void testSetAndGetRoutes() {
        Set<Route> newRoutes = new HashSet<>();
        Route mockRoute = new Route();
        newRoutes.add(mockRoute);
        station.setRoutes(newRoutes);

        assertEquals(newRoutes, station.getRoutes());
        logger.debug("Test testSetAndGetRoutes passed successfully.");
    }

    @Test
    public void testHashCode() {
        Station station1 = new Station("Central", "123 Main St");
        Station station2 = new Station("Central", "456 Elm St");

        assertEquals(station1.hashCode(), station2.hashCode());
    }

    @Test
    public void testConstructorWithoutParameters() {
        Station station = new Station();
        assertNull(station.getLocation());
        assertNull(station.getName());
        assertNull(station.getRoutes());
        logger.debug("Test testConstructorWithoutParameters passed successfully.");
    }

    @Test
    public void testEqualsAndHashCode() {
        Station station1 = new Station("Central", "123 Main St");
        Station station2 = new Station("Central", "456 Elm St");

        Station station3 = new Station("East Station", "123 Main St");

        Object differentClassObject = new Object();

        assertEquals(station1, station2);
        assertEquals(station1.hashCode(), station2.hashCode());

        assertNotEquals(station1, station3);
        assertNotEquals(station1.hashCode(), station3.hashCode());

        assertNotEquals(station1, null);

        assertNotEquals(station1, differentClassObject);

        logger.debug("Test testEqualsAndHashCode passed successfully.");
    }

}
