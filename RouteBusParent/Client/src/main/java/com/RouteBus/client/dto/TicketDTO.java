package com.RouteBus.client.dto;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TicketDTO {

    public enum TicketStatus {
        RESERVED, PURCHASED, CANCELLED
    }

    private String id;
    private int seatNumber;
    private double price;
    private TicketStatus status;
    private UserDTO user;
    private ScheduleDTO schedule;

    public TicketDTO() {}

    public TicketDTO(UserDTO user, int seatNumber, double price, TicketStatus status, ScheduleDTO schedule) {
        this.id = UUID.randomUUID().toString();
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        TicketDTO other = (TicketDTO) obj;
        return Objects.equals(id, other.id);
    }
}
