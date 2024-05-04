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
    public List<Route> getAllRoutes() {
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
    
	@GetMapping("bus/{licensePlate}/routes")
	public List<Route> obtainRoutesByBus(@PathVariable String licensePlate) {
        return routeService.obtainRoutesByBus(licensePlate);
    }
}
