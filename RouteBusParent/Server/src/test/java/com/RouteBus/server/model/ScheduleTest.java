package com.RouteBus.server.model;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleTest {

    private final Logger logger = Logger.getLogger(ScheduleTest.class);

    private Schedule schedule;

    @Mock
    private Route mockRoute;
    @Mock
    private Set<Ticket> mockTickets;

    private Date departureTime;
    private Date arrivalTime;
    private Date date;

    @Before
    public void setUp() {
        departureTime = new Date();
        arrivalTime = new Date();
        date = new Date();

        departureTime.setTime(1620000000L);
        arrivalTime.setTime(1620003600L);
        date.setTime(1620000000L);
        
        when(mockRoute.getName()).thenReturn("MockRouteName");
        
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
        Set<Ticket> newTickets = new HashSet<>();
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
        Schedule schedule1 = new Schedule("1", mockRoute, departureTime, arrivalTime, mockTickets);
        Schedule schedule2 = new Schedule("1", mockRoute, new Date(departureTime.getTime() + 10000), new Date(arrivalTime.getTime() + 10000), mockTickets);

        Schedule schedule3 = new Schedule("2", mockRoute, departureTime, arrivalTime, mockTickets);

        Schedule scheduleNull = null;

        Object differentClassObject = new Object();

        assertEquals(schedule1, schedule2);
        assertEquals(schedule1.hashCode(), schedule2.hashCode());

        assertNotEquals(schedule1, schedule3);
        assertNotEquals(schedule1.hashCode(), schedule3.hashCode());

        assertNotEquals(schedule1, scheduleNull);

        assertNotEquals(schedule1, differentClassObject);

        logger.debug("Test testEqualsAndHashCode passed successfully.");
    }

}
