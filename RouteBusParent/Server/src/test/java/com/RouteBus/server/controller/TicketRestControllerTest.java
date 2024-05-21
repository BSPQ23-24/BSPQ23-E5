package com.RouteBus.server.controller;

import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.TicketService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketRestControllerTest {

    private static final Logger logger = Logger.getLogger(TicketRestControllerTest.class);

    @Mock
    private TicketService ticketServiceMock;

    @InjectMocks
    private TicketRestController ticketController;

    @Test
    public void testGetAllTickets() {
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        when(ticketServiceMock.getAllTickets()).thenReturn(tickets);

        ResponseEntity<Set<Ticket>> response = ticketController.getAllTickets();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
        verify(ticketServiceMock, times(1)).getAllTickets();
        logger.debug("Test testGetAllTickets passed successfully.");
    }

    @Test
    public void testGetTicketById_TicketExists() {
        String ticketId = "1";
        Ticket ticket = new Ticket();
        when(ticketServiceMock.getTicketById(ticketId)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketServiceMock, times(1)).getTicketById(ticketId);
        logger.debug("Test testGetTicketById_TicketExists passed successfully.");
    }

    @Test
    public void testGetTicketById_TicketNotExists() {
        String ticketId = "1";
        when(ticketServiceMock.getTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ticketServiceMock, times(1)).getTicketById(ticketId);
        logger.debug("Test testGetTicketById_TicketNotExists passed successfully.");
    }

    @Test
    public void testCreateTicket_Success() {
        Ticket ticket = new Ticket();
        when(ticketServiceMock.createTicket(ticket)).thenReturn(TicketService.TicketServiceResult.SUCCESS);

        ResponseEntity<String> response = ticketController.createTicket(ticket);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket created successfully.", response.getBody());
        verify(ticketServiceMock, times(1)).createTicket(ticket);
        logger.debug("Test testCreateTicket_Success passed successfully.");
    }

    @Test
    public void testCreateTicket_Error() {
        Ticket ticket = new Ticket();
        when(ticketServiceMock.createTicket(ticket)).thenReturn(TicketService.TicketServiceResult.ERROR);

        ResponseEntity<String> response = ticketController.createTicket(ticket);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create Ticket.", response.getBody());
        verify(ticketServiceMock, times(1)).createTicket(ticket);
        logger.debug("Test testCreateTicket_Error passed successfully.");
    }

    @Test
    public void testCreateTicket_InternalServerError() {
        Ticket ticket = new Ticket();
        when(ticketServiceMock.createTicket(ticket)).thenReturn(TicketService.TicketServiceResult.OTHER);

        ResponseEntity<String> response = ticketController.createTicket(ticket);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());
        verify(ticketServiceMock, times(1)).createTicket(ticket);
        logger.debug("Test testCreateTicket_InternalServerError passed successfully.");
    }

    @Test
    public void testUpdateTicket_Success() {
        Ticket ticket = new Ticket();
        when(ticketServiceMock.updateTicket(ticket)).thenReturn(TicketService.TicketServiceResult.SUCCESS);

        ResponseEntity<String> response = ticketController.updateTicket(ticket);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket updated successfully.", response.getBody());
        verify(ticketServiceMock, times(1)).updateTicket(ticket);
        logger.debug("Test testUpdateTicket_Success passed successfully.");
    }

    @Test
    public void testUpdateTicket_NotFound() {
        Ticket ticket = new Ticket();
        when(ticketServiceMock.updateTicket(ticket)).thenReturn(TicketService.TicketServiceResult.NOT_FOUND);

        ResponseEntity<String> response = ticketController.updateTicket(ticket);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ticketServiceMock, times(1)).updateTicket(ticket);
        logger.debug("Test testUpdateTicket_NotFound passed successfully.");
    }

    @Test
    public void testDeleteTicket_Success() {
        String ticketId = "1";
        when(ticketServiceMock.deleteTicket(ticketId)).thenReturn(TicketService.TicketServiceResult.SUCCESS);

        ResponseEntity<String> response = ticketController.deleteTicket(ticketId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket deleted successfully.", response.getBody());
        verify(ticketServiceMock, times(1)).deleteTicket(ticketId);
        logger.debug("Test testDeleteTicket_Success passed successfully.");
    }

    @Test
    public void testDeleteTicket_NotFound() {
        String ticketId = "1";
        when(ticketServiceMock.deleteTicket(ticketId)).thenReturn(TicketService.TicketServiceResult.NOT_FOUND);

        ResponseEntity<String> response = ticketController.deleteTicket(ticketId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ticketServiceMock, times(1)).deleteTicket(ticketId);
        logger.debug("Test testDeleteTicket_NotFound passed successfully.");
    }
}
