package com.RouteBus.server.controller;

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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.StationService;

@RunWith(MockitoJUnitRunner.class)
public class StationRestControllerTest {

    private static final Logger logger = LogManager.getLogger(StationRestControllerTest.class);

    @Mock
    private StationService stationServiceMock;

    @InjectMocks
    private StationRestController stationController;

    private Station mockStation;

    @Before
    public void setUp() {
        mockStation = new Station();
        mockStation.setName("Station1");
        mockStation.setLocation("Location1");
    }

    @Test
    public void testGetAllStationes() {
        Set<Station> stations = new HashSet<>();
        stations.add(mockStation);
        when(stationServiceMock.getAllStations()).thenReturn(stations);

        ResponseEntity<Set<Station>> response = stationController.getAllStationes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stations, response.getBody());
        verify(stationServiceMock).getAllStations();
        logger.debug("getAllStationes method test completed successfully.");
    }

    @Test
    public void testGetStationById_StationExists() {
        String stationName = "Station1";
        when(stationServiceMock.getStationById(stationName)).thenReturn(mockStation);

        ResponseEntity<Station> response = stationController.getStationById(stationName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStation, response.getBody());
        verify(stationServiceMock).getStationById(stationName);
        logger.debug("getStationById method test completed successfully for found case.");
    }

    @Test
    public void testGetStationById_StationNotExists() {
        String stationName = "NonExistentStation";
        when(stationServiceMock.getStationById(stationName)).thenReturn(null);

        ResponseEntity<Station> response = stationController.getStationById(stationName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(stationServiceMock).getStationById(stationName);
        logger.debug("getStationById method test completed successfully for not found case.");
    }

    @Test
    public void testCreateStation_Success() {
        when(stationServiceMock.createStation(mockStation)).thenReturn(StationService.StationServiceResult.SUCCESS);

        ResponseEntity<String> response = stationController.createStation(mockStation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station created successfully.", response.getBody());
        verify(stationServiceMock).createStation(mockStation);
        logger.debug("createStation method test completed successfully for SUCCESS case.");
    }

    @Test
    public void testCreateStation_Error() {
        when(stationServiceMock.createStation(mockStation)).thenReturn(StationService.StationServiceResult.ERROR);

        ResponseEntity<String> response = stationController.createStation(mockStation);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create Station.", response.getBody());
        verify(stationServiceMock).createStation(mockStation);
        logger.debug("createStation method test for ERROR case successful.");
    }

    @Test
    public void testCreateStation_InternalServerError() {
        when(stationServiceMock.createStation(mockStation)).thenReturn(StationService.StationServiceResult.OTHER);

        ResponseEntity<String> response = stationController.createStation(mockStation);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());
        verify(stationServiceMock).createStation(mockStation);
        logger.debug("createStation method test for INTERNAL_SERVER_ERROR case successful.");
    }

    @Test
    public void testUpdateStation_Success() {
        when(stationServiceMock.updateStation(mockStation)).thenReturn(StationService.StationServiceResult.SUCCESS);

        ResponseEntity<String> response = stationController.updateStation(mockStation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station updated successfully.", response.getBody());
        verify(stationServiceMock).updateStation(mockStation);
        logger.debug("updateStation method test completed successfully for SUCCESS case.");
    }

    @Test
    public void testUpdateStation_NotFound() {
        when(stationServiceMock.updateStation(mockStation)).thenReturn(StationService.StationServiceResult.NOT_FOUND);

        ResponseEntity<String> response = stationController.updateStation(mockStation);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stationServiceMock).updateStation(mockStation);
        logger.debug("updateStation method test completed successfully for NOT FOUND case.");
    }

    @Test
    public void testDeleteStation_Success() {
        String stationName = "Station1";
        when(stationServiceMock.deleteStation(stationName)).thenReturn(StationService.StationServiceResult.SUCCESS);

        ResponseEntity<String> response = stationController.deleteStation(stationName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station deleted successfully.", response.getBody());
        verify(stationServiceMock).deleteStation(stationName);
        logger.debug("deleteStation method test completed successfully for SUCCESS case.");
    }

    @Test
    public void testDeleteStation_NotFound() {
        String stationName = "NonExistentStation";
        when(stationServiceMock.deleteStation(stationName)).thenReturn(StationService.StationServiceResult.NOT_FOUND);

        ResponseEntity<String> response = stationController.deleteStation(stationName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stationServiceMock).deleteStation(stationName);
        logger.debug("deleteStation method test completed successfully for NOT FOUND case.");
    }
}
