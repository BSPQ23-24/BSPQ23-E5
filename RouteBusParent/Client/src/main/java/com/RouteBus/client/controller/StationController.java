package com.RouteBus.client.controller;

import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.gateway.StationGateway;
import java.util.List;

/**
 * Controller class for managing station-related operations.
 */
public class StationController {
    private static final StationController INSTANCE = new StationController();
    private final StationGateway stationGateway;

    private StationController() {
        this.stationGateway = StationGateway.getInstance();
    }

    /**
     * Retrieves the singleton instance of StationController.
     * @return The singleton instance of StationController.
     */
    public static StationController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all stations.
     * @return A list of StationDTO objects representing all stations.
     */
    public List<StationDTO> getAllStations() {
        try {
            return stationGateway.getAllStations();
        } catch (Exception e) {
            System.err.println("Failed to fetch stations: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a station by name.
     * @param name The name of the station to retrieve.
     * @return The StationDTO object representing the station with the specified name, or null if not found.
     */
    public StationDTO getStationById(String name) {
        try {
            return stationGateway.getStationById(name);
        } catch (Exception e) {
            System.err.println("Failed to fetch station: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new station.
     * @param station The StationDTO object representing the station to be created.
     * @return true if the station is successfully created, false otherwise.
     */
    public boolean createStation(StationDTO station) {
        try {
            return stationGateway.createStation(station);
        } catch (Exception e) {
            System.err.println("Failed to create station: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing station.
     * @param station The StationDTO object representing the updated station information.
     * @return true if the station is successfully updated, false otherwise.
     */
    public boolean updateStation(StationDTO station) {
        try {
            return stationGateway.updateStation(station);
        } catch (Exception e) {
            System.err.println("Failed to update station: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a station by name.
     * @param name The name of the station to delete.
     * @return true if the station is successfully deleted, false otherwise.
     */
    public boolean deleteStation(String name) {
        try {
            return stationGateway.deleteStation(name);
        } catch (Exception e) {
            System.err.println("Failed to delete station: " + e.getMessage());
            return false;
        }
    }
}
