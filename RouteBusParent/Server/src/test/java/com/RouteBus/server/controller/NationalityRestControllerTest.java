package com.RouteBus.server.controller;

import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.service.NationalityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NationalityRestControllerTest {

    @Mock
    private NationalityService nationalityService;

    @InjectMocks
    private NationalityRestController nationalityRestController;

    
    List<Nationality> mockNationalities;
    
    @Before
    public void setUp() {
        mockNationalities = Arrays.asList(
                new Nationality("USA", "en"),
                new Nationality("Canada", "en"),
                new Nationality("UK", "en")
        );
        when(nationalityService.getAllNationalities()).thenReturn(mockNationalities);
    }

    @Test
    public void testGetAllNationalities() {
        ResponseEntity<List<Nationality>> response = nationalityRestController.getAllNationalities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockNationalities, response.getBody());

        verify(nationalityService, times(1)).getAllNationalities();
    }
}
