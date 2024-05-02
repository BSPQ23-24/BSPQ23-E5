package com.RouteBus.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
    private User client;
    private String destination;
    private double price;
    private Date date;

    public Ticket(){
        
    }

    public Ticket(User client, String destination, double price, Date date) {
        this.client = client;
        this.destination = destination;
        this.price = price;
        this.date = date;
    }

    // Getters and setters
    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Long getId() {
    	return id;
    }
    @Override
    public String toString() {
        return "Ticket{" +
            "client=" + client +
            ", destination='" + destination + '\'' +
            ", price=" + price +
            ", date=" + date +
            '}';
    }

}