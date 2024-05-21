package com.RouteBus.client.controller;

import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.gateway.RouteGateway;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class responsible for handling route-related operations.
 */
public class RouteController {
    private static final RouteController INSTANCE = new RouteController();
    private final RouteGateway routeGateway;

    private RouteController() {
        this.routeGateway = RouteGateway.getInstance();
    }

    /**
     * Retrieves the singleton instance of RouteController.
     *
     * @return The singleton instance of RouteController.
     */
    public static RouteController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all routes.
     *
     * @return A list of RouteDTO representing all routes, or null if an error occurs.
     */
    public List<RouteDTO> getAllRoutes() {
        try {
            return routeGateway.getAllRoutes();
        } catch (Exception e) {
            System.err.println("Failed to fetch routes: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a route by its ID.
     *
     * @param name The ID of the route to retrieve.
     * @return The RouteDTO representing the route, or null if an error occurs.
     */
    public RouteDTO getRouteById(String name) {
        try {
            return routeGateway.getRouteById(name);
        } catch (Exception e) {
            System.err.println("Failed to fetch route: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new route.
     *
     * @param route The RouteDTO representing the route to create.
     * @return true if the route is successfully created, false otherwise.
     */
    public boolean createRoute(RouteDTO route) {
        try {
            return routeGateway.createRoute(route);
        } catch (Exception e) {
            System.err.println("Failed to create route: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing route.
     *
     * @param route The RouteDTO representing the route to update.
     * @return true if the route is successfully updated, false otherwise.
     */
    public boolean updateRoute(RouteDTO route) {
        try {
            return routeGateway.updateRoute(route);
        } catch (Exception e) {
            System.err.println("Failed to update route: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a route by its ID.
     *
     * @param name The ID of the route to delete.
     * @return true if the route is successfully deleted, false otherwise.
     */
    public boolean deleteRoute(String name) {
        try {
            return routeGateway.deleteRoute(name);
        } catch (Exception e) {
            System.err.println("Failed to delete route: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves routes associated with a specific bus.
     *
     * @param licensePlate The license plate of the bus.
     * @return A list of RouteDTO representing the routes associated with the bus, or null if an error occurs.
     */
    public List<RouteDTO> obtainRoutesByBus(String licensePlate) {
        try {
            return routeGateway.obtainRoutesByBus(licensePlate);
        } catch (Exception e) {
            System.err.println("Failed to obtain routes by bus: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves routes ordered by popularity.
     *
     * @return A list of RouteDTO representing the routes ordered by popularity, or null if an error occurs.
     */
    public List<RouteDTO> getRoutesOrderedByPopularity() {
        List<RouteDTO> routes = getAllRoutes();
        if (routes != null) {
            routes.sort((r1, r2) -> Integer.compare(r2.getTotalPassengers(), r1.getTotalPassengers()));
        }
        return routes;
    }

    /**
     * Filters routes based on a query string.
     *
     * @param query The query string to filter routes by.
     * @return A list of RouteDTO representing the filtered routes, or null if an error occurs.
     */
    public List<RouteDTO> filterRoutes(String query) {
        List<RouteDTO> routes = getAllRoutes();
        if (routes != null) {
            return routes.stream()
                    .filter(route -> route.getName().toLowerCase().contains(query.toLowerCase()) ||
                            route.getStartPoint().toLowerCase().contains(query.toLowerCase()) ||
                            route.getEndPoint().toLowerCase().contains(query.toLowerCase()) ||
                            route.getStations().stream().anyMatch(station -> station.getLocation().toLowerCase().contains(query.toLowerCase())) ||
                            route.getBuses().stream().anyMatch(bus -> bus.getLicensePlate().toLowerCase().contains(query.toLowerCase())) ||
                            String.valueOf(route.getTotalDistance()).contains(query))
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Retrieves routes by stations.
     *
     * @param origin      The origin station.
     * @param destination The destination station.
     * @return A list of RouteDTO representing the routes between the given stations, or an empty list if an error occurs.
     */
    public List<RouteDTO> getRoutesByStations(String origin, String destination) {
        try {
            return routeGateway.getRoutesByStations(origin, destination);
        } catch (Exception e) {
            System.err.println("Failed to fetch routes by stations: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
