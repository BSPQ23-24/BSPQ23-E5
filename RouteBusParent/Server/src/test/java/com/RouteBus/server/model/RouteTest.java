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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class RouteTest {

    private final Logger logger = Logger.getLogger(RouteTest.class);

    private Route route;

    @Mock
    private Set<Station> mockStations;
    @Mock
    private Set<Bus> mockBuses;

    @Before
    public void setUp() {
        route = new Route("Route A", "Start Point", "End Point", 120.5);
        route.setStations(mockStations);
        route.setBuses(mockBuses);
    }

    @Test
    public void testConstructorAndProperties() {
        assertEquals("Route A", route.getName());
        assertEquals("Start Point", route.getStartPoint());
        assertEquals("End Point", route.getEndPoint());
        assertEquals(120.5, route.getTotalDistance(), 0.0);
        logger.debug("Test testConstructorAndProperties passed successfully.");
    }

    @Test
    public void testSetAndGetName() {
        route.setName("Route B");
        assertEquals("Route B", route.getName());
        logger.debug("Test testSetAndGetName passed successfully.");
    }

    @Test
    public void testSetAndGetStartPoint() {
        route.setStartPoint("New Start");
        assertEquals("New Start", route.getStartPoint());
        logger.debug("Test testSetAndGetStartPoint passed successfully.");
    }

    @Test
    public void testSetAndGetEndPoint() {
        route.setEndPoint("New End");
        assertEquals("New End", route.getEndPoint());
        logger.debug("Test testSetAndGetEndPoint passed successfully.");
    }

    @Test
    public void testSetAndGetTotalDistance() {
        route.setTotalDistance(150.0);
        assertEquals(150.0, route.getTotalDistance(), 0.0);
        logger.debug("Test testSetAndGetTotalDistance passed successfully.");
    }

    @Test
    public void testSetAndGetStations() {
        Set<Station> newStations = new HashSet<>();
        route.setStations(newStations);
        assertEquals(newStations, route.getStations());
        logger.debug("Test testSetAndGetStations passed successfully.");
    }

    @Test
    public void testSetAndGetBuses() {
        Set<Bus> newBuses = new HashSet<>();
        route.setBuses(newBuses);
        assertEquals(newBuses.size(), route.getBuses().size());
        assertTrue(route.getBuses().containsAll(newBuses));
        logger.debug("Test testSetAndGetBuses passed successfully.");
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Route route1 = new Route("Route A", "Start Point", "End Point", 120.5);
        Route route2 = new Route("Route A", "Start Point", "End Point", 120.5);
        Route route3 = new Route("Route B", "Start Point", "End Point", 120.5);

        assertEquals(route1, route2);
        assertEquals(route1.hashCode(), route2.hashCode());

        assertNotEquals(route1, route3);
        assertNotEquals(route1.hashCode(), route3.hashCode());

        assertNotEquals(route1, null);
        assertNotEquals(route1, new Object());

        logger.debug("Test testEqualsAndHashCode passed successfully.");
    }
}
