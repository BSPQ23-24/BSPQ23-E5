package com.RouteBus.server.service;

import com.RouteBus.server.dao.TicketRepository;
import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.model.TicketStatus;
import com.RouteBus.server.model.User;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class TicketServicePerformanceTest {

    @Mock
    private TicketRepository ticketRepository;

    private TicketService ticketService;

    public TicketServicePerformanceTest() {
        String reportPath = generateReportPath(TicketServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ticketService = new TicketService(ticketRepository);
    }

    @Test
    @JUnitPerfTest(threads = 50, durationMs = 15000, warmUpMs = 1000, maxExecutionsPerSecond = 100)
    public void testCreateTicketPerformance() {
        Ticket newTicket = mockTicket(UUID.randomUUID().toString());
        when(ticketRepository.findById(newTicket.getId())).thenReturn(Optional.empty());
        when(ticketRepository.save(any(Ticket.class))).thenReturn(newTicket);
        ticketService.createTicket(newTicket);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetTicketByIdPerformance() {
        Ticket ticket = mockTicket(UUID.randomUUID().toString());
        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        ticketService.getTicketById(ticket.getId());
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllTicketsPerformance() {
        when(ticketRepository.findAll()).thenReturn(mockTickets(10));
        ticketService.getAllTickets();
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000, warmUpMs = 1000, maxExecutionsPerSecond = 50)
    public void testUpdateTicketPerformance() {
        Ticket existingTicket = mockTicket(UUID.randomUUID().toString());
        when(ticketRepository.findById(existingTicket.getId())).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setPrice(50.0);
        updatedTicket.setStatus(TicketStatus.RESERVED);
        updatedTicket.setSeatNumber(15);
        updatedTicket.setUser(existingTicket.getUser());
        updatedTicket.setSchedule(existingTicket.getSchedule());

        ticketService.updateTicket(updatedTicket);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 30)
    public void testDeleteTicketPerformance() {
        Ticket ticketToDelete = mockTicket(UUID.randomUUID().toString());
        when(ticketRepository.findById(ticketToDelete.getId())).thenReturn(Optional.of(ticketToDelete));
        doNothing().when(ticketRepository).delete(ticketToDelete);
        ticketService.deleteTicket(ticketToDelete.getId());
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }

    private Ticket mockTicket(String id) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setUser(mock(User.class));
        ticket.setSeatNumber(10);
        ticket.setPrice(20.0);
        ticket.setStatus(TicketStatus.RESERVED);
        ticket.setSchedule(mock(Schedule.class));
        return ticket;
    }

    private List<Ticket> mockTickets(int count) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Ticket ticket = mockTicket(UUID.randomUUID().toString());
            tickets.add(ticket);
        }
        return tickets;
    }
}
