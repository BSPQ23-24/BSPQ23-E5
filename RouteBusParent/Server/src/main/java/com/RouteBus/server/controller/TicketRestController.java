package com.RouteBus.server.controller;

import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Ticket")
public class TicketRestController {
    private final TicketService ticketService;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Ticket>> getAllTicketsByUser(@PathVariable String email) {
        return ResponseEntity.ok(ticketService.getAllTicketsByUser(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

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

    @PutMapping("/update")
    public ResponseEntity<String> updateTicket(@RequestBody Ticket ticket) {
        TicketService.TicketServiceResult result = ticketService.updateTicket(ticket);
        return result == TicketService.TicketServiceResult.SUCCESS ?
                ResponseEntity.ok("Ticket updated successfully.") :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable String id) {
        TicketService.TicketServiceResult result = ticketService.deleteTicket(id);
        return result == TicketService.TicketServiceResult.SUCCESS ?
                ResponseEntity.ok("Ticket deleted successfully.") :
                ResponseEntity.notFound().build();
    }
}
