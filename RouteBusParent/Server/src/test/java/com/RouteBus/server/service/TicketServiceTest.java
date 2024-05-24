package com.RouteBus.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.RouteBus.server.dao.TicketRepository;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.model.TicketStatus;
import com.RouteBus.server.model.User;
import com.RouteBus.server.model.Schedule;

import java.util.*;

public class TicketServiceTest {

    private final Logger logger = LogManager.getLogger(TicketServiceTest.class);

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private AutoCloseable closeable;

    private Ticket existingTicket, updatedTicket;
    private User user;
    private Schedule schedule;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        user = new User("John", "Doe", "john.doe@example.com", "password", null, null);
        schedule = new Schedule("scheduleId", null, null, null, null);
        existingTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, schedule);

        when(ticketRepository.findById("ticketId")).thenReturn(Optional.of(existingTicket));
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
        logger.info("Resources closed and test completed successfully");
    }

    @Test
    public void testGetTicketById() {
        Ticket result = ticketService.getTicketById("ticketId");
        assertNotNull(result);
        assertEquals(existingTicket, result);
        logger.info("testGetTicketById passed successfully");
    }

    @Test
    public void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(existingTicket));
        Set<Ticket> result = ticketService.getAllTickets();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        logger.info("testGetAllTickets passed successfully");
    }

    @Test
    public void testGetAllTicketsByUser() {
        List<Ticket> tickets = Arrays.asList(existingTicket);
        when(ticketRepository.findByUserEmail("john.doe@example.com")).thenReturn(tickets);

        List<Ticket> result = ticketService.getAllTicketsByUser("john.doe@example.com");
        assertEquals(1, result.size());
        assertEquals(existingTicket, result.get(0));
        logger.info("testGetAllTicketsByUser passed successfully");
    }

    @Test
    public void testCreateTicket_NotPresent() {
        Ticket newTicket = new Ticket("newTicketId", user, 10, 150.00, TicketStatus.PURCHASED, schedule);
        when(ticketRepository.findById("newTicketId")).thenReturn(Optional.empty());
        when(ticketRepository.save(newTicket)).thenReturn(newTicket);
        TicketService.TicketServiceResult result = ticketService.createTicket(newTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        logger.info("testCreateTicket_NotPresent passed successfully");
    }

    @Test
    public void testCreateTicket_FailsToSave() {
        Ticket newTicket = new Ticket("newTicketId", user, 10, 150.00, TicketStatus.PURCHASED, schedule);
        when(ticketRepository.findById("newTicketId")).thenReturn(Optional.empty());
        when(ticketRepository.save(newTicket)).thenReturn(null);
        TicketService.TicketServiceResult result = ticketService.createTicket(newTicket);
        assertEquals(TicketService.TicketServiceResult.ERROR, result);
        logger.info("testCreateTicket_FailsToSave passed successfully");
    }

    @Test
    public void testCreateTicket_AlreadyExists() {
        Ticket newTicket = new Ticket("ticketId", user, 10, 99.99, TicketStatus.PURCHASED, schedule);
        TicketService.TicketServiceResult result = ticketService.createTicket(newTicket);
        assertEquals(TicketService.TicketServiceResult.ERROR, result);
        logger.info("testCreateTicket_AlreadyExists passed successfully");
    }

    @Test
    public void testUpdateTicket_ChangesMade() {
        User newUser = new User("Jane", "Smith", "jane.smith@example.com", "newpassword", null, null);
        Schedule newSchedule = new Schedule("newScheduleId", null, null, null, null);
        updatedTicket = new Ticket("ticketId", newUser, 30, 299.99, TicketStatus.PURCHASED, newSchedule);

        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);

        verify(ticketRepository).save(existingTicket);

        assertEquals(newUser, existingTicket.getUser());
        assertEquals(newSchedule, existingTicket.getSchedule());

        logger.info("testUpdateTicket_ChangesMade passed successfully");
    }

    @Test
    public void testUpdateTicket_NoChanges() {
        updatedTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository, never()).save(existingTicket);
        logger.info("testUpdateTicket_NoChanges passed successfully");
    }

    @Test
    public void testUpdateTicket_NotFound() {
        updatedTicket = new Ticket("nonExistentId", user, 30, 250.00, TicketStatus.CANCELLED, schedule);
        when(ticketRepository.findById("nonExistentId")).thenReturn(Optional.empty());
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.NOT_FOUND, result);
        logger.info("testUpdateTicket_NotFound passed successfully");
    }

    @Test
    public void testDeleteTicket_Found() {
        TicketService.TicketServiceResult result = ticketService.deleteTicket("ticketId");
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        logger.info("testDeleteTicket_Found passed successfully");
    }

    @Test
    public void testDeleteTicket_NotFound() {
        when(ticketRepository.findById("nonExistentId")).thenReturn(Optional.empty());
        TicketService.TicketServiceResult result = ticketService.deleteTicket("nonExistentId");
        assertEquals(TicketService.TicketServiceResult.NOT_FOUND, result);
        logger.info("testDeleteTicket_NotFound passed successfully");
    }
}
