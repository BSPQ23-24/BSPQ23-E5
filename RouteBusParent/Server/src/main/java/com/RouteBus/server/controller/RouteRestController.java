package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Route;
import com.RouteBus.server.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/Route")
public class RouteRestController {
    private final RouteService routeService;

    public RouteRestController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/all")
    public List<Route> getAllRoutees() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Route> getRouteById(@PathVariable String name) {
        Route Route = routeService.getRouteById(name);
        return Route != null ? ResponseEntity.ok(Route) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoute(@RequestBody Route Route) {
        RouteService.RouteServiceResult result = routeService.createRoute(Route);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Route created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create Route.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateRoute(@RequestBody Route Route) {
        RouteService.RouteServiceResult result = routeService.updateRoute(Route);
        return result == RouteService.RouteServiceResult.SUCCESS ?
            ResponseEntity.ok("Route updated successfully.") :
            ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteRoute(@PathVariable String name) {
        RouteService.RouteServiceResult result = routeService.deleteRoute(name);
        return result == RouteService.RouteServiceResult.SUCCESS ?
            ResponseEntity.ok("Route deleted successfully.") :
            ResponseEntity.notFound().build();
    }
    
	@GetMapping("/route/{routeName}/buses/{licensePlate}")
	public ResponseEntity<Object> addBusToRoute(@PathVariable String routeName, @PathVariable String licensePlate) {
		switch (routeService.addBusToRoute(routeName, licensePlate)) {
		case ERROR:
			return ResponseEntity.unprocessableEntity().body("Addition of bus to route failed");
		default:
			return ResponseEntity.ok("Bus added correctly to the route.");
		}
	}

	@GetMapping("/route/{routeName}/stations/{stationName}")
	public ResponseEntity<Object> addStationToRoute(@PathVariable String routeName, @PathVariable String stationName) {
		switch (routeService.addStationToRoute(routeName, stationName)) {
		case ERROR:
			return ResponseEntity.unprocessableEntity().body("Addition of station to route failed");
		default:
			return ResponseEntity.ok("Station added correctly to the route.");
		}
	}
	@GetMapping("bus/{licensePlate}/routes")
	public List<Route> obtainRoutesByBus(@PathVariable String licensePlate) {
        return routeService.obtainRoutesByBus(licensePlate);
    }
}
