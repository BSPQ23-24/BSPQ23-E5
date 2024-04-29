package com.RouteBus.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Station;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.BusService;

@RestController
public class BusServiceRestController {
	private static final Logger log = LoggerFactory.getLogger(BusServiceRestController.class);
	private BusService busService;
	public BusServiceRestController(BusService busService) {
		this.busService=busService;
	}
	@GetMapping("/bus/all")
	public List<Bus> getBuses() {
		log.warn("Getting all buses ...");
		return busService.getAllBuses();
	}

	@GetMapping("/bus/details/{id}")
	public Bus getBus(@PathVariable int id) {
		log.warn(String.format("Getting bus by id {%d} ...", id));
		return busService.getBusById(id);
	}

	@PostMapping("/bus/create")
	public ResponseEntity<Object> createUser(@RequestBody Bus bus) {
		log.warn("Creating a new Bus ...");

		switch (busService.createBus(bus)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Bus with id {%d} creation fails.", bus.getId()));
		default:
			return ResponseEntity.ok(String.format("A new Bus (%d) has been created.", bus.getId()));
		}
	}

	@PutMapping("/bus/update/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody Bus bus) {
		log.warn(String.format("Updating bus with id {%d} ...", id));

		switch (busService.updateBus(bus, id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Bus with id {%i} cannot be updated", bus.getId()));
		default:
			return ResponseEntity.ok(String.format("User with email {%s} has been updated.", bus.getId()));
		}
	}

	@DeleteMapping("/bus/delete/{id}")
	public ResponseEntity<Object> deleteBus(@PathVariable int id) {
		log.warn(String.format("Deleting user with id {%d} ...", id));

		switch (busService.deleteBus(id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body(String.format("Bus with id {%i} cannot be deleted", id));
		default:
			return ResponseEntity.ok(String.format("Bus with id {%i} has been deleted.", id));
		}
	}

	@DeleteMapping("/bus/delete/all")
	public ResponseEntity<Object> deleteBuses() {
		log.warn("Deleting ALL buses ...");

		switch (busService.deleteAllBuses()) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body("Deletion of all buses fails");
		default:
			return ResponseEntity.ok("All buses have been deleted.");
		}
	}

	@GetMapping("/ticket/all")
	public List<Ticket> getTicket() {
		log.warn("Getting all buses ...");
		return busService.getAllTickets();
	}

	@GetMapping("/ticket/details/{id}")
	public Ticket getTicket(@PathVariable Long id) {
		log.warn(String.format("Getting ticket by id {%d} ...", id));
		return busService.getTicketById(id);
	}

	@PostMapping("/ticket/create")
	public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket) {
		log.warn("Creating a new Ticket ...");

		switch (busService.createTicket(ticket)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Ticket with id {%i} creation fails.", ticket.getId()));
		default:
			return ResponseEntity.ok(String.format("A new ticket (%i) has been created.", ticket.getId()));
		}
	}

	@PutMapping("/ticket/update/{id}")
	public ResponseEntity<Object> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
		log.warn(String.format("Updating ticket with id {%d} ...", id));

		switch (busService.updateTicket(ticket, id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Ticket with id {%i} cannot be updated", ticket.getId()));
		default:
			return ResponseEntity.ok(String.format("Ticket with id {%s} has been updated.", ticket.getId().toString()));
		}
	}

	@DeleteMapping("/ticket/delete/{id}")
	public ResponseEntity<Object> deleteTicket(@PathVariable Long id) {
		log.warn(String.format("Deleting user with id {%d} ...", id));

		switch (busService.deleteTicket(id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Ticket with id {%i} cannot be deleted", id));
		default:
			return ResponseEntity.ok(String.format("Ticket with id {%i} has been deleted.", id));
		}
	}

	@DeleteMapping("/ticket/delete/all")
	public ResponseEntity<Object> deleteTickets() {
		log.warn("Deleting ALL tickets ...");

		switch (busService.deleteAllTickets()) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body("Deletion of all tickets fails");
		default:
			return ResponseEntity.ok("All tickets have been deleted.");
		}
	}
	
	@GetMapping("/station/all")
	public List<Station> getStation() {
		log.warn("Getting all buses ...");
		return busService.getAllStations();
	}


	@GetMapping("/station/details/{id}")
	public Station getStation(@PathVariable int id) {
		log.warn(String.format("Getting station by id {%d} ...", id));
		return busService.getStationById(id);
	}

	@PostMapping("/station/create")
	public ResponseEntity<Object> createStation(@RequestBody Station station) {
		log.warn("Creating a new Station ...");

		switch (busService.createStation(station)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Station with id {%i} creation fails.", station.getId()));
		default:
			return ResponseEntity.ok(String.format("A new station (%i) has been created.", station.getId()));
		}
	}

	@PutMapping("/station/update/{id}")
	public ResponseEntity<Object> updateStation(@PathVariable int id, @RequestBody Station station) {
		log.warn(String.format("Updating station with id {%d} ...", id));

		switch (busService.updateStation(station, id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Bus with id {%i} cannot be updated", station.getId()));
		default:
			return ResponseEntity.ok(String.format("Station with id {%s} has been updated.", station.getId()));
		}
	}

	@DeleteMapping("station/delete/{id}")
	public ResponseEntity<Object> deleteStation(@PathVariable int id) {
		log.warn(String.format("Deleting station with id {%d} ...", id));

		switch (busService.deleteStation(id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Station with id {%i} cannot be deleted", id));
		default:
			return ResponseEntity.ok(String.format("Station with id {%i} has been deleted.", id));
		}
	}

	@DeleteMapping("/station/delete/all")
	public ResponseEntity<Object> deleteStations() {
		log.warn("Deleting ALL users ...");

		switch (busService.deleteAllBuses()) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body("Deletion of all stations fails");
		default:
			return ResponseEntity.ok("All stations have been deleted.");
		}
	}

	@GetMapping("/route/all")
	public List<Route> getRoutes() {
		log.warn("Getting all buses ...");
		return busService.getAllRoutes();
	}

	@GetMapping("/route/details/{id}")
	public Route getRoute(@PathVariable int id) {
		log.warn(String.format("Getting route by id {%d} ...", id));
		return busService.getRouteById(id);
	}

	@PostMapping("/route/create")
	public ResponseEntity<Object> createRoute(@RequestBody Route route) {
		log.warn("Creating a new User ...");

		switch (busService.createRoute(route)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Route with id {%i} creation fails.", route.getId()));
		default:
			return ResponseEntity.ok(String.format("A new route (%i) has been created.", route.getId()));
		}
	}

	@PutMapping("/route/update/{id}")
	public ResponseEntity<Object> updateRoute(@PathVariable int id, @RequestBody Route route) {
		log.warn(String.format("Updating user with id {%d} ...", id));

		switch (busService.updateRoute(route, id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity()
					.body(String.format("Bus with id {%i} cannot be updated", route.getId()));
		default:
			return ResponseEntity.ok(String.format("User with email {%s} has been updated.", route.getId()));
		}
	}

	@DeleteMapping("/route/delete/{id}")
	public ResponseEntity<Object> deleteRoute(@PathVariable int id) {
		log.warn(String.format("Deleting route with id {%d} ...", id));

		switch (busService.deleteRoute(id)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body(String.format("Route with id {%i} cannot be deleted", id));
		default:
			return ResponseEntity.ok(String.format("Route with id {%i} has been deleted.", id));
		}
	}

	@DeleteMapping("/route/delete/all")
	public ResponseEntity<Object> deleteRoutes() {
		log.warn("Deleting ALL users ...");

		switch (busService.deleteAllRoutes()) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body("Deletion of all routes fails");
		default:
			return ResponseEntity.ok("All routes have been deleted.");
		}
	}

	@GetMapping("/route/{routeId}/buses/{busId}")
	public ResponseEntity<Object> addBusToRoute(@PathVariable int routeId, @PathVariable int busId) {
		log.warn("Adding Bus to Route ...");
		Route route = this.getRoute(routeId);
		Bus bus = this.getBus(busId);
		switch (busService.addBusToRoute(route, bus)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body("Addition of bus to route failed");
		default:
			return ResponseEntity.ok("Bus added correctly to the route.");
		}
	}

	@GetMapping("/route/{routeId}/stations/{stationId}")
	public ResponseEntity<Object> addStationToRoute(@PathVariable int routeId, @PathVariable int stationId) {
		log.warn("Adding Station to Route ...");
		Route route = this.getRoute(routeId);
		Station station = this.getStation(stationId);
		switch (busService.addStationToRoute(route, station)) {
		case FAIL:
			return ResponseEntity.unprocessableEntity().body("Addition of station to route failed");
		default:
			return ResponseEntity.ok("Station added correctly to the route.");
		}
	}
	@GetMapping("bus/{busId}/routes")
	public List<Route> obtainRoutesByBus(@PathVariable int busId) {
        return busService.obtainRoutesByBus(busId);
    }
}
