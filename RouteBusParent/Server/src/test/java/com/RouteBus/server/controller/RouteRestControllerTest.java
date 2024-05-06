package com.RouteBus.server.controller;

import com.RouteBus.server.model.Route;
import com.RouteBus.server.service.RouteService;
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

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RouteRestControllerTest {

    private static final Logger logger = LogManager.getLogger(RouteRestControllerTest.class);

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteRestController routeRestController;

    private List<Route> mockRoutes;
    private Route mockRoute;

    @Before
    public void setUp() {
        mockRoutes = new ArrayList<>();
        mockRoutes.add(new Route("Route1", "Start1", "End1", 100));
        mockRoutes.add(new Route("Route2", "Start2", "End2", 200));
        mockRoute = new Route("Route1", "Start1", "End1", 100);
    }

    @Test
    public void testGetAllRoutes() {
        when(routeService.getAllRoutes()).thenReturn(mockRoutes);
        List<Route> result = routeRestController.getAllRoutes();
        assertEquals(mockRoutes, result);
        logger.debug("Test getAllRoutes completed successfully.");
    }

    @Test
    public void testGetRouteByIdFound() {
        String name = "Route1";
        Route expectedRoute = new Route("Route1", "Start1", "End1", 100);
        when(routeService.getRouteById(name)).thenReturn(expectedRoute);
        ResponseEntity<Route> responseEntity = routeRestController.getRouteById(name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRoute, responseEntity.getBody());
        logger.debug("Test getRouteByIdFound completed successfully.");
    }

    @Test
    public void testGetRouteByIdNotFound() {
        String name = "NonExistingRoute";
        when(routeService.getRouteById(name)).thenReturn(null);
        ResponseEntity<Route> responseEntity = routeRestController.getRouteById(name);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test getRouteByIdNotFound completed successfully.");
    }

    @Test
    public void testCreateRouteSuccess() {
        when(routeService.createRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.SUCCESS);
        ResponseEntity<String> responseEntity = routeRestController.createRoute(mockRoute);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Route created successfully.", responseEntity.getBody());
        logger.debug("Test createRouteSuccess completed successfully.");
    }
    

    @Test
    public void testCreateRouteError() {
        when(routeService.createRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.ERROR);
        ResponseEntity<String> responseEntity = routeRestController.createRoute(mockRoute);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to create Route.", responseEntity.getBody());
        logger.debug("Test createRouteError completed successfully.");
    }

    @Test
    public void testCreateRouteInternalServerError() {
        when(routeService.createRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.NOT_FOUND);
        ResponseEntity<String> responseEntity = routeRestController.createRoute(mockRoute);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error.", responseEntity.getBody());
        logger.debug("Test createRouteInternalServerError completed successfully.");
    }

    @Test
    public void testUpdateRouteSuccess() {
        when(routeService.updateRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.SUCCESS);
        ResponseEntity<String> responseEntity = routeRestController.updateRoute(mockRoute);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Route updated successfully.", responseEntity.getBody());
        logger.debug("Test updateRouteSuccess completed successfully.");
    }

    @Test
    public void testUpdateRouteNotFound() {
        when(routeService.updateRoute(mockRoute)).thenReturn(RouteService.RouteServiceResult.NOT_FOUND);
        ResponseEntity<String> responseEntity = routeRestController.updateRoute(mockRoute);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test updateRouteNotFound completed successfully.");
    }

    @Test
    public void testDeleteRouteSuccess() {
        String name = "Route1";
        when(routeService.deleteRoute(name)).thenReturn(RouteService.RouteServiceResult.SUCCESS);
        ResponseEntity<String> responseEntity = routeRestController.deleteRoute(name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Route deleted successfully.", responseEntity.getBody());
        logger.debug("Test deleteRouteSuccess completed successfully.");
    }

    @Test
    public void testDeleteRouteNotFound() {
        String name = "NonExistingRoute";
        when(routeService.deleteRoute(name)).thenReturn(RouteService.RouteServiceResult.NOT_FOUND);
        ResponseEntity<String> responseEntity = routeRestController.deleteRoute(name);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test deleteRouteNotFound completed successfully.");
    }

    @Test
    public void testObtainRoutesByBus() {
        String licensePlate = "ABC123";
        when(routeService.obtainRoutesByBus(licensePlate)).thenReturn(mockRoutes);
        List<Route> result = routeRestController.obtainRoutesByBus(licensePlate);
        assertEquals(mockRoutes, result);
        logger.debug("Test obtainRoutesByBus completed successfully.");
    }
}
