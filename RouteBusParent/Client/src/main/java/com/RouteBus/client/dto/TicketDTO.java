package com.RouteBus.client.dto;

import java.util.Objects;

public class TicketDTO {

	public enum TicketStatus {
	    RESERVED,
	    PURCHASED,
	    CANCELLED
	}
	
	private String id;
    private UserDTO user;
    private int seatNumber;
    private double price;
    private TicketStatus status;
    private ScheduleDTO schedule;

    public TicketDTO() {
    }

    public TicketDTO(UserDTO user, int seatNumber, double price, TicketStatus status, ScheduleDTO schedule) {
    	this.id = schedule.getId() + "-" + String.format("%04d", seatNumber) + "-" + status.toString().toLowerCase();
        this.user = user;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
        this.schedule = schedule;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public ScheduleDTO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDTO schedule) {
        this.schedule = schedule;
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
		TicketDTO other = (TicketDTO) obj;
		return Objects.equals(id, other.id);
	}
}
