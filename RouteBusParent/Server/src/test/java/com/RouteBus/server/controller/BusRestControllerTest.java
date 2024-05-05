package com.RouteBus.server.controller;

import com.RouteBus.server.model.Bus;
import com.RouteBus.server.service.BusService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusRestControllerTest {

    private static final Logger logger = LogManager.getLogger(BusRestControllerTest.class);

    @Mock
    private BusService busService;

    @InjectMocks
    private BusRestController busRestController;

    private List<Bus> mockBuses;
    private Bus mockBus;

    @BeforeEach
    void setUp() {
        mockBuses = new ArrayList<>();
        mockBuses.add(new Bus("ABC123", 50, "Mercedes", "Sprinter"));
        mockBuses.add(new Bus("DEF456", 40, "Volvo", "B7R"));

        mockBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
    }

    @Test
    void testGetAllBuses() {
        // Arrange
        when(busService.getAllBuses()).thenReturn(mockBuses);

        // Act
        List<Bus> result = busRestController.getAllBuses();

        // Assert
        assertEquals(mockBuses, result);

        // Logging
        logger.debug("Test getAllBuses completed successfully.");
    }

    @Test
    void testGetBusByIdFound() {
        // Arrange
        String licensePlate = "ABC123";
        Bus expectedBus = new Bus("ABC123", 50, "Mercedes", "Sprinter");
        when(busService.getBusById(licensePlate)).thenReturn(expectedBus);

        // Act
        ResponseEntity<Bus> responseEntity = busRestController.getBusById(licensePlate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBus, responseEntity.getBody());

        // Logging
        logger.debug("Test getBusByIdFound completed successfully.");
    }

    @Test
    void testGetBusByIdNotFound() {
        // Arrange
        String licensePlate = "XYZ789";
        when(busService.getBusById(licensePlate)).thenReturn(null);

        // Act
        ResponseEntity<Bus> responseEntity = busRestController.getBusById(licensePlate);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Logging
        logger.debug("Test getBusByIdNotFound completed successfully.");
    }

    @Test
    void testCreateBusSuccess() {
        // Arrange
        when(busService.createBus(mockBus)).thenReturn(BusService.BusServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> responseEntity = busRestController.createBus(mockBus);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bus created successfully.", responseEntity.getBody());

        // Logging
        logger.debug("Test createBusSuccess completed successfully.");
    }

    @Test
    void testCreateBusError() {
        // Arrange
        when(busService.createBus(mockBus)).thenReturn(BusService.BusServiceResult.ERROR);

        // Act
        ResponseEntity<String> responseEntity = busRestController.createBus(mockBus);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to create bus.", responseEntity.getBody());

        // Logging
        logger.debug("Test createBusError completed successfully.");
    }

    @Test
    void testCreateBusInternalServerError() {
        // Arrange
        when(busService.createBus(mockBus)).thenReturn(BusService.BusServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> responseEntity = busRestController.createBus(mockBus);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error.", responseEntity.getBody());

        // Logging
        logger.debug("Test createBusInternalServerError completed successfully.");
    }

    @Test
    void testUpdateBusSuccess() {
        // Arrange
        when(busService.updateBus(mockBus)).thenReturn(BusService.BusServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> responseEntity = busRestController.updateBus(mockBus);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bus updated successfully.", responseEntity.getBody());

        // Logging
        logger.debug("Test updateBusSuccess completed successfully.");
    }

    @Test
    void testUpdateBusNotFound() {
        // Arrange
        when(busService.updateBus(mockBus)).thenReturn(BusService.BusServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> responseEntity = busRestController.updateBus(mockBus);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Logging
        logger.debug("Test updateBusNotFound completed successfully.");
    }

    @Test
    void testDeleteBusSuccess() {
        // Arrange
        String licensePlate = "ABC123";
        when(busService.deleteBus(licensePlate)).thenReturn(BusService.BusServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> responseEntity = busRestController.deleteBus(licensePlate);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Bus deleted successfully.", responseEntity.getBody());

        // Logging
        logger.debug("Test deleteBusSuccess completed successfully.");
    }

    @Test
    void testDeleteBusNotFound() {
        // Arrange
        String licensePlate = "XYZ789";
        when(busService.deleteBus(licensePlate)).thenReturn(BusService.BusServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> responseEntity = busRestController.deleteBus(licensePlate);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Logging
        logger.debug("Test deleteBusNotFound completed successfully.");
    }
}
