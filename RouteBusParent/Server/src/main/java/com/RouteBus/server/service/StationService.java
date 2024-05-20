package com.RouteBus.server.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.RouteBus.server.dao.StationRepository;
import com.RouteBus.server.model.Station;

@Service
public class StationService {
	private final StationRepository stationRepository;

	public StationService(StationRepository stationRepository) {
		this.stationRepository = stationRepository;
	}

	public enum StationServiceResult {
		SUCCESS, NOT_FOUND, ERROR, OTHER
	}

	public Set<Station> getAllStations() {
		return new HashSet<Station>(stationRepository.findAll());
	}
	
	public Station getStationById(String name) {
		return stationRepository.findById(name).orElse(null);
	}

	public StationServiceResult createStation(Station station) {
		if (!stationRepository.findById(station.getName()).isPresent()) {
			Station savedStation = stationRepository.save(station);
			return savedStation != null ? StationServiceResult.SUCCESS : StationServiceResult.ERROR;
		}
		return StationServiceResult.ERROR;
	}

	public StationServiceResult updateStation(Station station) {
	    return stationRepository.findById(station.getName()).map(existingStation -> {
	        boolean updated = false;
	        if (!Objects.equals(existingStation.getLocation(), station.getLocation())) {
	            existingStation.setLocation(station.getLocation());
	            updated = true;
	        }
	        if (!Objects.equals(existingStation.getRoutes(), station.getRoutes())) {
	            existingStation.setRoutes(new HashSet<>(station.getRoutes()));
	            updated = true;
	        }
	        if (updated) {
	            stationRepository.save(existingStation);
	        }
	        return StationServiceResult.SUCCESS;
	    }).orElse(StationServiceResult.NOT_FOUND);
	}


	public StationServiceResult deleteStation(String name) {
		return stationRepository.findById(name).map(station -> {
			stationRepository.delete(station);
			return StationServiceResult.SUCCESS;
		}).orElse(StationServiceResult.NOT_FOUND);
	}
}
