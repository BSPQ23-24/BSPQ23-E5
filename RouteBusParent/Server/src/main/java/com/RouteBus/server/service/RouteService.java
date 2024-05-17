package com.RouteBus.server.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

	public Set<Route> getAllRoutes() {
		return new HashSet<Route>(routeRepository.findAll());
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
			boolean updated = false;
			if (!Objects.equals(existingRoute.getStartPoint(), route.getStartPoint())) {
				existingRoute.setStartPoint(route.getStartPoint());
				updated = true;
			}
			if (!Objects.equals(existingRoute.getEndPoint(), route.getEndPoint())) {
				existingRoute.setEndPoint(route.getEndPoint());
				updated = true;
			}
			if (existingRoute.getTotalDistance() != route.getTotalDistance()) {
				existingRoute.setTotalDistance(route.getTotalDistance());
				updated = true;
			}
			if (!Objects.equals(existingRoute.getStations(), route.getStations())) {
				existingRoute.setStations(new HashSet<>(route.getStations()));
				updated = true;
			}
			if (!Objects.equals(existingRoute.getBuses(), route.getBuses())) {
				existingRoute.setBuses(new HashSet<>(route.getBuses()));
				updated = true;
			}
			if (updated) {
				routeRepository.save(existingRoute);
			}
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
