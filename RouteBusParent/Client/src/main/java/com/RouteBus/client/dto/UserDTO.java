package com.RouteBus.client.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class UserDTO {
    public enum UserRole {
        ADMIN, CUSTOMER,
    }

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private NationalityDTO nationality;
    private Date birthDate;
    private UserRole role;
    @JsonManagedReference("user-back")
    private Set<TicketDTO> tickets;
    
    public UserDTO() {}

	public UserDTO(String firstName, String lastName, String email, String password, NationalityDTO nationality,
			Date birthDate, UserRole role, Set<TicketDTO> tickets) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.role = role;
		this.tickets = tickets;
	}

	public UserDTO(String firstName, String lastName, String email, String password, NationalityDTO nationality, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.role = UserRole.CUSTOMER;
		this.tickets = new HashSet<TicketDTO>();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public NationalityDTO getNationality() {
		return nationality;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public UserRole getRole() {
		return role;
	}

	public Set<TicketDTO> getTickets() {
		return tickets;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNationality(NationalityDTO nationality) {
		this.nationality = nationality;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setTickets(Set<TicketDTO> tickets) {
		this.tickets = tickets;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(email, other.email);
	}
}
