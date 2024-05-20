package com.RouteBus.client.gateway;

import com.RouteBus.client.dto.TicketDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TicketGateway extends BaseGateway {
	
    private static final TicketGateway INSTANCE = new TicketGateway();

    private TicketGateway() {
        super();
    }

    public static TicketGateway getInstance() {
        return INSTANCE;
    }
    
    public List<TicketDTO> getAllTickets() {
        ResponseEntity<TicketDTO[]> response = sendRequest("/Ticket/all", HttpMethod.GET, null, TicketDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public List<TicketDTO> getAllTicketsByUser(String name) {
        ResponseEntity<TicketDTO[]> response = sendRequest("/Ticket/user/" + name, HttpMethod.GET, null, TicketDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public TicketDTO getTicketById(String id) {
        ResponseEntity<TicketDTO> response = sendRequest("/Ticket/" + id, HttpMethod.GET, null, TicketDTO.class);
        return response.getBody();
    }

    public boolean createTicket(TicketDTO ticket) {
        ResponseEntity<String> response = sendRequest("/Ticket/create", HttpMethod.POST, ticket, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean updateTicket(TicketDTO ticket) {
        ResponseEntity<String> response = sendRequest("/Ticket/update", HttpMethod.PUT, ticket, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean deleteTicket(String id) {
        ResponseEntity<String> response = sendRequest("/Ticket/delete/" + id, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
