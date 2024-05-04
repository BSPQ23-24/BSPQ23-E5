package com.RouteBus.client.controller;

import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.gateway.RouteGateway;
import java.util.List;

public class RouteController {
    private static final RouteController INSTANCE = new RouteController();
    private final RouteGateway routeGateway;

    private RouteController() {
        this.routeGateway = RouteGateway.getInstance();
    }

    public static RouteController getInstance() {
        return INSTANCE;
    }

    public List<RouteDTO> getAllRoutes() {
        try {
            return routeGateway.getAllRoutes();
        } catch (Exception e) {
            System.err.println("Failed to fetch routes: " + e.getMessage());
            return null;
        }
    }

    public RouteDTO getRouteById(String name) {
        try {
            return routeGateway.getRouteById(name);
        } catch (Exception e) {
            System.err.println("Failed to fetch route: " + e.getMessage());
            return null;
        }
    }

    public boolean createRoute(RouteDTO route) {
        try {
            return routeGateway.createRoute(route);
        } catch (Exception e) {
            System.err.println("Failed to create route: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRoute(RouteDTO route) {
        try {
            return routeGateway.updateRoute(route);
        } catch (Exception e) {
            System.err.println("Failed to update route: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRoute(String name) {
        try {
            return routeGateway.deleteRoute(name);
        } catch (Exception e) {
            System.err.println("Failed to delete route: " + e.getMessage());
            return false;
        }
    }
    
    public List<RouteDTO> obtainRoutesByBus(String licensePlate) {
        try {
            return routeGateway.obtainRoutesByBus(licensePlate);
        } catch (Exception e) {
            System.err.println("Failed to obtain routes by bus: " + e.getMessage());
            return null;
        }
    }
}