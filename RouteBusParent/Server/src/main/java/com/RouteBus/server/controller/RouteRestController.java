package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Route;
import com.RouteBus.server.service.RouteService;

import java.util.List;
import java.util.Set;

/**
 * REST controller for managing Route entities.
 */
@RestController
@RequestMapping("/Route")
public class RouteRestController {
    private final RouteService routeService;

    /**
     * Constructor for RouteRestController.
     *
     * @param routeService The RouteService instance to be used by the controller.
     */
    public RouteRestController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Endpoint to get all routes.
     *
     * @return ResponseEntity containing a Set of Route objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Set<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    /**
     * Endpoint to get a route by its name.
     *
     * @param name The name of the route to retrieve.
     * @return ResponseEntity containing a Route object if found, or ResponseEntity.notFound() if not found.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Route> getRouteById(@PathVariable String name) {
        Route route = routeService.getRouteById(name);
        return route != null ? ResponseEntity.ok(route) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to create a new route.
     *
     * @param route The Route object to be created.
     * @return ResponseEntity with a success message if creation is successful, or error message otherwise.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createRoute(@RequestBody Route route) {
        RouteService.RouteServiceResult result = routeService.createRoute(route);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Route created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create Route.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    /**
     * Endpoint to update an existing route.
     *
     * @param route The Route object with updated information.
     * @return ResponseEntity with a success message if update is successful, or ResponseEntity.notFound() otherwise.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateRoute(@RequestBody Route route) {
        RouteService.RouteServiceResult result = routeService.updateRoute(route);
        return result == RouteService.RouteServiceResult.SUCCESS ?
            ResponseEntity.ok("Route updated successfully.") :
            ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to delete a route by its name.
     *
     * @param name The name of the route to be deleted.
     * @return ResponseEntity with a success message if deletion is successful, or ResponseEntity.notFound() otherwise.
     */
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteRoute(@PathVariable String name) {
        RouteService.RouteServiceResult result = routeService.deleteRoute(name);
        return result == RouteService.RouteServiceResult.SUCCESS ?
            ResponseEntity.ok("Route deleted successfully.") :
            ResponseEntity.notFound().build();
    }
    
    /**
     * Endpoint to obtain routes associated with a specific bus.
     *
     * @param licensePlate The license plate of the bus.
     * @return List of routes associated with the bus.
     */
    @GetMapping("/bus/{licensePlate}/routes")
    public List<Route> obtainRoutesByBus(@PathVariable String licensePlate) {
        return routeService.obtainRoutesByBus(licensePlate);
    }
    
    /**
     * Endpoint to get routes by origin and destination stations.
     *
     * @param origin      The origin station.
     * @param destination The destination station.
     * @return ResponseEntity containing a list of routes if found, or ResponseEntity.notFound() otherwise.
     */
    @GetMapping("/stations")
    public ResponseEntity<List<Route>> getRoutesByStations(@RequestParam String origin, @RequestParam String destination) {
        List<Route> routes = routeService.getRoutesByStations(origin, destination);
        return routes != null ? ResponseEntity.ok(routes) : ResponseEntity.notFound().build();
    }
}
