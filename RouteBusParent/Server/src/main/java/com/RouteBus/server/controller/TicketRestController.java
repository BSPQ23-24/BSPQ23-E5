package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/Ticket")
public class TicketRestController {
    private final TicketService ticketService;

    public TicketRestController(TicketService TicketService) {
        this.ticketService = TicketService;
    }

    @GetMapping("/all")
    public List<Ticket> getAllTicketes() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        Ticket Ticket = ticketService.getTicketById(id);
        return Ticket != null ? ResponseEntity.ok(Ticket) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTicket(@RequestBody Ticket Ticket) {
        TicketService.TicketServiceResult result = ticketService.createTicket(Ticket);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Ticket created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create Ticket.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTicket(@RequestBody Ticket Ticket) {
        TicketService.TicketServiceResult result = ticketService.updateTicket(Ticket);
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
