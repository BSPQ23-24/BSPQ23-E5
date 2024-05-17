package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Station;
import com.RouteBus.server.service.StationService;

import java.util.Set;

@RestController
@RequestMapping("/Station")
public class StationRestController {
    private final StationService stationService;

    public StationRestController(StationService StationService) {
        this.stationService = StationService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Station>> getAllStationes() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Station> getStationById(@PathVariable String name) {
        Station Station = stationService.getStationById(name);
        return Station != null ? ResponseEntity.ok(Station) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStation(@RequestBody Station Station) {
        StationService.StationServiceResult result = stationService.createStation(Station);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Station created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create Station.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStation(@RequestBody Station Station) {
        StationService.StationServiceResult result = stationService.updateStation(Station);
        return result == StationService.StationServiceResult.SUCCESS ?
            ResponseEntity.ok("Station updated successfully.") :
            ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteStation(@PathVariable String name) {
        StationService.StationServiceResult result = stationService.deleteStation(name);
        return result == StationService.StationServiceResult.SUCCESS ?
            ResponseEntity.ok("Station deleted successfully.") :
            ResponseEntity.notFound().build();
    }
}
