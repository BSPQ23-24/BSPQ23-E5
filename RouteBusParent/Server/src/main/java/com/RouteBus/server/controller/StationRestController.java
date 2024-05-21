package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.StationService;

import java.util.Set;

/**
 * REST controller for managing Station entities.
 */
@RestController
@RequestMapping("/Station")
public class StationRestController {
    private final StationService stationService;

    /**
     * Constructor for StationRestController.
     *
     * @param stationService The StationService instance to be used by the controller.
     */
    public StationRestController(StationService stationService) {
        this.stationService = stationService;
    }

    /**
     * Endpoint to get all stations.
     *
     * @return ResponseEntity containing a Set of Station objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Set<Station>> getAllStationes() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    /**
     * Endpoint to get a station by its ID.
     *
     * @param name The ID of the station to retrieve.
     * @return ResponseEntity containing a Station object if found, or ResponseEntity.notFound() if not found.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Station> getStationById(@PathVariable String name) {
        Station station = stationService.getStationById(name);
        return station != null ? ResponseEntity.ok(station) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to create a new station.
     *
     * @param station The Station object to be created.
     * @return ResponseEntity with a success message if creation is successful, or error message otherwise.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createStation(@RequestBody Station station) {
        StationService.StationServiceResult result = stationService.createStation(station);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Station created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create Station.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    /**
     * Endpoint to update an existing station.
     *
     * @param station The Station object with updated information.
     * @return ResponseEntity with a success message if update is successful, or ResponseEntity.notFound() otherwise.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateStation(@RequestBody Station station) {
        StationService.StationServiceResult result = stationService.updateStation(station);
        return result == StationService.StationServiceResult.SUCCESS ?
            ResponseEntity.ok("Station updated successfully.") :
            ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to delete a station by its ID.
     *
     * @param name The ID of the station to be deleted.
     * @return ResponseEntity with a success message if deletion is successful, or ResponseEntity.notFound() otherwise.
     */
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteStation(@PathVariable String name) {
        StationService.StationServiceResult result = stationService.deleteStation(name);
        return result == StationService.StationServiceResult.SUCCESS ?
            ResponseEntity.ok("Station deleted successfully.") :
            ResponseEntity.notFound().build();
    }
}
