package com.RouteBus.server.controller;

import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.service.NationalityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NationalityRestControllerTest {

    private static final Logger logger = LogManager.getLogger(NationalityRestControllerTest.class);

    @Mock
    private NationalityService nationalityService;

    @InjectMocks
    private NationalityRestController nationalityRestController;

    private Set<Nationality> mockNationalities;

    @Before
    public void setUp() {
        mockNationalities = new HashSet<>();
        mockNationalities.add(new Nationality("USA", "English"));
        mockNationalities.add(new Nationality("Germany", "German"));
    }

    @Test
    public void testGetAllNationalities() {
        when(nationalityService.getAllNationalities()).thenReturn(mockNationalities);
        ResponseEntity<Set<Nationality>> responseEntity = nationalityRestController.getAllNationalities();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNationalities, responseEntity.getBody());
        logger.debug("Test getAllNationalities completed successfully.");
    }
}
