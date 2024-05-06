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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicketServiceTest {

    private final Logger logger = LogManager.getLogger(TicketServiceTest.class);

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private AutoCloseable closeable;

    private Ticket existingTicket, updatedTicket;
    private User user, newUser;
    private Schedule schedule, newSchedule;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        user = new User("John", "Doe", "john.doe@example.com", "password", null, null);
        newUser = new User("Jane", "Doe", "jane.doe@example.com", "newpassword", null, null);
        schedule = new Schedule("scheduleId", null, null, null, null);
        newSchedule = new Schedule("newScheduleId", null, null, null, null);
        existingTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, schedule);

        when(ticketRepository.findById("ticketId")).thenReturn(Optional.of(existingTicket));
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
        logger.info("Resources closed and test completed successfully");
    }
    
    @Test
    public void testUpdateTicketPrice() {
        updatedTicket = new Ticket("ticketId", user, 25, 299.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository).save(updatedTicket);
        logger.info("testUpdateTicketPrice passed successfully");
    }

    @Test
    public void testUpdateTicketStatus() {
        updatedTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.PURCHASED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository).save(updatedTicket);
        logger.info("testUpdateTicketStatus passed successfully");
    }

    @Test
    public void testUpdateTicketSeatNumber() {
        updatedTicket = new Ticket("ticketId", user, 30, 199.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository).save(updatedTicket);
        logger.info("testUpdateTicketSeatNumber passed successfully");
        
    }

    @Test
    public void testUpdateTicketUser() {
        updatedTicket = new Ticket("ticketId", newUser, 25, 199.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository).save(updatedTicket);
        logger.info("testUpdateTicketUser passed successfully");
    }

    @Test
    public void testUpdateTicketSchedule() {
        updatedTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, newSchedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository).save(updatedTicket);
        logger.info("testUpdateTicketSchedule passed successfully");
    }

    @Test
    public void testUpdateTicketNoChange() {
        updatedTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository, never()).save(updatedTicket);
        logger.info("testUpdateTicketNoChange passed successfully");
    }

    @Test
    public void testCreateTicketNotPresent() {
        Ticket newTicket = new Ticket("newTicketId", user, 10, 150.00, TicketStatus.PURCHASED, schedule);
        when(ticketRepository.findById("newTicketId")).thenReturn(Optional.empty());
        when(ticketRepository.save(newTicket)).thenReturn(newTicket);
        TicketService.TicketServiceResult result = ticketService.createTicket(newTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        logger.info("testCreateTicketNotPresent passed successfully");
    }

    @Test
    public void testCreateTicketFailsToSave() {
    	Ticket newTicket = new Ticket("newTicketId", user, 10, 150.00, TicketStatus.PURCHASED, schedule);
        when(ticketRepository.findById("newTicketId")).thenReturn(Optional.empty());
        when(ticketRepository.save(newTicket)).thenReturn(null);
        TicketService.TicketServiceResult result = ticketService.createTicket(newTicket);
        assertEquals(TicketService.TicketServiceResult.ERROR, result);
        logger.info("testCreateTicketFailsToSave passed successfully");
    }

    @Test
    public void testUpdateTicketNotUpdated() {
    	Ticket unchangedTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(unchangedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository, never()).save(unchangedTicket);
        logger.info("testUpdateTicketNotUpdated passed successfully");
    }

    @Test
    public void testUpdateTicketNotFound() {
        updatedTicket = new Ticket("nonExistentId", user, 30, 250.00, TicketStatus.CANCELLED, schedule);
        when(ticketRepository.findById("nonExistentId")).thenReturn(Optional.empty());
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.NOT_FOUND, result);
        logger.info("testUpdateTicketNotFound passed successfully");
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
        List<Ticket> result = ticketService.getAllTickets();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        logger.info("testGetAllTicketsReturnsTickets passed successfully");
    }

    @Test
    public void testCreateTicketWhenAlreadyExists() {
        Ticket newTicket = new Ticket("ticketId", user, 10, 99.99, TicketStatus.PURCHASED, schedule);
        TicketService.TicketServiceResult result = ticketService.createTicket(newTicket);
        assertEquals(TicketService.TicketServiceResult.ERROR, result);
        logger.info("testCreateTicketWhenAlreadyExists passed successfully");
    }

    @Test
    public void testUpdateTicketWithNoChanges() {
        Ticket sameTicket = new Ticket("ticketId", user, 25, 199.99, TicketStatus.RESERVED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(sameTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository, never()).save(sameTicket);
        logger.info("testUpdateTicketWithNoChanges passed successfully");
    }

    @Test
    public void testUpdateTicketWithChanges() {
        updatedTicket = new Ticket("ticketId", user, 30, 299.99, TicketStatus.PURCHASED, schedule);
        TicketService.TicketServiceResult result = ticketService.updateTicket(updatedTicket);
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        verify(ticketRepository).save(updatedTicket);
        logger.info("testUpdateTicketWithChanges passed successfully");
    }

    @Test
    public void testDeleteTicket() {
        TicketService.TicketServiceResult result = ticketService.deleteTicket("ticketId");
        assertEquals(TicketService.TicketServiceResult.SUCCESS, result);
        logger.info("testDeleteTicket passed successfully");
    }
}
