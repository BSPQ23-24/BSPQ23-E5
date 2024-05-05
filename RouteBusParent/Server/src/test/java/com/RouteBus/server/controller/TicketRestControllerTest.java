package com.RouteBus.server.controller;

import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.TicketService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketRestControllerTest {

    private static final Logger logger = Logger.getLogger(TicketRestControllerTest.class);

    @Mock
    TicketService ticketServiceMock;

    @InjectMocks
    TicketRestController ticketController;

    @BeforeEach
    void setUp() {
        // Configuración inicial, si es necesario
    }

    @Test
    void testGetAllTickets() {
        logger.debug("Starting test testGetAllTickets...");
        // Arrange
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());
        when(ticketServiceMock.getAllTickets()).thenReturn(tickets);

        // Act
        List<Ticket> result = ticketController.getAllTicketes();

        // Assert
        assertEquals(2, result.size());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).getAllTickets();
        logger.debug("Test testGetAllTickets passed successfully.");
    }

    @Test
    void testGetTicketById_TicketExists() {
        logger.debug("Starting test testGetTicketById_TicketExists...");
        // Arrange
        String ticketId = "1";
        Ticket ticket = new Ticket();
        when(ticketServiceMock.getTicketById(ticketId)).thenReturn(ticket);

        // Act
        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).getTicketById(ticketId);
        logger.debug("Test testGetTicketById_TicketExists passed successfully.");
    }

    @Test
    void testGetTicketById_TicketNotExists() {
        logger.debug("Starting test testGetTicketById_TicketNotExists...");
        // Arrange
        String ticketId = "1";
        when(ticketServiceMock.getTicketById(ticketId)).thenReturn(null);

        // Act
        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).getTicketById(ticketId);
        logger.debug("Test testGetTicketById_TicketNotExists passed successfully.");
    }

    @Test
    void testCreateTicket_Success() {
        logger.debug("Starting test testCreateTicket_Success...");
        // Arrange
        Ticket ticket = new Ticket();
        when(ticketServiceMock.createTicket(ticket)).thenReturn(TicketService.TicketServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> response = ticketController.createTicket(ticket);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket created successfully.", response.getBody());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).createTicket(ticket);
        logger.debug("Test testCreateTicket_Success passed successfully.");
    }

    @Test
    void testCreateTicket_Error() {
        logger.debug("Starting test testCreateTicket_Error...");
        // Arrange
        Ticket ticket = new Ticket();
        when(ticketServiceMock.createTicket(ticket)).thenReturn(TicketService.TicketServiceResult.ERROR);

        // Act
        ResponseEntity<String> response = ticketController.createTicket(ticket);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create Ticket.", response.getBody());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).createTicket(ticket);
        logger.debug("Test testCreateTicket_Error passed successfully.");
    }

    @Test
    void testCreateTicket_InternalServerError() {
        logger.debug("Starting test testCreateTicket_InternalServerError...");
        // Arrange
        Ticket ticket = new Ticket();
        when(ticketServiceMock.createTicket(ticket)).thenReturn(TicketService.TicketServiceResult.NOT_FOUND); // Cambia a ERROR en lugar de null

        // Act
        ResponseEntity<String> response = ticketController.createTicket(ticket);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).createTicket(ticket);
        logger.debug("Test testCreateTicket_InternalServerError passed successfully.");
    }


    @Test
    void testUpdateTicket_Success() {
        logger.debug("Starting test testUpdateTicket_Success...");
        // Arrange
        Ticket ticket = new Ticket();
        when(ticketServiceMock.updateTicket(ticket)).thenReturn(TicketService.TicketServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> response = ticketController.updateTicket(ticket);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket updated successfully.", response.getBody());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).updateTicket(ticket);
        logger.debug("Test testUpdateTicket_Success passed successfully.");
    }

    @Test
    void testUpdateTicket_NotFound() {
        logger.debug("Starting test testUpdateTicket_NotFound...");
        // Arrange
        Ticket ticket = new Ticket();
        when(ticketServiceMock.updateTicket(ticket)).thenReturn(TicketService.TicketServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> response = ticketController.updateTicket(ticket);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).updateTicket(ticket);
        logger.debug("Test testUpdateTicket_NotFound passed successfully.");
    }

    @Test
    void testDeleteTicket_Success() {
        logger.debug("Starting test testDeleteTicket_Success...");
        // Arrange
        String ticketId = "1";
        when(ticketServiceMock.deleteTicket(ticketId)).thenReturn(TicketService.TicketServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> response = ticketController.deleteTicket(ticketId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket deleted successfully.", response.getBody());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).deleteTicket(ticketId);
        logger.debug("Test testDeleteTicket_Success passed successfully.");
    }

    @Test
    void testDeleteTicket_NotFound() {
        logger.debug("Starting test testDeleteTicket_NotFound...");
        // Arrange
        String ticketId = "1";
        when(ticketServiceMock.deleteTicket(ticketId)).thenReturn(TicketService.TicketServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> response = ticketController.deleteTicket(ticketId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify mock interactions
        verify(ticketServiceMock, times(1)).deleteTicket(ticketId);
        logger.debug("Test testDeleteTicket_NotFound passed successfully.");
    }
}
