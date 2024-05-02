package com.RouteBus.server.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleTest {

    @InjectMocks
    private Schedule schedule;

    @Mock
    private Route mockRoute;
    
    @Mock
    private List<Ticket> mockTickets;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        Date now = new Date();
        schedule = new Schedule(mockRoute, now, new Date(now.getTime() + 10000), now);  
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetters() {
        assertEquals(mockRoute, schedule.getRoute());
        assertNotNull(schedule.getDepartureTime());
        assertNotNull(schedule.getArrivalTime());
        assertNotNull(schedule.getDate());
        assertEquals(schedule.getDepartureTime().getTime() + 10000, schedule.getArrivalTime().getTime());
    }

    @Test
    public void testSetters() {
        Route newRoute = mock(Route.class);
        schedule.setRoute(newRoute);
        assertEquals(newRoute, schedule.getRoute());

        Date newDepartureTime = new Date();
        schedule.setDepartureTime(newDepartureTime);
        assertEquals(newDepartureTime, schedule.getDepartureTime());

        Date newArrivalTime = new Date(newDepartureTime.getTime() + 20000);  
        schedule.setArrivalTime(newArrivalTime);
        assertEquals(newArrivalTime, schedule.getArrivalTime());

        Date newDate = new Date();
        schedule.setDate(newDate);
        assertEquals(newDate, schedule.getDate());

        List<Ticket> newTickets = new ArrayList<>();
        schedule.setTickets(newTickets);
        assertEquals(newTickets, schedule.getTickets());
    }
}
