package com.RouteBus.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private User user;

    @Mock
    private Nationality mockNationality;
    @Mock
    private List<Ticket> mockTickets;

    private Date birthDate;

    @Before
    public void setUp() {
        birthDate = new Date();
        user = new User("John", "Doe", "johndoe@example.com", "password123", birthDate, UserRole.CUSTOMER);
        user.setNationality(mockNationality);
        user.setTickets(mockTickets);
        user.setId(0L);
    }

    @Test
    public void testConstructorAndProperties() {
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(UserRole.CUSTOMER, user.getRole());
        assertEquals(mockNationality, user.getNationality());
    }
    
    

    @Test
    public void testSetAndGetId() {
        user.setId(1L);
        assertEquals(Long.valueOf(1), user.getId());
    }

    @Test
    public void testSetAndGetEmail() {
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    public void testSetAndGetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testSetAndGetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testSetAndGetNationality() {
        Nationality newNationality = new Nationality("American", "en");
        user.setNationality(newNationality);
        assertEquals(newNationality, user.getNationality());
    }

    @Test
    public void testSetAndGetBirthDate() {
        Date newBirthDate = new Date();
        user.setBirthDate(newBirthDate);
        assertEquals(newBirthDate, user.getBirthDate());
    }

    @Test
    public void testSetAndGetRole() {
        user.setRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    public void testSetAndGetTickets() {
        List<Ticket> newTickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        newTickets.add(ticket);
        user.setTickets(newTickets);

        assertEquals(newTickets, user.getTickets());
    }

    @Test
    public void testConstructorWithoutParameters() {
        User user = new User();
        assertNull(user.getFirstName());
        assertEquals(null, user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getBirthDate());
        assertNull(user.getRole());
        assertNull(user.getNationality());
        assertNull(user.getId());
        assertNull(user.getTickets());
    }

    @Test
    public void testEqualsAndHashCode() {
        assertEquals(user, user);

        User sameUser = new User("John", "Doe", "johndoe@example.com", "password123", birthDate, UserRole.CUSTOMER);
        sameUser.setId(user.getId());

        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());

        User differentUser = new User("John", "Doe", "johndoe@example.com", "password123", birthDate, UserRole.CUSTOMER);
        differentUser.setId(999L);
        assertNotEquals(user, differentUser);
        assertNotEquals(user.hashCode(), differentUser.hashCode());

        User nullIdUser1 = new User("John", "Doe", "johndoe@example.com", "password123", birthDate, UserRole.CUSTOMER);
        assertEquals(user, nullIdUser1);

        User nullIdUser2 = new User("John", "Doe", "johndoe@example.com", "password123", birthDate, UserRole.CUSTOMER);
        nullIdUser2.setId(null);
        user.setId(null);
        assertEquals(user, nullIdUser2);
        assertEquals(user.hashCode(), nullIdUser2.hashCode());

        User nullIdDifferentEmailUser = new User("John", "Doe", "different@example.com", "password123", birthDate, UserRole.CUSTOMER);
        nullIdDifferentEmailUser.setId(null);  
        assertNotEquals(user, nullIdDifferentEmailUser);
        assertNotEquals(user.hashCode(), nullIdDifferentEmailUser.hashCode());

        assertNotEquals(user, null); 
        assertNotEquals(user, new Object()); 
    }



    @Test
    public void testToString() {
        String expectedString = "User{id=" + user.getId() + 
                                ", firstName='John', lastName='Doe', email='johndoe@example.com', role=CUSTOMER}";
        assertEquals(expectedString, user.toString());
    }
}
