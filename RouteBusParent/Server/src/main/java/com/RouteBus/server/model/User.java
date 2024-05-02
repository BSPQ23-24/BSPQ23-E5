package com.RouteBus.server.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
@Table(name = "userTable")
@Entity
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date birthDate;
    private String password;
    private String nationality;
    private double money;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ticket> tickets;
    
    public User() {
    	
    }
    public User(String name, String lastName, String email, Date birthDate, String password, String nationality) {
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
        this.nationality = nationality;
    }

    public User(String firstName, String lastName, String email, String password) {
    	this(firstName, lastName, email, null, password, null);
	}

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		if(money>=0) {
			this.money = money;
		}
	}
	@Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", birthDate=" + birthDate +
            ", password='" + password + '\'' +
            ", nationality='" + nationality + '\'' +
            '}';
    }
	
	public boolean addTicket(Ticket ticket) {
		return this.tickets.add(ticket);
	}
}