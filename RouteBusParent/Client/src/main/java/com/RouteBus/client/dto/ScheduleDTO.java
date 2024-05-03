package com.RouteBus.client.dto;

import java.util.Date;
import java.util.List;

public class ScheduleDTO {

    private RouteDTO route;
    private Date departureTime;
    private Date arrivalTime;
    private Date date;
    private List<TicketDTO> tickets;

    public ScheduleDTO() {
    }

    public ScheduleDTO(RouteDTO route, Date departureTime, Date arrivalTime, Date date) {
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.date = date;
    }

	public RouteDTO getRoute() {
		return route;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public Date getDate() {
		return date;
	}

	public List<TicketDTO> getTickets() {
		return tickets;
	}

	public void setRoute(RouteDTO route) {
		this.route = route;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTickets(List<TicketDTO> tickets) {
		this.tickets = tickets;
	}
}