package com.RouteBus.server.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.RouteBus.server.dao.StationRepository;
import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.StationService.StationServiceResult;

@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    private final Logger logger = Logger.getLogger(StationServiceTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void testGetAllStations() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("Station1", "Location1"));
        stations.add(new Station("Station2", "Location2"));

        when(stationRepository.findAll()).thenReturn(stations);

        Set<Station> result = stationService.getAllStations();
        assertEquals(new HashSet<>(stations), result);

        logger.info("getAllStations test passed.");
    }

    @Test
    public void testCreateStation_StationDoesNotExist() {
        Station newStation = new Station("Station1", "Location1");

        when(stationRepository.findById(newStation.getName())).thenReturn(Optional.empty());
        when(stationRepository.save(newStation)).thenReturn(newStation);

        StationServiceResult result = stationService.createStation(newStation);
        assertEquals(StationServiceResult.SUCCESS, result);

        verify(stationRepository, times(1)).findById(newStation.getName());
        verify(stationRepository, times(1)).save(newStation);

        logger.info("createStation_StationDoesNotExist test passed.");
    }

    @Test
    public void testCreateStation_StationAlreadyExists() {
        Station existingStation = new Station("Station1", "Location1");

        when(stationRepository.findById(existingStation.getName())).thenReturn(Optional.of(existingStation));

        StationServiceResult result = stationService.createStation(existingStation);
        assertEquals(StationServiceResult.ERROR, result);

        verify(stationRepository, times(1)).findById(existingStation.getName());
        verify(stationRepository, times(0)).save(existingStation);

        logger.info("createStation_StationAlreadyExists test passed.");
    }

    @Test
    public void testCreateStation_SaveError() {
        Station newStation = new Station("Station1", "Location1");

        when(stationRepository.findById(newStation.getName())).thenReturn(Optional.empty());
        when(stationRepository.save(newStation)).thenReturn(null);

        StationServiceResult result = stationService.createStation(newStation);
        assertEquals(StationServiceResult.ERROR, result);

        verify(stationRepository, times(1)).findById(newStation.getName());
        verify(stationRepository, times(1)).save(newStation);

        logger.info("createStation_SaveError test passed.");
    }

    @Test
    public void testGetStationById_Exists() {
        String name = "Station1";
        Station station = new Station(name, "Location1");
        when(stationRepository.findById(name)).thenReturn(Optional.of(station));

        Station result = stationService.getStationById(name);
        assertEquals(station, result);

        logger.info("getStationById_Exists test passed.");
    }

    @Test
    public void testGetStationById_NotExists() {
        String name = "NonExistingStation";
        when(stationRepository.findById(name)).thenReturn(Optional.empty());

        Station result = stationService.getStationById(name);
        assertEquals(null, result);

        logger.info("getStationById_NotExists test passed.");
    }

    @Test
    public void testUpdateStation_StationFound() {
        Station existingStation = new Station("Station1", "Location1");
        Station updatedStation = new Station("Station1", "Location2");
        updatedStation.setRoutes(new HashSet<>());

        when(stationRepository.findById("Station1")).thenReturn(Optional.of(existingStation));
        when(stationRepository.save(existingStation)).thenReturn(existingStation);

        StationServiceResult result = stationService.updateStation(updatedStation);
        assertEquals(StationServiceResult.SUCCESS, result);

        assertEquals("Location2", existingStation.getLocation());
        assertEquals(new HashSet<>(), existingStation.getRoutes());

        verify(stationRepository, times(1)).findById("Station1");
        verify(stationRepository, times(1)).save(existingStation);

        logger.info("updateStation_StationFound test passed.");
    }

    @Test
    public void testUpdateStation_NoChange() {
        Station existingStation = new Station("Station1", "Location1");
        Station updatedStation = new Station("Station1", "Location1");

        when(stationRepository.findById("Station1")).thenReturn(Optional.of(existingStation));

        StationServiceResult result = stationService.updateStation(updatedStation);
        assertEquals(StationServiceResult.SUCCESS, result);

        verify(stationRepository, times(1)).findById("Station1");
        verify(stationRepository, times(0)).save(existingStation);

        logger.info("updateStation_NoChange test passed.");
    }

    @Test
    public void testUpdateStation_StationNotFound() {
        Station updatedStation = new Station("NonExistingStation", "Location1");

        when(stationRepository.findById("NonExistingStation")).thenReturn(Optional.empty());

        StationServiceResult result = stationService.updateStation(updatedStation);
        assertEquals(StationServiceResult.NOT_FOUND, result);

        verify(stationRepository, times(1)).findById("NonExistingStation");
        verify(stationRepository, times(0)).save(updatedStation);

        logger.info("updateStation_StationNotFound test passed.");
    }

    @Test
    public void testDeleteStation_StationFound() {
        String name = "Station1";
        Station stationToDelete = new Station(name, "Location1");

        when(stationRepository.findById(name)).thenReturn(Optional.of(stationToDelete));

        StationServiceResult result = stationService.deleteStation(name);
        assertEquals(StationServiceResult.SUCCESS, result);

        verify(stationRepository, times(1)).findById(name);
        verify(stationRepository, times(1)).delete(stationToDelete);

        logger.info("deleteStation_StationFound test passed.");
    }

    @Test
    public void testDeleteStation_StationNotFound() {
        String name = "NonExistingStation";

        when(stationRepository.findById(name)).thenReturn(Optional.empty());

        StationServiceResult result = stationService.deleteStation(name);
        assertEquals(StationServiceResult.NOT_FOUND, result);

        verify(stationRepository, times(1)).findById(name);
        verify(stationRepository, times(0)).delete(any(Station.class));

        logger.info("deleteStation_StationNotFound test passed.");
    }
}
