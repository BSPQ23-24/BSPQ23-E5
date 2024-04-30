package com.RouteBus.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TicketTest {

    private Ticket ticket;
    private User user;

    @Before
    public void setUp() {
        user = new User();
        ticket = new Ticket();
        Ticket ticket2= new Ticket(user,"Albacete",15,new Date());
    }

    @Test
    public void testGetAndSetId() {
        assertTrue( ticket.getId()!=null);
    }

    @Test
    public void testGetAndSetClient() {
        ticket.setClient(user);
        assertEquals(user, ticket.getClient());
    }

    @Test
    public void testGetAndSetDestination() {
        String destination = "Zamora";
        ticket.setDestination(destination);
        assertEquals(destination, ticket.getDestination());
    }

    @Test
    public void testGetAndSetPrice() {
        double price = 60.0;
        ticket.setPrice(price);
        assertEquals(price, ticket.getPrice(), 0.001);
    }

    @Test
    public void testGetAndSetDate() {
        Date date = new Date();
        ticket.setDate(date);
        assertEquals(date, ticket.getDate());
    }

    @Test
    public void testToString() {
        ticket.setClient(user);
        ticket.setDestination("Destination");
        ticket.setPrice(60.0);
        Date date = new Date();
        ticket.setDate(date);

        assertNotNull(ticket.toString());
    }
}
