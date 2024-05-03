package com.RouteBus.client.dto;

import java.util.Date;

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
    
    public UserDTO() {}

	public UserDTO(String firstName, String lastName, String email, String password, NationalityDTO nationality, Date birthDate, UserRole role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.role = role;
	}
	
	public UserDTO(String firstName, String lastName, String email, String password, NationalityDTO nationality, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.role = UserRole.CUSTOMER;
	}

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
}
