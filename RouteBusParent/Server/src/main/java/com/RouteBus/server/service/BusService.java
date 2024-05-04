package com.RouteBus.server.service;

import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.model.Bus;
import org.springframework.stereotype.Service;

import java.util.List;

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
			existingBus.setCapacity(bus.getCapacity());
			existingBus.setMake(bus.getMake());
			existingBus.setModel(bus.getModel());
			existingBus.setRoutes(bus.getRoutes());
			busRepository.save(existingBus);
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
