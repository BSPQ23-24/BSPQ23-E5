package com.RouteBus.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.*;
import static org.mockito.Mockito.*;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NationalityTest {

    @InjectMocks
    private Nationality nationality;

    @Mock
    private EntityManager entityManager;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        nationality = new Nationality("Spanish");
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        nationality.setId(id);
        assertEquals(id, nationality.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Spanish", nationality.getName());
    }

    @Test
    public void testSetName() {
        String newName = "French";
        nationality.setName(newName);
        assertEquals(newName, nationality.getName());
    }

    @Test
    public void testPersistNationality() {
        nationality.setId(1L);
        entityManager.persist(nationality);
        verify(entityManager).persist(nationality);
    }

    @Test
    public void testFindNationalityById() {
        when(entityManager.find(Nationality.class, 1L)).thenReturn(nationality);
        Nationality found = entityManager.find(Nationality.class, 1L);
        assertEquals(nationality, found);
    }
}
