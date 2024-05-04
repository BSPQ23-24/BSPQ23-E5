package com.RouteBus.client.controller;

import com.RouteBus.client.dto.BusDTO;
import com.RouteBus.client.gateway.BusGateway;
import java.util.List;

public class BusController {
    private static final BusController INSTANCE = new BusController();
    private final BusGateway busGateway;

    private BusController() {
        this.busGateway = BusGateway.getInstance();
    }

    public static BusController getInstance() {
        return INSTANCE;
    }

    public List<BusDTO> getAllBuses() {
        try {
            return busGateway.getAllBuses();
        } catch (Exception e) {
            System.err.println("Failed to fetch buses: " + e.getMessage());
            return null;
        }
    }

    public BusDTO getBusById(String licensePlate) {
        try {
            return busGateway.getBusById(licensePlate);
        } catch (Exception e) {
            System.err.println("Failed to fetch bus: " + e.getMessage());
            return null;
        }
    }

    public boolean createBus(BusDTO bus) {
        try {
            return busGateway.createBus(bus);
        } catch (Exception e) {
            System.err.println("Failed to create bus: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBus(BusDTO bus) {
        try {
            return busGateway.updateBus(bus);
        } catch (Exception e) {
            System.err.println("Failed to update bus: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBus(String licensePlate) {
        try {
            return busGateway.deleteBus(licensePlate);
        } catch (Exception e) {
            System.err.println("Failed to delete bus: " + e.getMessage());
            return false;
        }
    }
}
