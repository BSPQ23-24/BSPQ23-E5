package com.RouteBus.server.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.TicketRepository;
import com.RouteBus.server.model.Ticket;

@Service
public class TicketService {
	private final TicketRepository ticketRepository;

	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public enum TicketServiceResult {
		SUCCESS, NOT_FOUND, ERROR, OTHER
	}

	public Ticket getTicketById(String id) {
		return ticketRepository.findById(id).orElse(null);
	}

	public Set<Ticket> getAllTickets() {
		return new HashSet<Ticket>(ticketRepository.findAll());
	}
	public List<Ticket> getAllTicketsByUser(String email) {
		return ticketRepository.findByUserEmail(email);
	}

	public TicketServiceResult createTicket(Ticket ticket) {
		if (!ticketRepository.findById(ticket.getId()).isPresent()) {
			Ticket savedTicket = ticketRepository.save(ticket);
			return savedTicket != null ? TicketServiceResult.SUCCESS : TicketServiceResult.ERROR;
		}
		return TicketServiceResult.ERROR;
	}

	public TicketServiceResult updateTicket(Ticket ticket) {
	    return ticketRepository.findById(ticket.getId()).map(existingTicket -> {
	        boolean updated = false;
	        if (existingTicket.getPrice() != ticket.getPrice()) {
	            existingTicket.setPrice(ticket.getPrice());
	            updated = true;
	        }
	        if (!Objects.equals(existingTicket.getStatus(), ticket.getStatus())) {
	            existingTicket.setStatus(ticket.getStatus());
	            updated = true;
	        }
	        if (!Objects.equals(existingTicket.getSeatNumber(), ticket.getSeatNumber())) {
	            existingTicket.setSeatNumber(ticket.getSeatNumber());
	            updated = true;
	        }
	        if (!Objects.equals(existingTicket.getUser(), ticket.getUser())) {
	            existingTicket.setUser(ticket.getUser());
	            updated = true;
	        }
	        if (!Objects.equals(existingTicket.getSchedule(), ticket.getSchedule())) {
	            existingTicket.setSchedule(ticket.getSchedule());
	            updated = true;
	        }
	        if (updated) {
	            ticketRepository.save(existingTicket);
	        }
	        return TicketServiceResult.SUCCESS;
	    }).orElse(TicketServiceResult.NOT_FOUND);
	}


	public TicketServiceResult deleteTicket(String id) {
		return ticketRepository.findById(id).map(ticket -> {
			ticketRepository.delete(ticket);
			return TicketServiceResult.SUCCESS;
		}).orElse(TicketServiceResult.NOT_FOUND);
	}
}
