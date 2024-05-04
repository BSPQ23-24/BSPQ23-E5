package com.RouteBus.client.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ScheduleDTO {

	private String id;
    private RouteDTO route;
    private Date departureTime;
    private Date arrivalTime;
    @JsonManagedReference("schedule-back")
    private Set<TicketDTO> tickets;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String id, RouteDTO route, Date departureTime, Date arrivalTime, Set<TicketDTO> tickets) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    	this.id = route.getName().replaceAll("\\s+", "").toLowerCase() + "-" + sdf.format(departureTime) + "-" + sdf.format(arrivalTime);
		this.route = route;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.tickets = tickets;
	}

	public ScheduleDTO(RouteDTO route, Date departureTime, Date arrivalTime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    	this.id = route.getName().replaceAll("\\s+", "").toLowerCase() + "-" + sdf.format(departureTime) + "-" + sdf.format(arrivalTime);
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.tickets = new HashSet<TicketDTO>();
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Set<TicketDTO> getTickets() {
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

	public void setTickets(Set<TicketDTO> tickets) {
		this.tickets = tickets;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleDTO other = (ScheduleDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}