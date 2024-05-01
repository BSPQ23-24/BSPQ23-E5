package com.RouteBus.server.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTest {

    @InjectMocks
    private User user;

    @Mock
    private Nationality mockNationality;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setBirthDate(new Date());
        user.setRole(UserRole.CUSTOMER);
        user.setNationality(mockNationality);
    }

    @Test
    public void testGetAndSetId() {
        Long expectedId = 2L;
        user.setId(expectedId);
        assertEquals(expectedId, user.getId());
    }

    @Test
    public void testGetAndSetFirstName() {
        String expectedFirstName = "Jane";
        user.setFirstName(expectedFirstName);
        assertEquals(expectedFirstName, user.getFirstName());
    }

    @Test
    public void testGetAndSetLastName() {
        String expectedLastName = "Smith";
        user.setLastName(expectedLastName);
        assertEquals(expectedLastName, user.getLastName());
    }

    @Test
    public void testGetAndSetEmail() {
        String expectedEmail = "jane.smith@example.com";
        user.setEmail(expectedEmail);
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    public void testGetAndSetPassword() {
        String expectedPassword = "newpassword123";
        user.setPassword(expectedPassword);
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    public void testGetAndSetNationality() {
        Nationality expectedNationality = new Nationality("British");
        user.setNationality(expectedNationality);
        assertEquals(expectedNationality, user.getNationality());
    }

    @Test
    public void testGetAndSetBirthDate() {
        Date expectedBirthDate = new Date(100000000000L);
        user.setBirthDate(expectedBirthDate);
        assertEquals(expectedBirthDate, user.getBirthDate());
    }

    @Test
    public void testGetAndSetRole() {
        UserRole expectedRole = UserRole.ADMIN;
        user.setRole(expectedRole);
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    public void testSetAndGetTickets() {
        List<Ticket> tickets = new ArrayList<>();
        Ticket mockTicket = mock(Ticket.class);
        tickets.add(mockTicket);
        user.setTickets(tickets);
        assertEquals(tickets, user.getTickets());
        verifyNoInteractions(mockTicket); 
    }
}
