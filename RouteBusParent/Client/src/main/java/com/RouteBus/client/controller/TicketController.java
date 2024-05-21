package com.RouteBus.client.controller;

import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.gateway.TicketGateway;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing ticket-related operations.
 */
public class TicketController {
    private static final TicketController INSTANCE = new TicketController();
    private final TicketGateway ticketGateway;

    private TicketController() {
        this.ticketGateway = TicketGateway.getInstance();
    }

    /**
     * Retrieves the singleton instance of TicketController.
     * @return The singleton instance of TicketController.
     */
    public static TicketController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all tickets.
     * @return A list of TicketDTO objects representing all tickets.
     */
    public List<TicketDTO> getAllTickets() {
        try {
            return ticketGateway.getAllTickets();
        } catch (Exception e) {
            System.err.println("Failed to fetch tickets: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a ticket by ID.
     * @param id The ID of the ticket to retrieve.
     * @return The TicketDTO object representing the ticket with the specified ID, or null if not found.
     */
    public TicketDTO getTicketById(String id) {
        try {
            return ticketGateway.getTicketById(id);
        } catch (Exception e) {
            System.err.println("Failed to fetch ticket: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Retrieves all tickets associated with a user.
     * @param email The email of the user.
     * @return A list of TicketDTO objects representing tickets associated with the user.
     */
    public List<TicketDTO> getTicketByUser(String email) {
        try {
            return ticketGateway.getAllTicketsByUser(email);
        } catch (Exception e) {
            System.err.println("Failed to fetch ticket: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new ticket.
     * @param ticket The TicketDTO object representing the ticket to be created.
     * @return true if the ticket is successfully created, false otherwise.
     */
    public boolean createTicket(TicketDTO ticket) {
        try {
            return ticketGateway.createTicket(ticket);
        } catch (Exception e) {
            System.err.println("Failed to create ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing ticket.
     * @param ticket The TicketDTO object representing the updated ticket information.
     * @return true if the ticket is successfully updated, false otherwise.
     */
    public boolean updateTicket(TicketDTO ticket) {
        try {
            return ticketGateway.updateTicket(ticket);
        } catch (Exception e) {
            System.err.println("Failed to update ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a ticket by ID.
     * @param id The ID of the ticket to delete.
     * @return true if the ticket is successfully deleted, false otherwise.
     */
    public boolean deleteTicket(String id) {
        try {
            return ticketGateway.deleteTicket(id);
        } catch (Exception e) {
            System.err.println("Failed to delete ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Filters tickets based on the specified query string.
     * @param query The query string used for filtering.
     * @return A list of TicketDTO objects that match the filter criteria.
     */
    public List<TicketDTO> filterTickets(String query) {
        List<TicketDTO> tickets = getAllTickets();
        if (tickets != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return tickets.stream()
                .filter(ticket -> ticket.getId().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(ticket.getSeatNumber()).contains(query) ||
                        String.valueOf(ticket.getPrice()).contains(query) ||
                        ticket.getStatus().toString().toLowerCase().contains(query.toLowerCase()) ||
                        ticket.getUser().getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        sdf.format(ticket.getSchedule().getDepartureTime()).contains(query) ||
                        sdf.format(ticket.getSchedule().getArrivalTime()).contains(query))
                .collect(Collectors.toList());
        }
        return null;
    }
}
