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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RouteRestControllerTest {

    private static final Logger logger = LogManager.getLogger(RouteRestControllerTest.class);

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteRestController routeRestController;

    private Set<Route> mockRoutesSet;
    private List<Route> mockRoutesList;
    private Route mockRoute;

    @Before
    public void setUp() {
        mockRoutesSet = new HashSet<>();
        mockRoutesSet.add(new Route("Route1", "Start1", "End1", 100));
        mockRoutesSet.add(new Route("Route2", "Start2", "End2", 200));

        mockRoutesList = List.copyOf(mockRoutesSet);

        mockRoute = new Route("Route1", "Start1", "End1", 100);
    }

    @Test
    public void testGetAllRoutes() {
        when(routeService.getAllRoutes()).thenReturn(mockRoutesSet);
        ResponseEntity<Set<Route>> responseEntity = routeRestController.getAllRoutes();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockRoutesSet, responseEntity.getBody());
        logger.debug("Test getAllRoutes completed successfully.");
    }

    @Test
    public void testGetRouteByIdFound() {
        String name = "Route1";
        when(routeService.getRouteById(name)).thenReturn(mockRoute);
        ResponseEntity<Route> responseEntity = routeRestController.getRouteById(name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockRoute, responseEntity.getBody());
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
        when(routeService.obtainRoutesByBus(licensePlate)).thenReturn(mockRoutesList);
        List<Route> result = routeRestController.obtainRoutesByBus(licensePlate);
        assertEquals(mockRoutesList, result);
        logger.debug("Test obtainRoutesByBus completed successfully.");
    }

    @Test
    public void testGetRoutesByStations() {
        String origin = "Start1";
        String destination = "End1";
        when(routeService.getRoutesByStations(origin, destination)).thenReturn(mockRoutesList);
        
        ResponseEntity<List<Route>> responseEntity = routeRestController.getRoutesByStations(origin, destination);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockRoutesList, responseEntity.getBody());
        logger.debug("Test getRoutesByStations completed successfully.");
    }

    @Test
    public void testGetRoutesByStationsNotFound() {
        String origin = "NonExistingStart";
        String destination = "NonExistingEnd";
        when(routeService.getRoutesByStations(origin, destination)).thenReturn(null);
        
        ResponseEntity<List<Route>> responseEntity = routeRestController.getRoutesByStations(origin, destination);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        logger.debug("Test getRoutesByStationsNotFound completed successfully.");
    }
}
