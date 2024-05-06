package com.RouteBus.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.StationService;

@RunWith(MockitoJUnitRunner.class)
public class StationRestControllerTest {

    private static final Logger logger = LogManager.getLogger(StationRestControllerTest.class);

    @Mock
    private StationService stationServiceMock;

    @InjectMocks
    private StationRestController stationController;


    @Test
    public void testGetAllStationes() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("Station1", "Location1"));
        stations.add(new Station("Station2", "Location2"));
        when(stationServiceMock.getAllStations()).thenReturn(stations);
        List<Station> result = stationController.getAllStationes();
        assertEquals(2, result.size());
        assertEquals("Station1", result.get(0).getName());
        assertEquals("Location1", result.get(0).getLocation());
        assertEquals("Station2", result.get(1).getName());
        assertEquals("Location2", result.get(1).getLocation());
        verify(stationServiceMock, times(1)).getAllStations();
        logger.debug("Finishing testGetAllStationes");
    }

    @Test
    public void testGetStationById_StationExists() {
        String stationName = "Station1";
        Station station = new Station(stationName, "Location1");
        when(stationServiceMock.getStationById(stationName)).thenReturn(station);
        ResponseEntity<Station> response = stationController.getStationById(stationName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(station, response.getBody());
        verify(stationServiceMock, times(1)).getStationById(stationName);
        logger.debug("Finishing testGetStationById_StationExists");
    }

    @Test
    public void testGetStationById_StationNotExists() {
        String stationName = "NonExistentStation";
        when(stationServiceMock.getStationById(stationName)).thenReturn(null);
        ResponseEntity<Station> response = stationController.getStationById(stationName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stationServiceMock, times(1)).getStationById(stationName);
        logger.debug("Finishing testGetStationById_StationNotExists");
    }

    @Test
    public void testCreateStation_Success() {
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.createStation(station)).thenReturn(StationService.StationServiceResult.SUCCESS);
        ResponseEntity<String> response = stationController.createStation(station);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station created successfully.", response.getBody());
        verify(stationServiceMock, times(1)).createStation(station);
        logger.debug("Finishing testCreateStation_Success");
    }

    @Test
    public void testCreateStation_Error() {
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.createStation(station)).thenReturn(StationService.StationServiceResult.ERROR);
        ResponseEntity<String> response = stationController.createStation(station);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create Station.", response.getBody());
        verify(stationServiceMock, times(1)).createStation(station);
        logger.debug("Finishing testCreateStation_Error");
    }

    @Test
    public void testCreateStation_InternalServerError() {
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.createStation(station)).thenReturn(StationService.StationServiceResult.NOT_FOUND);
        ResponseEntity<String> response = stationController.createStation(station);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());
        verify(stationServiceMock, times(1)).createStation(station);
        logger.debug("Finishing testCreateStation_InternalServerError");
    }

    @Test
    public void testUpdateStation_Success() {
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.updateStation(station)).thenReturn(StationService.StationServiceResult.SUCCESS);
        ResponseEntity<String> response = stationController.updateStation(station);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station updated successfully.", response.getBody());
        verify(stationServiceMock, times(1)).updateStation(station);
        logger.debug("Finishing testUpdateStation_Success");
    }

    @Test
    public void testUpdateStation_NotFound() {
        Station station = new Station("Station1", "Location1");
        when(stationServiceMock.updateStation(station)).thenReturn(StationService.StationServiceResult.NOT_FOUND);
        ResponseEntity<String> response = stationController.updateStation(station);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stationServiceMock, times(1)).updateStation(station);
        logger.debug("Finishing testUpdateStation_NotFound");
    }

    @Test
    public void testDeleteStation_Success() {
        String stationName = "Station1";
        when(stationServiceMock.deleteStation(stationName)).thenReturn(StationService.StationServiceResult.SUCCESS);
        ResponseEntity<String> response = stationController.deleteStation(stationName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Station deleted successfully.", response.getBody());
        verify(stationServiceMock, times(1)).deleteStation(stationName);
        logger.debug("Finishing testDeleteStation_Success");
    }

    @Test
    public void testDeleteStation_NotFound() {
        String stationName = "NonExistentStation";
        when(stationServiceMock.deleteStation(stationName)).thenReturn(StationService.StationServiceResult.NOT_FOUND);
        ResponseEntity<String> response = stationController.deleteStation(stationName);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stationServiceMock, times(1)).deleteStation(stationName);
        logger.debug("Finishing testDeleteStation_NotFound");
    }
}
