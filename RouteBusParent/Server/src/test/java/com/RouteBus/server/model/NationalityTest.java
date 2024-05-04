//package com.RouteBus.server.model;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
//public class NationalityTest {
//
//    private static final Logger logger = Logger.getLogger(NationalityTest.class);
//    private Nationality nationality;
//
//    @Before
//    public void setUp() {
//        nationality = new Nationality("American", "en");
//    }
//
//    @Test
//    public void testDefaultConstructor() {
//        Nationality defaultNationality = new Nationality();
//        assertEquals(null, defaultNationality.getName());
//        logger.debug("Test testDefaultConstructor passed successfully.");
//    }
//
//    @Test
//    public void testConstructorWithName() {
//        assertEquals("American", nationality.getName());
//        logger.debug("Test testConstructorWithName passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetId() {
//        nationality.setId(1L);
//        assertEquals(Long.valueOf(1), nationality.getId());
//        logger.debug("Testing testSetAndGetId passed successfully.");
//    }
//
//    @Test
//    public void testSetAndGetName() {
//        nationality.setName("British");
//        assertEquals("British", nationality.getName());
//        logger.debug("Testing testSetAndGetName passed successfully.");
//    }
//    
//    @Test
//    public void testSetAndGetLanguage() {
//        nationality.setLanguage("en");
//        assertEquals("en", nationality.getLanguage());
//        logger.debug("Test testSetAndGetLanguage passed successfully.");
//    }
//}
