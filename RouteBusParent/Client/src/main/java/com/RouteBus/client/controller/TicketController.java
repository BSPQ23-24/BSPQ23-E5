package com.RouteBus.client.controller;

import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.gateway.TicketGateway;
import java.util.List;

public class TicketController {
    private static final TicketController INSTANCE = new TicketController();
    private final TicketGateway ticketGateway;

    private TicketController() {
        this.ticketGateway =TicketGateway.getInstance();
    }

    public static TicketController getInstance() {
        return INSTANCE;
    }

    public List<TicketDTO> getAllTickets() {
        try {
            return ticketGateway.getAllTickets();
        } catch (Exception e) {
            System.err.println("Failed to fetch tickets: " + e.getMessage());
            return null;
        }
    }

    public TicketDTO getTicketById(String id) {
        try {
            return ticketGateway.getTicketById(id);
        } catch (Exception e) {
            System.err.println("Failed to fetch ticket: " + e.getMessage());
            return null;
        }
    }

    public boolean createTicket(TicketDTO ticket) {
        try {
            return ticketGateway.createTicket(ticket);
        } catch (Exception e) {
            System.err.println("Failed to create ticket: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTicket(TicketDTO ticket) {
        try {
            return ticketGateway.updateTicket(ticket);
        } catch (Exception e) {
            System.err.println("Failed to update ticket: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTicket(String id) {
        try {
            return ticketGateway.deleteTicket(id);
        } catch (Exception e) {
            System.err.println("Failed to delete ticket: " + e.getMessage());
            return false;
        }
    }
}