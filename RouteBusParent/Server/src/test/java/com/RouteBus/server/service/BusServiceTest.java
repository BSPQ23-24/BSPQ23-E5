package com.RouteBus.server.service;

import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.service.BusService.BusServiceResult;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    private final Logger logger = Logger.getLogger(BusServiceTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void testGetBusById() {
        String licensePlate = "ABC123";
        Bus bus = new Bus(licensePlate, 50, "Mercedes", "Sprinter");
        when(busRepository.findById(licensePlate)).thenReturn(Optional.of(bus));

        Bus result = busService.getBusById(licensePlate);
        assertEquals(bus, result);

        logger.info("getBusById test passed.");
    }

    @Test
    public void testGetAllBuses() {
        List<Bus> busList = List.of(
            new Bus("ABC123", 50, "Mercedes", "Sprinter"),
            new Bus("XYZ789", 60, "Volvo", "R3")
        );
        when(busRepository.findAll()).thenReturn(busList);

        Set<Bus> expectedBusSet = new HashSet<>(busList);
        Set<Bus> result = busService.getAllBuses();
        assertEquals(expectedBusSet, result);

        logger.info("getAllBuses test passed.");
    }

    @Test
    public void testCreateBus_BusDoesNotExist() {
        Bus newBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");

        when(busRepository.existsById(newBus.getLicensePlate())).thenReturn(false);
        when(busRepository.save(newBus)).thenReturn(newBus);

        BusServiceResult result = busService.createBus(newBus);
        assertEquals(BusServiceResult.SUCCESS, result);

        logger.info("createBus_BusDoesNotExist test passed.");
    }

    @Test
    public void testCreateBus_BusAlreadyExists() {
        Bus existingBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");

        when(busRepository.existsById(existingBus.getLicensePlate())).thenReturn(true);

        BusServiceResult result = busService.createBus(existingBus);
        assertEquals(BusServiceResult.ERROR, result);

        logger.info("createBus_BusAlreadyExists test passed.");
    }

    @Test
    public void testUpdateBus_BusFound() {
        Bus existingBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        Bus updatedBus = new Bus("ABC123", 60, "Volvo", "R3", new HashSet<Route>());
        when(busRepository.findById("ABC123")).thenReturn(Optional.of(existingBus));

        BusServiceResult result = busService.updateBus(updatedBus);
        assertEquals(BusServiceResult.SUCCESS, result);
        assertEquals(60, existingBus.getCapacity());
        assertEquals("Volvo", existingBus.getMake());
        assertEquals("R3", existingBus.getModel());
        verify(busRepository, times(1)).findById("ABC123");
        verify(busRepository, times(1)).save(existingBus);

        logger.info("updateBus_BusFound test passed.");
    }

    @Test
    public void testUpdateBus_NoChange() {
        Bus existingBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        Bus updatedBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        when(busRepository.findById("ABC123")).thenReturn(Optional.of(existingBus));

        BusServiceResult result = busService.updateBus(updatedBus);
        assertEquals(BusServiceResult.SUCCESS, result);
        verify(busRepository, times(1)).findById("ABC123");
        verify(busRepository, never()).save(any(Bus.class));

        logger.info("updateBus_NoChange test passed.");
    }

    @Test
    public void testUpdateBus_BusNotFound() {
        Bus updatedBus = new Bus("ABC123", 60, "Mercedes", "Sprinter");
        when(busRepository.findById("ABC123")).thenReturn(Optional.empty());

        BusServiceResult result = busService.updateBus(updatedBus);
        assertEquals(BusServiceResult.NOT_FOUND, result);

        logger.info("updateBus_BusNotFound test passed.");
    }

    @Test
    public void testDeleteBus_BusFound() {
        String licensePlate = "ABC123";

        when(busRepository.existsById(licensePlate)).thenReturn(true);

        BusServiceResult result = busService.deleteBus(licensePlate);
        assertEquals(BusServiceResult.SUCCESS, result);

        logger.info("deleteBus_BusFound test passed.");
    }

    @Test
    public void testDeleteBus_BusNotFound() {
        String licensePlate = "ABC123";

        when(busRepository.existsById(licensePlate)).thenReturn(false);

        BusServiceResult result = busService.deleteBus(licensePlate);
        assertEquals(BusServiceResult.NOT_FOUND, result);

        logger.info("deleteBus_BusNotFound test passed.");
    }
}
