package com.RouteBus.server.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "schedules")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Ticket> tickets;

    public Schedule() {
    }

    public Schedule(Route route, Date departureTime, Date arrivalTime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    	this.id = route.getName().replaceAll("\\s+", "").toLowerCase() + "-" + sdf.format(departureTime) + "-" + sdf.format(arrivalTime);
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.tickets = null;
    }
    
    public Schedule(String id, Route route, Date departureTime, Date arrivalTime, Set<Ticket> tickets) {
    	this.id = id;
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.tickets = tickets;
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

	public Set<Ticket> getTickets() {
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

	public void setTickets(Set<Ticket> tickets) {
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
		Schedule other = (Schedule) obj;
		return Objects.equals(id, other.id);
	}
	
}