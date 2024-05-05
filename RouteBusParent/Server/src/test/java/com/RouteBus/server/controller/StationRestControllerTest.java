package com.RouteBus.server.controller;

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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.StationService;

@ExtendWith(MockitoExtension.class)
class StationRestControllerTest {

    private static final Logger logger = LogManager.getLogger(StationRestControllerTest.class);

    @Mock
    StationService stationServiceMock;

    @InjectMocks
    StationRestController stationController;

    @BeforeEach
    void setUp() {
        // Configuración inicial, si es necesario
    }

    @Test
    void testGetAllStationes() {
        logger.debug("Starting testGetAllStationes");
        // Arrange
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("Station1", "Location1"));
        stations.add(new Station("Station2", "Location2"));
        when(stationServiceMock.getAllStations()).thenReturn(stations);

        // Act
        List<Station> result = stationController.getAllStationes();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Station1", result.get(0).getName());
        assertEquals("Location1", result.get(0).getLocation());
        assertEquals("Station2", result.get(1).getName());
        assertEquals("Location2", result.get(1).getLocation());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).getAllStations();
        logger.debug("Finishing testGetAllStationes");
    }

    @Test
    void testGetStationById_StationExists() {
        logger.debug("Starting testGetStationById_StationExists");
        // Arrange
        String stationName = "Station1";
        Station station = new Station(stationName, "Location1");
        when(stationServiceMock.getStationById(stationName)).thenReturn(station);

        // Act
        ResponseEntity<Station> response = stationController.getStationById(stationName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(station, response.getBody());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).getStationById(stationName);
        logger.debug("Finishing testGetStationById_StationExists");
    }

    @Test
    void testGetStationById_StationNotExists() {
        logger.debug("Starting testGetStationById_StationNotExists");
        // Arrange
        String stationName = "NonExistentStation";
        when(stationServiceMock.getStationById(stationName)).thenReturn(null);

        // Act
        ResponseEntity<Station> response = stationController.getStationById(stationName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).getStationById(stationName);
        logger.debug("Finishing testGetStationById_StationNotExists");
    }

    @Test
    void testCreateStation_Success() {
        logger.debug("Starting testCreateStation_Success");
        // Arrange
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.createStation(station)).thenReturn(StationService.StationServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> response = stationController.createStation(station);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station created successfully.", response.getBody());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).createStation(station);
        logger.debug("Finishing testCreateStation_Success");
    }

    @Test
    void testCreateStation_Error() {
        logger.debug("Starting testCreateStation_Error");
        // Arrange
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.createStation(station)).thenReturn(StationService.StationServiceResult.ERROR);

        // Act
        ResponseEntity<String> response = stationController.createStation(station);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create Station.", response.getBody());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).createStation(station);
        logger.debug("Finishing testCreateStation_Error");
    }

    @Test
    void testCreateStation_InternalServerError() {
        logger.debug("Starting testCreateStation_InternalServerError");
        // Arrange
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.createStation(station)).thenReturn(StationService.StationServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> response = stationController.createStation(station);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).createStation(station);
        logger.debug("Finishing testCreateStation_InternalServerError");
    }

    @Test
    void testUpdateStation_Success() {
        logger.debug("Starting testUpdateStation_Success");
        // Arrange
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.updateStation(station)).thenReturn(StationService.StationServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> response = stationController.updateStation(station);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station updated successfully.", response.getBody());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).updateStation(station);
        logger.debug("Finishing testUpdateStation_Success");
    }

    @Test
    void testUpdateStation_NotFound() {
        logger.debug("Starting testUpdateStation_NotFound");
        // Arrange
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.updateStation(station)).thenReturn(StationService.StationServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> response = stationController.updateStation(station);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).updateStation(station);
        logger.debug("Finishing testUpdateStation_NotFound");
    }

    @Test
    void testDeleteStation_Success() {
        logger.debug("Starting testDeleteStation_Success");
        // Arrange
        String stationName = "Station1";
        when(stationServiceMock.deleteStation(stationName)).thenReturn(StationService.StationServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> response = stationController.deleteStation(stationName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station deleted successfully.", response.getBody());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).deleteStation(stationName);
        logger.debug("Finishing testDeleteStation_Success");
    }

    @Test
    void testDeleteStation_NotFound() {
        logger.debug("Starting testDeleteStation_NotFound");
        // Arrange
        String stationName = "NonExistentStation";
        when(stationServiceMock.deleteStation(stationName)).thenReturn(StationService.StationServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> response = stationController.deleteStation(stationName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify mock interactions
        verify(stationServiceMock, times(1)).deleteStation(stationName);
        logger.debug("Finishing testDeleteStation_NotFound");
    }
}
