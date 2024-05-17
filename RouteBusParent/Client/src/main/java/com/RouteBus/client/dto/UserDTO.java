package com.RouteBus.client.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
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
    private Set<TicketDTO> tickets = new HashSet<>();

    public UserDTO() {}

    public UserDTO(String firstName, String lastName, String email, String password, NationalityDTO nationality, Date birthDate, UserRole role, Set<TicketDTO> tickets) {
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
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NationalityDTO getNationality() {
        return nationality;
    }

    public void setNationality(NationalityDTO nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<TicketDTO> getTickets() {
        return tickets;
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
        UserDTO other = (UserDTO) obj;
        return Objects.equals(email, other.email);
    }
}
