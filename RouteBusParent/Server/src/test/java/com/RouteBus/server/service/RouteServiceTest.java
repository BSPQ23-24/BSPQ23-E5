package com.RouteBus.server.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Station;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @Before
    public void setUp() {
    }

    @Test
    public void testGetRouteById() {
        String id = "testId";
        Route route = new Route();
        route.setName(id);
        when(routeRepository.findById(id)).thenReturn(Optional.of(route));

        Route result = routeService.getRouteById(id);
        assertEquals(route, result);
    }

    @Test
    public void testGetAllRoutes() {
        List<Route> routes = new ArrayList<>();
        routes.add(new Route());
        routes.add(new Route());
        when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result = routeService.getAllRoutes();
        assertEquals(routes, result);
    }

    @Test
    public void testCreateRoute_Success() {
        Route route = new Route();
        route.setName("testRoute");

        when(routeRepository.findById(route.getName())).thenReturn(Optional.empty());
        when(routeRepository.save(route)).thenReturn(route);

        RouteService.RouteServiceResult result = routeService.createRoute(route);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        verify(routeRepository, times(1)).findById(route.getName());
        verify(routeRepository, times(1)).save(route);
    }

    @Test
    public void testCreateRoute_Error() {
        Route route = new Route();
        route.setName("testRoute");

        when(routeRepository.findById(route.getName())).thenReturn(Optional.of(route));

        RouteService.RouteServiceResult result = routeService.createRoute(route);
        assertEquals(RouteService.RouteServiceResult.ERROR, result);

        verify(routeRepository, times(1)).findById(route.getName());
        verify(routeRepository, times(0)).save(any(Route.class));
    }
    
    @Test
    public void testUpdateRoute_OnlyStartPoint() {
        Route existingRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        Route updatedRoute = new Route("testRoute", "newStartPoint", "endPoint", 100.0);

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals("newStartPoint", existingRoute.getStartPoint());
        assertEquals("endPoint", existingRoute.getEndPoint());
        assertEquals(100.0, existingRoute.getTotalDistance(), 0.0);

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
    }

    @Test
    public void testCreateRoute_SaveSuccess() {
        Route route = new Route();
        route.setName("testRoute");

        when(routeRepository.findById(route.getName())).thenReturn(Optional.empty());
        when(routeRepository.save(route)).thenReturn(route);

        RouteService.RouteServiceResult result = routeService.createRoute(route);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        verify(routeRepository, times(1)).findById(route.getName());
        verify(routeRepository, times(1)).save(route);
    }


    @Test
    public void testUpdateRoute_OnlyStations() {
        Route existingRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        HashSet<Station> stations = new HashSet<>();
        Station s = new Station();
        s.setName("s");
        stations.add(s);
        Route updatedRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        updatedRoute.setStations(stations);

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals("startPoint", existingRoute.getStartPoint());
        assertEquals("endPoint", existingRoute.getEndPoint());
        assertEquals(100.0, existingRoute.getTotalDistance(), 0.0);
        assertEquals(stations, existingRoute.getStations());

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
    }

    @Test
    public void testUpdateRoute_OnlyBuses() {
        Route existingRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        HashSet<Bus> buses = new HashSet<>();
        Bus bus = new Bus();
        bus.setLicensePlate("12A");
        buses.add(bus);
        Route updatedRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        updatedRoute.setBuses(buses);

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals("startPoint", existingRoute.getStartPoint());
        assertEquals("endPoint", existingRoute.getEndPoint());
        assertEquals(100.0, existingRoute.getTotalDistance(), 0.0);
        assertEquals(buses, existingRoute.getBuses());

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
    }

    @Test
    public void testUpdateRoute_NoChange() {
        Route existingRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        Route updatedRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals("startPoint", existingRoute.getStartPoint());
        assertEquals("endPoint", existingRoute.getEndPoint());
        assertEquals(100.0, existingRoute.getTotalDistance(), 0.0);

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(0)).save(existingRoute);
    }
    
    @Test
    public void testCreateRoute_SaveError() {
        Route route = new Route();
        route.setName("testRoute");

        when(routeRepository.findById(route.getName())).thenReturn(Optional.empty());
        when(routeRepository.save(route)).thenReturn(null);

        RouteService.RouteServiceResult result = routeService.createRoute(route);
        assertEquals(RouteService.RouteServiceResult.ERROR, result);

        verify(routeRepository, times(1)).findById(route.getName());
        verify(routeRepository, times(1)).save(route);
    }

    @Test
    public void testUpdateRoute_Success() {
        Route existingRoute = new Route("testRoute", "startPoint", "endPoint", 100.0);
        Route updatedRoute = new Route("testRoute", "newStartPoint", "newEndPoint", 200.0);

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals("newStartPoint", existingRoute.getStartPoint());
        assertEquals("newEndPoint", existingRoute.getEndPoint());
        assertEquals(200.0, existingRoute.getTotalDistance(), 0.0);

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
    }

    @Test
    public void testUpdateRoute_NotFound() {
        Route updatedRoute = new Route("testRoute", "newStartPoint", "newEndPoint", 200.0);

        when(routeRepository.findById("testRoute")).thenReturn(Optional.empty());

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.NOT_FOUND, result);

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(0)).save(any(Route.class));
    }

    @Test
    public void testDeleteRoute_Success() {
        String id = "testRoute";
        Route routeToDelete = new Route();
        routeToDelete.setName(id);

        when(routeRepository.findById(id)).thenReturn(Optional.of(routeToDelete));

        RouteService.RouteServiceResult result = routeService.deleteRoute(id);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        verify(routeRepository, times(1)).findById(id);
        verify(routeRepository, times(1)).delete(routeToDelete);
    }

    @Test
    public void testDeleteRoute_NotFound() {
        String id = "testRoute";

        when(routeRepository.findById(id)).thenReturn(Optional.empty());

        RouteService.RouteServiceResult result = routeService.deleteRoute(id);
        assertEquals(RouteService.RouteServiceResult.NOT_FOUND, result);

        verify(routeRepository, times(1)).findById(id);
        verify(routeRepository, times(0)).delete(any(Route.class));
    }

    @Test
    public void testObtainRoutesByBus() {
        String licensePlate = "testLicensePlate";
        List<Route> routes = new ArrayList<>();
        routes.add(new Route());
        routes.add(new Route());

        when(routeRepository.findByBusesLicensePlate(licensePlate)).thenReturn(routes);

        List<Route> result = routeService.obtainRoutesByBus(licensePlate);
        assertEquals(routes, result);
    }
}
