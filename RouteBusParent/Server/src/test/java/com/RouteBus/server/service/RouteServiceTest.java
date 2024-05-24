package com.RouteBus.server.service;

import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Station;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    private Route existingRoute, updatedRoute;
    private Set<Station> stations;
    private Set<Bus> buses;

    @Before
    public void setUp() {
        stations = new HashSet<>(Arrays.asList(new Station("Station1", "Location1"), new Station("Station2", "Location2")));
        buses = new HashSet<>(Arrays.asList(new Bus("Bus1", 50, "Make1", "Model1"), new Bus("Bus2", 60, "Make2", "Model2")));

        existingRoute = new Route("testRoute", "startPoint", "endPoint", 100.0, new HashSet<>(), new HashSet<>());
        updatedRoute = new Route("testRoute", "newStartPoint", "newEndPoint", 200.0, stations, buses);
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

        Set<Route> result = routeService.getAllRoutes();
        assertEquals(new HashSet<>(routes), result);
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
    public void testCreateRoute_Error_SaveReturnsNull() {
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
    public void testCreateRoute_Error_AlreadyExists() {
        Route route = new Route();
        route.setName("testRoute");

        when(routeRepository.findById(route.getName())).thenReturn(Optional.of(route));

        RouteService.RouteServiceResult result = routeService.createRoute(route);
        assertEquals(RouteService.RouteServiceResult.ERROR, result);

        verify(routeRepository, times(1)).findById(route.getName());
        verify(routeRepository, times(0)).save(any(Route.class));
    }

    @Test
    public void testUpdateRoute_Success() {
        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals("newStartPoint", existingRoute.getStartPoint());
        assertEquals("newEndPoint", existingRoute.getEndPoint());
        assertEquals(200.0, existingRoute.getTotalDistance(), 0.0);
        assertEquals(stations, existingRoute.getStations());
        assertEquals(buses, existingRoute.getBuses());

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
    }

    @Test
    public void testUpdateRoute_NotFound() {
        when(routeRepository.findById("testRoute")).thenReturn(Optional.empty());

        RouteService.RouteServiceResult result = routeService.updateRoute(updatedRoute);
        assertEquals(RouteService.RouteServiceResult.NOT_FOUND, result);

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(0)).save(any(Route.class));
    }

    @Test
    public void testUpdateRoute_NoChanges() {
        Route noChangesRoute = new Route();
        noChangesRoute.setName("testRoute");
        noChangesRoute.setStartPoint("startPoint");
        noChangesRoute.setEndPoint("endPoint");
        noChangesRoute.setTotalDistance(100.0);
        noChangesRoute.setStations(new HashSet<>());
        noChangesRoute.setBuses(new HashSet<>());

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));

        RouteService.RouteServiceResult result = routeService.updateRoute(noChangesRoute);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(0)).save(existingRoute);
    }

    @Test
    public void testUpdateRoute_StationsChanged() {
        Route routeWithChangedStations = new Route();
        routeWithChangedStations.setName("testRoute");
        routeWithChangedStations.setStartPoint("startPoint");
        routeWithChangedStations.setEndPoint("endPoint");
        routeWithChangedStations.setTotalDistance(100.0);
        routeWithChangedStations.setStations(new HashSet<>(Arrays.asList(new Station("Station3", "Location3"))));
        routeWithChangedStations.setBuses(new HashSet<>());

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(routeWithChangedStations);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals(new HashSet<>(Arrays.asList(new Station("Station3", "Location3"))), existingRoute.getStations());

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
    }

    @Test
    public void testUpdateRoute_BusesChanged() {
        Route routeWithChangedBuses = new Route();
        routeWithChangedBuses.setName("testRoute");
        routeWithChangedBuses.setStartPoint("startPoint");
        routeWithChangedBuses.setEndPoint("endPoint");
        routeWithChangedBuses.setTotalDistance(100.0);
        routeWithChangedBuses.setStations(new HashSet<>());
        routeWithChangedBuses.setBuses(new HashSet<>(Arrays.asList(new Bus("Bus3", 70, "Make3", "Model3"))));

        when(routeRepository.findById("testRoute")).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        RouteService.RouteServiceResult result = routeService.updateRoute(routeWithChangedBuses);
        assertEquals(RouteService.RouteServiceResult.SUCCESS, result);

        assertEquals(new HashSet<>(Arrays.asList(new Bus("Bus3", 70, "Make3", "Model3"))), existingRoute.getBuses());

        verify(routeRepository, times(1)).findById("testRoute");
        verify(routeRepository, times(1)).save(existingRoute);
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

    @Test
    public void testGetRoutesByStations() {
        Route route1 = new Route();
        route1.setStartPoint("Origin");
        route1.setEndPoint("Destination");

        Route route2 = new Route();
        route2.setStartPoint("Origin");
        route2.setEndPoint("Destination");

        List<Route> routes = Arrays.asList(route1, route2);

        when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result = routeService.getRoutesByStations("Origin", "Destination");
        assertEquals(routes, result);
    }

    @Test
    public void testGetRoutesByStations_NoMatch() {
        Route route1 = new Route();
        route1.setStartPoint("Origin1");
        route1.setEndPoint("Destination1");

        Route route2 = new Route();
        route2.setStartPoint("Origin2");
        route2.setEndPoint("Destination2");

        List<Route> routes = Arrays.asList(route1, route2);

        when(routeRepository.findAll()).thenReturn(routes);

        List<Route> result = routeService.getRoutesByStations("Origin", "Destination");
        assertEquals(Collections.emptyList(), result);
    }
}
