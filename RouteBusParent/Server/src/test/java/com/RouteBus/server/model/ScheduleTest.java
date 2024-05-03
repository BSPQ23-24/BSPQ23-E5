package com.RouteBus.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleTest {

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

        schedule = new Schedule(mockRoute, departureTime, arrivalTime, date);
        schedule.setTickets(mockTickets);
    }

    @Test
    public void testConstructorAndProperties() {
        assertEquals(mockRoute, schedule.getRoute());
        assertEquals(departureTime, schedule.getDepartureTime());
        assertEquals(arrivalTime, schedule.getArrivalTime());
        assertEquals(date, schedule.getDate());
    }

    @Test
    public void testSetAndGetId() {
        schedule.setId(1L);
        assertEquals(Long.valueOf(1), schedule.getId());
    }

    @Test
    public void testSetAndGetRoute() {
        Route newRoute = new Route();
        schedule.setRoute(newRoute);
        assertEquals(newRoute, schedule.getRoute());
    }

    @Test
    public void testSetAndGetDepartureTime() {
        Date newDepartureTime = new Date();
        newDepartureTime.setTime(1620007200L);
        schedule.setDepartureTime(newDepartureTime);
        assertEquals(newDepartureTime, schedule.getDepartureTime());
    }

    @Test
    public void testSetAndGetArrivalTime() {
        Date newArrivalTime = new Date();
        newArrivalTime.setTime(1620010800L);
        schedule.setArrivalTime(newArrivalTime);
        assertEquals(newArrivalTime, schedule.getArrivalTime());
    }

    @Test
    public void testSetAndGetDate() {
        Date newDate = new Date();
        newDate.setTime(1620090000L);
        schedule.setDate(newDate);
        assertEquals(newDate, schedule.getDate());
    }

    @Test
    public void testSetAndGetTickets() {
        List<Ticket> newTickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        newTickets.add(ticket);
        schedule.setTickets(newTickets);

        assertEquals(newTickets, schedule.getTickets());
    }
}
