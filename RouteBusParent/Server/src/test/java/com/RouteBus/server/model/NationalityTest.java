package com.RouteBus.server.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NationalityTest {

    private Nationality nationality;

    @Before
    public void setUp() {
        nationality = new Nationality("American", "en");
    }

    @Test
    public void testDefaultConstructor() {
        Nationality defaultNationality = new Nationality();
        assertEquals(null, defaultNationality.getName());
    }

    @Test
    public void testConstructorWithName() {
        assertEquals("American", nationality.getName());
    }

    @Test
    public void testSetAndGetId() {
        nationality.setId(1L);
        assertEquals(Long.valueOf(1), nationality.getId());
    }

    @Test
    public void testSetAndGetName() {
        nationality.setName("British");
        assertEquals("British", nationality.getName());
    }
    
    @Test
    public void testSetAndGetLanguage() {
        nationality.setLanguage("en");
        assertEquals("en", nationality.getLanguage());
    }
}
