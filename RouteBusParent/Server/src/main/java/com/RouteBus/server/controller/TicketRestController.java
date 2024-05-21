package com.RouteBus.server.controller;

import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST controller for managing Ticket entities.
 */
@RestController
@RequestMapping("/Ticket")
public class TicketRestController {
    private final TicketService ticketService;

    /**
     * Constructor for TicketRestController.
     *
     * @param ticketService The TicketService instance to be used by the controller.
     */
    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Endpoint to get all tickets.
     *
     * @return ResponseEntity containing a Set of Ticket objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Set<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    /**
     * Endpoint to get all tickets associated with a user.
     *
     * @param email The email of the user to retrieve tickets for.
     * @return ResponseEntity containing a List of Ticket objects.
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Ticket>> getAllTicketsByUser(@PathVariable String email) {
        return ResponseEntity.ok(ticketService.getAllTicketsByUser(email));
    }

    /**
     * Endpoint to get a ticket by its ID.
     *
     * @param id The ID of the ticket to retrieve.
     * @return ResponseEntity containing a Ticket object if found, or ResponseEntity.notFound() if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to create a new ticket.
     *
     * @param ticket The Ticket object to be created.
     * @return ResponseEntity with a success message if creation is successful, or error message otherwise.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createTicket(@RequestBody Ticket ticket) {
        TicketService.TicketServiceResult result = ticketService.createTicket(ticket);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Ticket created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create ticket.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }
    
    /**
     * Endpoint to create a new ticket for a specific user.
     *
     * @param ticket The Ticket object to be created.
     * @return ResponseEntity with a success message if creation is successful, or error message otherwise.
     */
    @PostMapping("/createForUser")
    public ResponseEntity<String> createTicketForUser(@RequestBody Ticket ticket) {
        TicketService.TicketServiceResult result = ticketService.createTicket(ticket);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Ticket created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create ticket.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    /**
     * Endpoint to update an existing ticket.
     *
     * @param ticket The Ticket object with updated information.
     * @return ResponseEntity with a success message if update is successful, or ResponseEntity.notFound() otherwise.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateTicket(@RequestBody Ticket ticket) {
        TicketService.TicketServiceResult result = ticketService.updateTicket(ticket);
        return result == TicketService.TicketServiceResult.SUCCESS ?
                ResponseEntity.ok("Ticket updated successfully.") :
                ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to delete a ticket by its ID.
     *
     * @param id The ID of the ticket to be deleted.
     * @return ResponseEntity with a success message if deletion is successful, or ResponseEntity.notFound() otherwise.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable String id) {
        TicketService.TicketServiceResult result = ticketService.deleteTicket(id);
        return result == TicketService.TicketServiceResult.SUCCESS ?
                ResponseEntity.ok("Ticket deleted successfully.") :
                ResponseEntity.notFound().build();
    }
}
