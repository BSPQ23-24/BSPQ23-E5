package com.RouteBus.client.controller;

import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.gateway.StationGateway;
import java.util.List;

public class StationController {
    private static final StationController INSTANCE = new StationController();
    private final StationGateway stationGateway;

    private StationController() {
        this.stationGateway = StationGateway.getInstance();
    }

    public static StationController getInstance() {
        return INSTANCE;
    }

    public List<StationDTO> getAllStations() {
        try {
            return stationGateway.getAllStations();
        } catch (Exception e) {
            System.err.println("Failed to fetch stations: " + e.getMessage());
            return null;
        }
    }

    public StationDTO getStationById(String name) {
        try {
            return stationGateway.getStationById(name);
        } catch (Exception e) {
            System.err.println("Failed to fetch station: " + e.getMessage());
            return null;
        }
    }

    public boolean createStation(StationDTO station) {
        try {
            return stationGateway.createStation(station);
        } catch (Exception e) {
            System.err.println("Failed to create station: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStation(StationDTO station) {
        try {
            return stationGateway.updateStation(station);
        } catch (Exception e) {
            System.err.println("Failed to update station: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStation(String name) {
        try {
            return stationGateway.deleteStation(name);
        } catch (Exception e) {
            System.err.println("Failed to delete station: " + e.getMessage());
            return false;
        }
    }
}
