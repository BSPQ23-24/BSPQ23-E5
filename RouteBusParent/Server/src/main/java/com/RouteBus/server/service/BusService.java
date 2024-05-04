package com.RouteBus.server.service;

import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.model.Bus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BusService {
	private final BusRepository busRepository;

	public BusService(BusRepository busRepository) {
		this.busRepository = busRepository;
	}

	public enum BusServiceResult {
		SUCCESS, NOT_FOUND, ERROR
	}

	public Bus getBusById(String id) {
		return busRepository.findById(id).orElse(null);
	}

	public List<Bus> getAllBuses() {
		return busRepository.findAll();
	}

	public BusServiceResult createBus(Bus bus) {
		if (!busRepository.existsById(bus.getLicensePlate())) {
			busRepository.save(bus);
			return BusServiceResult.SUCCESS;
		}
		return BusServiceResult.ERROR;
	}

	public BusServiceResult updateBus(Bus bus) {
	    return busRepository.findById(bus.getLicensePlate()).map(existingBus -> {
	        boolean updated = false;
	        if (existingBus.getCapacity() != bus.getCapacity()) {
	            existingBus.setCapacity(bus.getCapacity());
	            updated = true;
	        }
	        if (!Objects.equals(existingBus.getMake(), bus.getMake())) {
	            existingBus.setMake(bus.getMake());
	            updated = true;
	        }
	        if (!Objects.equals(existingBus.getModel(), bus.getModel())) {
	            existingBus.setModel(bus.getModel());
	            updated = true;
	        }
	        if (!Objects.equals(existingBus.getRoutes(), bus.getRoutes())) {
	            existingBus.setRoutes(bus.getRoutes());
	            updated = true;
	        }
	        if (updated) {
	            busRepository.save(existingBus);
	        }
	        return BusServiceResult.SUCCESS;
	    }).orElse(BusServiceResult.NOT_FOUND);
	}


	public BusServiceResult deleteBus(String licensePlate) {
		if (busRepository.existsById(licensePlate)) {
			busRepository.deleteById(licensePlate);
			return BusServiceResult.SUCCESS;
		}
		return BusServiceResult.NOT_FOUND;
	}
}
