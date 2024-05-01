package com.RouteBus.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Schedule() {
    }

    public Schedule(Route route, Date departureTime, Date arrivalTime, Date date) {
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.date = date;
    }

	public Long getId() {
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

	public Date getDate() {
		return date;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setId(Long id) {
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

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}