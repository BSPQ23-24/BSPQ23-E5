package com.RouteBus.server.controller;

import com.RouteBus.server.model.Bus;
import com.RouteBus.server.service.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/bus")
public class BusRestController {
    private final BusService busService;

    public BusRestController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Bus>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Bus> getBusById(@PathVariable String licensePlate) {
        Bus bus = busService.getBusById(licensePlate);
        return bus != null ? ResponseEntity.ok(bus) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBus(@RequestBody Bus bus) {
        BusService.BusServiceResult result = busService.createBus(bus);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Bus created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create bus.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBus(@RequestBody Bus bus) {
        BusService.BusServiceResult result = busService.updateBus(bus);
        return result == BusService.BusServiceResult.SUCCESS ?
            ResponseEntity.ok("Bus updated successfully.") :
            ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{licensePlate}")
    public ResponseEntity<String> deleteBus(@PathVariable String licensePlate) {
        BusService.BusServiceResult result = busService.deleteBus(licensePlate);
        return result == BusService.BusServiceResult.SUCCESS ?
            ResponseEntity.ok("Bus deleted successfully.") :
            ResponseEntity.notFound().build();
    }
}
