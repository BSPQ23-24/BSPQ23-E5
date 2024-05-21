package com.RouteBus.client.controller;

import com.RouteBus.client.dto.BusDTO;
import com.RouteBus.client.gateway.BusGateway;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing bus-related operations.
 */
public class BusController {
    private static final BusController INSTANCE = new BusController();
    private final BusGateway busGateway;

    private BusController() {
        this.busGateway = BusGateway.getInstance();
    }

    /**
     * Retrieves the singleton instance of BusController.
     *
     * @return the singleton instance of BusController
     */
    public static BusController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all buses.
     *
     * @return a list of BusDTO representing all buses, or null if an error occurs
     */
    public List<BusDTO> getAllBuses() {
        try {
            return busGateway.getAllBuses();
        } catch (Exception e) {
            System.err.println("Failed to fetch buses: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a bus by its license plate.
     *
     * @param licensePlate the license plate of the bus to retrieve
     * @return the BusDTO representing the bus, or null if an error occurs
     */
    public BusDTO getBusById(String licensePlate) {
        try {
            return busGateway.getBusById(licensePlate);
        } catch (Exception e) {
            System.err.println("Failed to fetch bus: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new bus.
     *
     * @param bus the BusDTO representing the bus to create
     * @return true if the bus is successfully created, false otherwise
     */
    public boolean createBus(BusDTO bus) {
        try {
            return busGateway.createBus(bus);
        } catch (Exception e) {
            System.err.println("Failed to create bus: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing bus.
     *
     * @param bus the BusDTO representing the bus to update
     * @return true if the bus is successfully updated, false otherwise
     */
    public boolean updateBus(BusDTO bus) {
        try {
            return busGateway.updateBus(bus);
        } catch (Exception e) {
            System.err.println("Failed to update bus: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a bus by its license plate.
     *
     * @param licensePlate the license plate of the bus to delete
     * @return true if the bus is successfully deleted, false otherwise
     */
    public boolean deleteBus(String licensePlate) {
        try {
            return busGateway.deleteBus(licensePlate);
        } catch (Exception e) {
            System.err.println("Failed to delete bus: " + e.getMessage());
            return false;
        }
    }

    /**
     * Filters buses based on a query string.
     *
     * @param query the query string to filter buses by
     * @return a list of BusDTO representing the filtered buses, or null if an error occurs
     */
    public List<BusDTO> filterBuses(String query) {
        List<BusDTO> buses = getAllBuses();
        if (buses != null) {
            return buses.stream()
                    .filter(bus -> bus.getLicensePlate().toLowerCase().contains(query.toLowerCase()) ||
                            bus.getModel().toLowerCase().contains(query.toLowerCase()) ||
                            String.valueOf(bus.getCapacity()).contains(query))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
