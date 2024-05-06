package com.RouteBus.server.model;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class NationalityTest {

    private final Logger logger = Logger.getLogger(NationalityTest.class);
    private Nationality nationality;

    @Before
    public void setUp() {
        nationality = new Nationality("American", "en");
    }

    @Test
    public void testDefaultConstructor() {
        Nationality defaultNationality = new Nationality();
        assertEquals(null, defaultNationality.getName());
        logger.debug("Test testDefaultConstructor passed successfully.");
    }

    @Test
    public void testConstructorWithName() {
        assertEquals("American", nationality.getName());
        logger.debug("Test testConstructorWithName passed successfully.");
    }

    @Test
    public void testSetAndGetName() {
        nationality.setName("British");
        assertEquals("British", nationality.getName());
        logger.debug("Testing testSetAndGetName passed successfully.");
    }
    
    @Test
    public void testSetAndGetLanguage() {
        nationality.setLanguage("en");
        assertEquals("en", nationality.getLanguage());
        logger.debug("Test testSetAndGetLanguage passed successfully.");
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Nationality nationality1 = new Nationality("American", "en");
        Nationality nationality2 = new Nationality("American", "en");
        Nationality nationality3 = new Nationality("British", "en");

        assertEquals(nationality1, nationality2);
        assertEquals(nationality1.hashCode(), nationality2.hashCode());

        assertNotEquals(nationality1, nationality3);
        assertNotEquals(nationality1.hashCode(), nationality3.hashCode());

        assertNotEquals(nationality1, null);
        assertNotEquals(nationality1, new Object());

        logger.debug("Test testEqualsAndHashCode passed successfully.");
    }
}
