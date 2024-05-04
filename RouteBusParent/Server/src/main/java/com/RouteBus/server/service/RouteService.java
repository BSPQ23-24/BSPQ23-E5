package com.RouteBus.server.service;

import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.BusService.BusServiceResult;
import com.RouteBus.server.service.StationService.StationServiceResult;

@Service
public class RouteService {
	private final RouteRepository routeRepository;
	private final StationService stationService;
	private final BusService busService;

	public RouteService(RouteRepository routeRepository, StationService stationService, BusService busService) {
		this.routeRepository = routeRepository;
		this.stationService = stationService;
		this.busService = busService;
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

	@Transactional
	public RouteServiceResult addStationToRoute(String routeName, String stationName) {
		Route route = getRouteById(routeName);
		Station station = stationService.getStationById(stationName);

		if (route != null && station != null) {
			if (route.addStation(station) && station.addRoute(route)) {
				if (stationService.updateStation(station) == StationServiceResult.SUCCESS
						&& updateRoute(route) == RouteServiceResult.SUCCESS) {
					return RouteServiceResult.SUCCESS;
				}
			}
		}
		return RouteServiceResult.ERROR;
	}

	@Transactional
	public RouteServiceResult addBusToRoute(String routeName, String licensePlate) {
		Route route = getRouteById(routeName);
		Bus bus = busService.getBusById(licensePlate);

		if (route != null && bus != null) {
			if (route.addBus(bus) && bus.addRoute(route)) {
				if (busService.updateBus(bus) == BusServiceResult.SUCCESS
						&& updateRoute(route) == RouteServiceResult.SUCCESS) {
					return RouteServiceResult.SUCCESS;
				}
			}
		}
		return RouteServiceResult.ERROR;
	}
}
