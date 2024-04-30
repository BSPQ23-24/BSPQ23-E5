package com.RouteBus.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.dao.StationRepository;
import com.RouteBus.server.dao.TicketRepository;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Station;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.model.User;
import com.RouteBus.server.service.BusService.BusServiceResult;

@RunWith(MockitoJUnitRunner.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private BusService busService;

    // Setup method
    @Before
    public void setup() {
        // Mocking behavior for repositories if needed
    	
    }

    // Test cases for getBusById method
    @Test
    public void testGetBusById() {
        // Mocking data
        Bus bus = new Bus();
        bus.setId(1);
        Optional<Bus> optionalBus = Optional.of(bus);
        doReturn(optionalBus).when(busRepository).findById(1);

        // Test
        Bus result = busService.getBusById(1);

        // Assertion
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    // Test cases for getAllBuses method
    @Test
    public void testGetAllBuses() {
        // Mocking data
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus());
        buses.add(new Bus());
        doReturn(buses).when(busRepository).findAll();

        // Test
        List<Bus> result = busService.getAllBuses();

        // Assertion
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // Test cases for createBus method
    @Test
    public void testCreateBus() {
        // Mocking data
        Bus bus = new Bus();
        Bus bus2 = new Bus();
        bus2.setId(1);
        bus.setId(1);
        doReturn(Optional.empty()).when(busRepository).findById(1);
        doReturn(bus).when(busRepository).save(bus);

        // Test
        BusService.BusServiceResult result = busService.createBus(bus);
        BusService.BusServiceResult result2 = busService.createBus(bus2);
        // Assertion
        assertEquals(BusService.BusServiceResult.SUCCESS, result);
        verify(busRepository).save(bus);
    }
    
    @Test
    public void testUpdateBusSuccess() {
        // Preparar
        Bus existingBus = new Bus();
        existingBus.setCapacity(40);
        existingBus.setDriver("John Doe");

        when(busRepository.findById(1)).thenReturn(Optional.of(existingBus));
        when(busRepository.save(any(Bus.class))).thenReturn(existingBus);
        
        // Actuar
        Bus updateInfo = new Bus();
        updateInfo.setCapacity(50);
        updateInfo.setDriver("Jane Doe");
        BusServiceResult result = busService.updateBus(updateInfo, 1);
        BusServiceResult result2 = busService.updateBus(updateInfo, 1921);
        // Verificar
        assertEquals(BusServiceResult.SUCCESS, result);
        verify(busRepository).save(existingBus);
        assertEquals(50, existingBus.getCapacity());
        assertEquals("Jane Doe", existingBus.getDriver());
    }
    
    
    @Test
    public void testDeleteBusSuccess() {
        Bus bus = new Bus();
        when(busRepository.findById(0)).thenReturn(Optional.of(bus));
        doNothing().when(busRepository).delete(bus);
        when(busRepository.findById(0)).thenReturn(Optional.empty());
        busService.createBus(bus);
        BusServiceResult result = busService.deleteBus(0);

        assertEquals(BusServiceResult.SUCCESS, result);
        verify(busRepository).delete(bus);
    }

    @Test
    public void testDeleteBusFailWhenBusNotFound() {
        when(busRepository.findById(1)).thenReturn(Optional.empty());

        BusServiceResult result = busService.deleteBus(1);

        assertEquals(BusServiceResult.FAIL, result);
        verify(busRepository, never()).delete(any(Bus.class));
    }

    @Test
    public void testDeleteAllBusesSuccess() {
        List<Bus> buses = Arrays.asList(new Bus(), new Bus());
        when(busRepository.findAll()).thenReturn(buses);
        when(busRepository.findById(anyInt())).thenReturn(Optional.empty());

        BusServiceResult result = busService.deleteAllBuses();

        assertEquals(BusServiceResult.SUCCESS, result);
        verify(busRepository, times(buses.size())).deleteById(anyInt());
    }

    @Test
    public void testDeleteAllBusesFail() {
        List<Bus> buses = Arrays.asList(new Bus(), new Bus());
        when(busRepository.findAll()).thenReturn(buses);
        // Simulamos que la primera llamada a findById retorna un Optional con Bus y la segunda vez un Optional vacío
        when(busRepository.findById(anyInt())).thenReturn(Optional.of(new Bus()), Optional.empty());

        BusServiceResult result = busService.deleteAllBuses();

        assertEquals(BusServiceResult.FAIL, result);
        verify(busRepository, times(buses.size())).deleteById(anyInt());
    }

    @Test
    public void testGetRouteByIdFound() {
        Route route = new Route();
        when(routeRepository.findById(1)).thenReturn(Optional.of(route));

        Route foundRoute = busService.getRouteById(1);

        assertNotNull(foundRoute);
        assertEquals(route, foundRoute);
    }

    @Test
    public void testGetRouteByIdNotFound() {
        when(routeRepository.findById(1)).thenReturn(Optional.empty());

        Route foundRoute = busService.getRouteById(1);

        assertNull(foundRoute);
    }

    @Test
    public void testGetAllRoutes() {
        List<Route> routes = Arrays.asList(new Route(), new Route());
        when(routeRepository.findAll()).thenReturn(routes);

        List<Route> foundRoutes = busService.getAllRoutes();

        assertNotNull(foundRoutes);
        assertEquals(2, foundRoutes.size());
        assertEquals(routes, foundRoutes);
    }
    
    @Test
    public void testCreateRoute() {
        Route route = new Route();
        route.setId(1);
        when(routeRepository.findById(route.getId())).thenReturn(Optional.empty());
        when(routeRepository.save(route)).thenReturn(route);

        assertEquals(BusServiceResult.SUCCESS, busService.createRoute(route));
        verify(routeRepository).save(route);
    }

    // Test updating an existing route
    @Test
    public void testUpdateRoute() {
        Route existingRoute = new Route();
        existingRoute.setId(1);
        when(routeRepository.findById(existingRoute.getId())).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(existingRoute)).thenReturn(existingRoute);

        Route updateInfo = new Route();
        updateInfo.setBuses(new HashSet<Bus>()); // Assume setters
        updateInfo.setStations(new HashSet<Station>());
        updateInfo.setTotalDistance(100);

        assertEquals(BusServiceResult.SUCCESS, busService.updateRoute(updateInfo, existingRoute.getId()));
        verify(routeRepository).save(existingRoute);
    }

    // Test deleting a route
    @Test
    public void testDeleteRoute() {
        Route route = new Route();
        route.setId(1);
        when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        doNothing().when(routeRepository).delete(route);
        when(routeRepository.findById(route.getId())).thenReturn(Optional.empty());

        assertEquals(BusServiceResult.SUCCESS, busService.deleteRoute(route.getId()));
        verify(routeRepository).delete(route);
    }
    
    @Test
    public void testDeleteAllRoutes() {
        Route route1 = new Route();
        route1.setId(1);
        Route route2 = new Route();
        route2.setId(2);
        List<Route> routes = Arrays.asList(route1, route2);

        when(routeRepository.findAll()).thenReturn(routes);
        when(routeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertEquals(BusServiceResult.SUCCESS, busService.deleteAllRoutes());
        verify(routeRepository, times(routes.size())).deleteById(anyInt());
    }

    @Test
    public void testGetStationByIdFound() {
        Station station = new Station();
        when(stationRepository.findById(1)).thenReturn(Optional.of(station));

        assertEquals(station, busService.getStationById(1));
    }

    @Test
    public void testGetStationByIdNotFound() {
        when(stationRepository.findById(1)).thenReturn(Optional.empty());

        assertNull(busService.getStationById(1));
    }

    @Test
    public void testGetAllStations() {
        List<Station> stations = Arrays.asList(new Station(), new Station());
        when(stationRepository.findAll()).thenReturn(stations);

        List<Station> foundStations = busService.getAllStations();

        assertNotNull(foundStations);
        assertEquals(2, foundStations.size());
        assertArrayEquals(stations.toArray(), foundStations.toArray());
    }

    @Test
    public void testCreateStation() {
        Station newStation = new Station();
        newStation.setId(1);
        when(stationRepository.findById(newStation.getId())).thenReturn(Optional.empty());
        when(stationRepository.save(newStation)).thenReturn(newStation);

        assertEquals(BusServiceResult.SUCCESS, busService.createStation(newStation));
        verify(stationRepository).save(newStation);
    }

    @Test
    public void testCreateStationFailure() {
        Station existingStation = new Station();
        existingStation.setId(1);
        when(stationRepository.findById(existingStation.getId())).thenReturn(Optional.of(existingStation));

        assertEquals(BusServiceResult.FAIL, busService.createStation(existingStation));
        verify(stationRepository, never()).save(existingStation);
    }
   
    @Test
    public void testUpdateStationSuccess() {
        Station existingStation = new Station();
        existingStation.setId(1);
        existingStation.setLocation("Old Location");
        existingStation.setName("Old Name");

        Station newDetails = new Station();
        newDetails.setLocation("New Location");
        newDetails.setName("New Name");

        when(stationRepository.findById(1)).thenReturn(Optional.of(existingStation));
        when(stationRepository.save(existingStation)).thenReturn(existingStation);

        assertEquals(BusServiceResult.SUCCESS, busService.updateStation(newDetails, 1));
        verify(stationRepository).save(existingStation);
        assertEquals("New Location", existingStation.getLocation());
        assertEquals("New Name", existingStation.getName());
    }
    
    @Test
    public void testDeleteStationSuccess() {
        Station station = new Station();
        station.setId(1);
        when(stationRepository.findById(1)).thenReturn(Optional.of(station));
        doNothing().when(stationRepository).delete(station);
        when(stationRepository.findById(1)).thenReturn(Optional.empty());

        assertEquals(BusServiceResult.SUCCESS, busService.deleteStation(1));
        verify(stationRepository).delete(station);
    }

    @Test
    public void testDeleteAllStationsSuccess() {
        List<Station> stations = Arrays.asList(new Station(), new Station());
        when(stationRepository.findAll()).thenReturn(stations);
        doNothing().when(stationRepository).deleteById(anyInt());
        when(stationRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertEquals(BusServiceResult.SUCCESS, busService.deleteAllStations());
        verify(stationRepository, times(stations.size())).deleteById(anyInt());
    }
    
    @Test
    public void testCreateTicketSuccess() {
        Ticket newTicket = new Ticket();
        when(ticketRepository.findById(newTicket.getId())).thenReturn(Optional.empty());
        when(ticketRepository.save(newTicket)).thenReturn(newTicket);

        assertEquals(BusServiceResult.SUCCESS, busService.createTicket(newTicket));
        verify(ticketRepository).save(newTicket);
    }

    @Test
    public void testUpdateTicketSuccess() {
        Ticket existingTicket = new Ticket();
        User user= new User();
        Ticket newDetails = new Ticket();
        newDetails.setClient(user);
        newDetails.setDate(new java.util.Date());
        newDetails.setDestination("Destination");
        newDetails.setPrice(100.0);

        when(ticketRepository.findById(existingTicket.getId())).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(existingTicket)).thenReturn(existingTicket);

        assertEquals(BusServiceResult.SUCCESS, busService.updateTicket(newDetails, 1L));
        verify(ticketRepository).save(existingTicket);
    }

    @Test
    public void testGetAllTickets() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> foundTickets = busService.getAllTickets();

        assertNotNull(foundTickets);
        assertEquals(2, foundTickets.size());
        assertEquals(tickets, foundTickets);
    }
    
    @Test
    public void testDeleteTicketSuccess() {
        Ticket ticket = new Ticket();
        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).delete(ticket);
        //when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(null));
        assertEquals(BusServiceResult.SUCCESS, busService.deleteTicket(ticket.getId()));
        //verify(ticketRepository).delete(ticket);
    }

    @Test
    public void testDeleteTicketFailure() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertEquals(BusServiceResult.FAIL, busService.deleteTicket(1L));
    }

    @Test
    public void testDeleteAllTickets() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);
        doNothing().when(ticketRepository).deleteById(anyLong());
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertEquals(BusServiceResult.SUCCESS, busService.deleteAllTickets());
    }

//    @Test
//    public void testAddStationToRouteSuccess() {
//        Route route = new Route();
//        route.setId(1);
//        Station station = new Station();
//        station.setId(1);
//
//        when(route.addStation(station)).thenReturn(true);
//        when(station.addRoute(route)).thenReturn(true);
//        when(busService.updateRoute(route, route.getId())).thenReturn(BusServiceResult.SUCCESS);
//        when(busService.updateStation(station, station.getId())).thenReturn(BusServiceResult.SUCCESS);
//
//        assertEquals(BusServiceResult.SUCCESS, busService.addStationToRoute(route, station));
//    }

//    @Test
//    public void testAddStationToRouteFailure() {
//        Route route = new Route();
//        Station station = new Station();
//
//        when(route.addStation(station)).thenReturn(false); // Simulate failure in adding station to route
//
//        assertEquals(BusServiceResult.FAIL, busService.addStationToRoute(route, station));
//        verify(busService, never()).updateRoute(route, route.getId());
//    }
//    @Test
//    public void testAddBusToRouteSuccess() {
//        Route route = new Route();
//        Bus bus = new Bus();
//        when(route.addBus(bus)).thenReturn(true);
//        when(bus.addRoute(route)).thenReturn(true);
//        when(busService.updateRoute(route, route.getId())).thenReturn(BusServiceResult.SUCCESS);
//        when(busService.updateBus(bus, bus.getId())).thenReturn(BusServiceResult.SUCCESS);
//        assertEquals(BusServiceResult.SUCCESS, busService.addBusToRoute(route, bus));
//    }

//    @Test
//    public void testAddBusToRouteFailure() {
//        Route route = new Route();
//        Bus bus = new Bus();
//
//        when(route.addBus(bus)).thenReturn(false); // Simulate failure in adding bus to route
//
//        assertEquals(BusServiceResult.FAIL, busService.addBusToRoute(route, bus));
//        verify(busService, never()).updateRoute(route, route.getId());
//    }
//    @Test
    public void testObtainRoutesByBus() {
        int busId = 1;
        List<Route> expectedRoutes = Arrays.asList(new Route(), new Route());
        when(routeRepository.findByBusesId(busId)).thenReturn(expectedRoutes);

        List<Route> actualRoutes = busService.obtainRoutesByBus(busId);

        assertNotNull(actualRoutes);
        assertEquals(expectedRoutes.size(), actualRoutes.size());
        assertEquals(expectedRoutes, actualRoutes);
    }
}
