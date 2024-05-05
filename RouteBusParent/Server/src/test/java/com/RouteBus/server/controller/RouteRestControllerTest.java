package com.RouteBus.server.controller;

import com.RouteBus.server.controller.RouteRestController;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.service.RouteService;
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
class RouteRestControllerTest {

    private static final Logger logger = LogManager.getLogger(RouteRestControllerTest.class);

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteRestController routeRestController;

    private List<Route> mockRoutes;
    private Route mockRoute;

    @BeforeEach
    void setUp() {
        mockRoutes = new ArrayList<>();
        mockRoutes.add(new Route("Route1", "Start1", "End1", 100));
        mockRoutes.add(new Route("Route2", "Start2", "End2", 200));

        mockRoute = new Route("Route1", "Start1", "End1", 100);
    }

    @Test
    void testGetAllRoutes() {
        // Arrange
        when(routeService.getAllRoutes()).thenReturn(mockRoutes);

        // Act
        List<Route> result = routeRestController.getAllRoutes();

        // Assert
        assertEquals(mockRoutes, result);

        // Logging
        logger.debug("Test getAllRoutes completed successfully.");
    }

    @Test
    void testGetRouteByIdFound() {
        // Arrange
        String name = "Route1";
        Route expectedRoute = new Route("Route1", "Start1", "End1", 100);
        when(routeService.getRouteById(name)).thenReturn(expectedRoute);

        // Act
        ResponseEntity<Route> responseEntity = routeRestController.getRouteById(name);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRoute, responseEntity.getBody());

        // Logging
        logger.debug("Test getRouteByIdFound completed successfully.");
    }

    @Test
    void testGetRouteByIdNotFound() {
        // Arrange
        String name = "NonExistingRoute";
        when(routeService.getRouteById(name)).thenReturn(null);

        // Act
        ResponseEntity<Route> responseEntity = routeRestController.getRouteById(name);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Logging
        logger.debug("Test getRouteByIdNotFound completed successfully.");
    }

    @Test
    void testCreateRouteSuccess() {
        // Arrange
        when(routeService.createRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.createRoute(mockRoute);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Route created successfully.", responseEntity.getBody());

        // Logging
        logger.debug("Test createRouteSuccess completed successfully.");
    }

    @Test
    void testCreateRouteError() {
        // Arrange
        when(routeService.createRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.ERROR);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.createRoute(mockRoute);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to create Route.", responseEntity.getBody());

        // Logging
        logger.debug("Test createRouteError completed successfully.");
    }

    @Test
    void testCreateRouteInternalServerError() {
        // Arrange
        when(routeService.createRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.createRoute(mockRoute);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error.", responseEntity.getBody());

        // Logging
        logger.debug("Test createRouteInternalServerError completed successfully.");
    }

    @Test
    void testUpdateRouteSuccess() {
        // Arrange
        when(routeService.updateRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.updateRoute(mockRoute);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Route updated successfully.", responseEntity.getBody());

        // Logging
        logger.debug("Test updateRouteSuccess completed successfully.");
    }

    @Test
    void testUpdateRouteNotFound() {
        // Arrange
        when(routeService.updateRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.updateRoute(mockRoute);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Logging
        logger.debug("Test updateRouteNotFound completed successfully.");
    }

    @Test
    void testDeleteRouteSuccess() {
        // Arrange
        String name = "Route1";
        when(routeService.deleteRoute(name)).thenReturn(RouteService.RouteServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.deleteRoute(name);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Route deleted successfully.", responseEntity.getBody());

        // Logging
        logger.debug("Test deleteRouteSuccess completed successfully.");
    }

    @Test
    void testDeleteRouteNotFound() {
        // Arrange
        String name = "NonExistingRoute";
        when(routeService.deleteRoute(name)).thenReturn(RouteService.RouteServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> responseEntity = routeRestController.deleteRoute(name);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Logging
        logger.debug("Test deleteRouteNotFound completed successfully.");
    }

    @Test
    void testObtainRoutesByBus() {
        // Arrange
        String licensePlate = "ABC123";
        when(routeService.obtainRoutesByBus(licensePlate)).thenReturn(mockRoutes);

        // Act
        List<Route> result = routeRestController.obtainRoutesByBus(licensePlate);

        // Assert
        assertEquals(mockRoutes, result);

        // Logging
        logger.debug("Test obtainRoutesByBus completed successfully.");
    }
}
