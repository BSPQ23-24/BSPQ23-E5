package com.RouteBus.server.controller;

import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.service.NationalityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NationalityRestControllerTest {

    private static final Logger logger = LogManager.getLogger(NationalityRestControllerTest.class);

    @Mock
    private NationalityService nationalityService;

    @InjectMocks
    private NationalityRestController nationalityRestController;

    private List<Nationality> mockNationalities;

    @BeforeEach
    void setUp() {
        mockNationalities = new ArrayList<>();
        mockNationalities.add(new Nationality("USA", "English"));
        mockNationalities.add(new Nationality("Germany", "German"));
    }
    
    @Test
    void testGetAllNationalities() {
    	logger.debug("Testing getAllNationalities...");
        // Arrange
        when(nationalityService.getAllNationalities()).thenReturn(mockNationalities);

        // Act
        ResponseEntity<List<Nationality>> responseEntity = nationalityRestController.getAllNationalities();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNationalities, responseEntity.getBody());

        // Logging
        logger.debug("Test getAllNationalities completed successfully.");
    }
}
