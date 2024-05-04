package com.RouteBus.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.RouteBus.server.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
}
