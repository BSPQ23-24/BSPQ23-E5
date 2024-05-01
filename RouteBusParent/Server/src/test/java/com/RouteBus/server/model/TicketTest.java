package com.RouteBus.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class TicketTest {

    @InjectMocks
    private Ticket ticket;

    @Mock
    private User mockUser;

    @Mock
    private Schedule mockSchedule;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket(mockUser, 25, 199.99, TicketStatus.RESERVED, mockSchedule);
    }

    @Test
    public void testGettersAndSetters() {
        // Testing getters
        assertEquals(mockUser, ticket.getUser());
        assertEquals(25, ticket.getSeatNumber());
        assertEquals(199.99, ticket.getPrice());
        assertEquals(TicketStatus.RESERVED, ticket.getStatus());
        assertEquals(mockSchedule, ticket.getSchedule());

        // Testing setters
        User newUser = mock(User.class);
        Schedule newSchedule = mock(Schedule.class);
        ticket.setUser(newUser);
        assertEquals(newUser, ticket.getUser());

        ticket.setSeatNumber(30);
        assertEquals(30, ticket.getSeatNumber());

        ticket.setPrice(299.99);
        assertEquals(299.99, ticket.getPrice());

        ticket.setStatus(TicketStatus.PURCHASED);
        assertEquals(TicketStatus.PURCHASED, ticket.getStatus());

        ticket.setSchedule(newSchedule);
        assertEquals(newSchedule, ticket.getSchedule());
    }

    @Test
    public void testUserInteraction() {
        ticket.setUser(mockUser);
        verifyNoMoreInteractions(mockUser);
        User fetchedUser = ticket.getUser();
        assertEquals(mockUser, fetchedUser);
    }

    @Test
    public void testScheduleInteraction() {
        ticket.setSchedule(mockSchedule);
        verifyNoMoreInteractions(mockSchedule); 
        Schedule fetchedSchedule = ticket.getSchedule();
        assertEquals(mockSchedule, fetchedSchedule);
    }
}
