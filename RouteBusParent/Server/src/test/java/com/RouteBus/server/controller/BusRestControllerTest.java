package com.RouteBus.server.controller;

import com.RouteBus.server.model.Bus;
import com.RouteBus.server.service.BusService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusRestControllerTest {

    private static final Logger logger = LogManager.getLogger(BusRestControllerTest.class);

    @Mock
    private BusService busService;

    @InjectMocks
    private BusRestController busRestController;

    private List<Bus> mockBuses;
    private Bus mockBus;

    @Before
    public void setUp() {
        mockBuses = new ArrayList<>();
        mockBuses.add(new Bus("ABC123", 50, "Mercedes", "Sprinter"));
        mockBuses.add(new Bus("DEF456", 40, "Volvo", "B7R"));
        mockBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
    }

    @Test
    public void testGetAllBuses() {
        when(busService.getAllBuses()).thenReturn(mockBuses);
        Set<Bus> result = busRestController.getAllBuses();
        assertEquals(mockBuses, result);
        logger.debug("Test getAllBuses completed successfully.");
    }

    @Test
    public void testGetBusByIdFound() {
        String licensePlate = "ABC123";
        Bus expectedBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        when(busService.getBusById(licensePlate)).thenReturn(expectedBus);
        ResponseEntity<Bus> responseEntity = busRestController.getBusById(licensePlate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBus, responseEntity.getBody());
        logger.debug("Test getBusByIdFound completed successfully.");
    }

    @Test
    public void testGetBusByIdNotFound() {
        String licensePlate = "XYZ789";
        when(busService.getBusById(licensePlate)).thenReturn(null);
        ResponseEntity<Bus> responseEntity = busRestController.getBusById(licensePlate);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test getBusByIdNotFound completed successfully.");
    }

    @Test
    public void testCreateBusSuccess() {
        when(busService.createBus(mockBus)).thenReturn(BusService.BusServiceResult.SUCCESS);
        ResponseEntity<String> responseEntity = busRestController.createBus(mockBus);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bus created successfully.", responseEntity.getBody());
        logger.debug("Test createBusSuccess completed successfully.");
    }

    @Test
    public void testCreateBusError() {
        when(busService.createBus(mockBus)).thenReturn(BusService.BusServiceResult.ERROR);
        ResponseEntity<String> responseEntity = busRestController.createBus(mockBus);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to create bus.", responseEntity.getBody());
        logger.debug("Test createBusError completed successfully.");
    }

    @Test
    public void testCreateBusInternalServerError() {
        when(busService.createBus(mockBus)).thenReturn(BusService.BusServiceResult.NOT_FOUND);
        ResponseEntity<String> responseEntity = busRestController.createBus(mockBus);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error.", responseEntity.getBody());
        logger.debug("Test createBusInternalServerError completed successfully.");
    }

    @Test
    public void testUpdateBusSuccess() {
        when(busService.updateBus(mockBus)).thenReturn(BusService.BusServiceResult.SUCCESS);
        ResponseEntity<String> responseEntity = busRestController.updateBus(mockBus);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bus updated successfully.", responseEntity.getBody());
        logger.debug("Test updateBusSuccess completed successfully.");
    }

    @Test
    public void testUpdateBusNotFound() {
        when(busService.updateBus(mockBus)).thenReturn(BusService.BusServiceResult.NOT_FOUND);
        ResponseEntity<String> responseEntity = busRestController.updateBus(mockBus);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test updateBusNotFound completed successfully.");
    }

    @Test
    public void testDeleteBusSuccess() {
        String licensePlate = "ABC123";
        when(busService.deleteBus(licensePlate)).thenReturn(BusService.BusServiceResult.SUCCESS);
        ResponseEntity<String> responseEntity = busRestController.deleteBus(licensePlate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bus deleted successfully.", responseEntity.getBody());
        logger.debug("Test deleteBusSuccess completed successfully.");
    }

    @Test
    public void testDeleteBusNotFound() {
        String licensePlate = "XYZ789";
        when(busService.deleteBus(licensePlate)).thenReturn(BusService.BusServiceResult.NOT_FOUND);
        ResponseEntity<String> responseEntity = busRestController.deleteBus(licensePlate);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test deleteBusNotFound completed successfully.");
    }
}
