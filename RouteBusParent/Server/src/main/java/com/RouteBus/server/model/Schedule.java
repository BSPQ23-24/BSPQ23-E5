package com.RouteBus.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    public Schedule() {
    }

    public Schedule(Route route, Date departureTime, Date arrivalTime) {
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

	public String getId() {
		return id;
	}

	public Route getRoute() {
		return route;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setTickets(List<Ticket> tickets) {
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
		Schedule other = (Schedule) obj;
		return Objects.equals(id, other.id);
	}
	
}