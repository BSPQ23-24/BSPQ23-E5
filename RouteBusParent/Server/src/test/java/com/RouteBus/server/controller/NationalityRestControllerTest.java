package com.RouteBus.server.controller;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(NationalityRestControllerTest.class);

    @Mock
    private NationalityService nationalityService;

    @InjectMocks
    private NationalityRestController nationalityRestController;

    List<Nationality> mockNationalities;

    @Before
    public void setUp() {
        logger.info("Setting up test...");

        mockNationalities = Arrays.asList(
                new Nationality("USA", "en"),
                new Nationality("Canada", "en"),
                new Nationality("UK", "en")
        );
        when(nationalityService.getAllNationalities()).thenReturn(mockNationalities);

        logger.info("Test setup completed.");
    }

    @Test
    public void testGetAllNationalities() {
        logger.info("Starting test testGetAllNationalities...");

        ResponseEntity<List<Nationality>> response = nationalityRestController.getAllNationalities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockNationalities, response.getBody());

        verify(nationalityService, times(1)).getAllNationalities();

        logger.info("Test testGetAllNationalities passed successfully.");
    }
}
