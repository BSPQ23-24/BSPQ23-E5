package com.RouteBus.server.model;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleTest {

    private static final Logger logger = Logger.getLogger(ScheduleTest.class);

    private Schedule schedule;

    @Mock
    private Route mockRoute;
    @Mock
    private List<Ticket> mockTickets;

    private Date departureTime;
    private Date arrivalTime;
    private Date date;

    @Before
    public void setUp() {
        departureTime = new Date();
        arrivalTime = new Date();
        date = new Date();

        // Set times for testing
        departureTime.setTime(1620000000L); // Some arbitrary timestamp
        arrivalTime.setTime(1620003600L); // Arbitrary, but after departure
        date.setTime(1620000000L); // The date of the schedule

        schedule = new Schedule(mockRoute, departureTime, arrivalTime);
        schedule.setTickets(mockTickets);
    }

    @Test
    public void testConstructorAndProperties() {
        assertEquals(mockRoute, schedule.getRoute());
        assertEquals(departureTime, schedule.getDepartureTime());
        assertEquals(arrivalTime, schedule.getArrivalTime());
        logger.debug("Test testConstructorAndProperties passed successfully.");
    }

    @Test
    public void testSetAndGetId() {
        String newId = "ABC123";
        schedule.setId(newId);
        assertEquals(newId, schedule.getId());
        logger.debug("Test testSetAndGetId passed successfully.");
    }

    @Test
    public void testSetAndGetRoute() {
        Route newRoute = new Route();
        schedule.setRoute(newRoute);
        assertEquals(newRoute, schedule.getRoute());
        logger.debug("Test testSetAndGetRoute passed successfully.");
    }

    @Test
    public void testSetAndGetDepartureTime() {
        Date newDepartureTime = new Date();
        newDepartureTime.setTime(1620007200L);
        schedule.setDepartureTime(newDepartureTime);
        assertEquals(newDepartureTime, schedule.getDepartureTime());
        logger.debug("Test testSetAndGetDepartureTime passed successfully.");
    }

    @Test
    public void testSetAndGetArrivalTime() {
        Date newArrivalTime = new Date();
        newArrivalTime.setTime(1620010800L);
        schedule.setArrivalTime(newArrivalTime);
        assertEquals(newArrivalTime, schedule.getArrivalTime());
        logger.debug("Test testSetAndGetArrivalTime passed successfully.");
    }

    @Test
    public void testSetAndGetTickets() {
        List<Ticket> newTickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        newTickets.add(ticket);
        schedule.setTickets(newTickets);

        assertEquals(newTickets, schedule.getTickets());
        logger.debug("Test testSetAndGetTickets passed successfully.");
    }

    @Test
    public void testConstructorWithoutParameters() {
        Schedule schedule = new Schedule();
        assertNull(schedule.getRoute());
        assertNull(schedule.getDepartureTime());
        assertNull(schedule.getArrivalTime());
        assertNull(schedule.getTickets());
        logger.debug("Test testConstructorWithoutParameters passed successfully.");
    }

    @Test
    public void testEqualsAndHashCode() {
        Schedule schedule1 = new Schedule();
        schedule1.setId("1");

        Schedule schedule2 = new Schedule();
        schedule2.setId("1");

        assertEquals(schedule1, schedule2);
        assertEquals(schedule1.hashCode(), schedule2.hashCode());

        Schedule schedule3 = new Schedule();
        schedule3.setId("2");

        assertNotEquals(schedule1, schedule3);
        assertNotEquals(schedule1.hashCode(), schedule3.hashCode());

        logger.debug("Test testEqualsAndHashCode passed successfully.");
    }
}
