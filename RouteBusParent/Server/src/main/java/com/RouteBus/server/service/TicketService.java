package com.RouteBus.server.service;

import java.util.List;
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
		SUCCESS, NOT_FOUND, ERROR
	}

	public Ticket getTicketById(String id) {
		return ticketRepository.findById(id).orElse(null);
	}

	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
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
			existingTicket.setPrice(ticket.getPrice());
			existingTicket.setStatus(ticket.getStatus());
			existingTicket.setSeatNumber(ticket.getSeatNumber());
			if (!existingTicket.getUser().equals(ticket.getUser())) {
				existingTicket.setUser(ticket.getUser());
			}
			if (!existingTicket.getSchedule().equals(ticket.getSchedule())) {
				existingTicket.setSchedule(ticket.getSchedule());
			}
			ticketRepository.save(existingTicket);
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
