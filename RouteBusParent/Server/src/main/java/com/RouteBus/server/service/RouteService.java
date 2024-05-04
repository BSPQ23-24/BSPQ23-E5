package com.RouteBus.server.service;

import java.util.HashSet;
import java.util.List;


import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.model.Route;

@Service
public class RouteService {
	private final RouteRepository routeRepository;

	public RouteService(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	public enum RouteServiceResult {
		SUCCESS, NOT_FOUND, ERROR
	}

	public Route getRouteById(String id) {
		return routeRepository.findById(id).orElse(null);
	}

	public List<Route> getAllRoutes() {
		return routeRepository.findAll();
	}

	public RouteServiceResult createRoute(Route route) {
		if (!routeRepository.findById(route.getName()).isPresent()) {
			Route savedRoute = routeRepository.save(route);
			return savedRoute != null ? RouteServiceResult.SUCCESS : RouteServiceResult.ERROR;
		}
		return RouteServiceResult.ERROR;
	}

	public RouteServiceResult updateRoute(Route route) {
		return routeRepository.findById(route.getName()).map(existingRoute -> {
			existingRoute.setStartPoint(route.getStartPoint());
			existingRoute.setEndPoint(route.getEndPoint());
			existingRoute.setTotalDistance(route.getTotalDistance());
			existingRoute.setStations(new HashSet<>(route.getStations()));
			existingRoute.setBuses(new HashSet<>(route.getBuses()));

			routeRepository.save(existingRoute);
			return RouteServiceResult.SUCCESS;
		}).orElse(RouteServiceResult.NOT_FOUND);
	}

	public RouteServiceResult deleteRoute(String id) {
		return routeRepository.findById(id).map(route -> {
			routeRepository.delete(route);
			return RouteServiceResult.SUCCESS;
		}).orElse(RouteServiceResult.NOT_FOUND);
	}

	public List<Route> obtainRoutesByBus(String licensePlate) {
		return routeRepository.findByBusesLicensePlate(licensePlate);
	}
}
