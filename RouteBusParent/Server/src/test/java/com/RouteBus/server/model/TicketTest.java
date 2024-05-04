//package com.RouteBus.server.model;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TicketTest {
//
//    private static final Logger logger = Logger.getLogger(TicketTest.class);
//
//    private Ticket ticket;
//
//    @Mock
//    private User mockUser;
//    @Mock
//    private Schedule mockSchedule;
//
//    @Before
//    public void setUp() {
//        ticket = new Ticket(mockUser, 25, 199.99, TicketStatus.RESERVED, mockSchedule);
//    }
//
//    @Test
//    public void testConstructorAndProperties() {
//        assertEquals(mockUser, ticket.getUser());
//        assertEquals(25, ticket.getSeatNumber());
//        assertEquals(199.99, ticket.getPrice(), 0.001);
//        assertEquals(TicketStatus.RESERVED, ticket.getStatus());
//        assertEquals(mockSchedule, ticket.getSchedule());
//        logger.debug("Test testConstructorAndProperties passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetId() {
//        ticket.setId(1L);
//        assertEquals(Long.valueOf(1), ticket.getId());
//        logger.debug("Test testSetAndGetId passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetUser() {
//        ticket.setUser(mockUser);
//        assertEquals(mockUser, ticket.getUser());
//        logger.debug("Test testSetAndGetUser passed successfully.");
//    }
//
//
//    @Test
//    public void testSetAndGetSeatNumber() {
//        ticket.setSeatNumber(30);
//        assertEquals(30, ticket.getSeatNumber());
//        logger.debug("Test testSetAndGetSeatNumber passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetPrice() {
//        ticket.setPrice(150.00);
//        assertEquals(150.00, ticket.getPrice(), 0.001);
//        logger.debug("Test testSetAndGetPrice passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetStatus() {
//        ticket.setStatus(TicketStatus.PURCHASED);
//        assertEquals(TicketStatus.PURCHASED, ticket.getStatus());
//        logger.debug("Test testSetAndGetStatus passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetSchedule() {
//        Schedule newSchedule = new Schedule();
//        ticket.setSchedule(newSchedule);
//        assertEquals(newSchedule, ticket.getSchedule());
//        logger.debug("Test testSetAndGetSchedule passed successfully.");
//    }
//}
