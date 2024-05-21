package com.RouteBus.server.controller;

import com.RouteBus.server.model.Bus;
import com.RouteBus.server.service.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * REST controller for managing Bus entities.
 */
@RestController
@RequestMapping("/bus")
public class BusRestController {
    private final BusService busService;

    /**
     * Constructor for BusRestController.
     *
     * @param busService The BusService instance to be used by the controller.
     */
    public BusRestController(BusService busService) {
        this.busService = busService;
    }

    /**
     * Endpoint to get all buses.
     *
     * @return ResponseEntity containing a Set of Bus objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Set<Bus>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    /**
     * Endpoint to get a bus by its license plate.
     *
     * @param licensePlate The license plate of the bus to retrieve.
     * @return ResponseEntity containing a Bus object if found, or ResponseEntity.notFound() if not found.
     */
    @GetMapping("/{licensePlate}")
    public ResponseEntity<Bus> getBusById(@PathVariable String licensePlate) {
        Bus bus = busService.getBusById(licensePlate);
        return bus != null ? ResponseEntity.ok(bus) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to create a new bus.
     *
     * @param bus The Bus object to be created.
     * @return ResponseEntity with a success message if creation is successful, or error message otherwise.
     */
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

    /**
     * Endpoint to update an existing bus.
     *
     * @param bus The Bus object with updated information.
     * @return ResponseEntity with a success message if update is successful, or ResponseEntity.notFound() otherwise.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateBus(@RequestBody Bus bus) {
        BusService.BusServiceResult result = busService.updateBus(bus);
        return result == BusService.BusServiceResult.SUCCESS ?
                ResponseEntity.ok("Bus updated successfully.") :
                ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to delete a bus by its license plate.
     *
     * @param licensePlate The license plate of the bus to be deleted.
     * @return ResponseEntity with a success message if deletion is successful, or ResponseEntity.notFound() otherwise.
     */
    @DeleteMapping("/delete/{licensePlate}")
    public ResponseEntity<String> deleteBus(@PathVariable String licensePlate) {
        BusService.BusServiceResult result = busService.deleteBus(licensePlate);
        return result == BusService.BusServiceResult.SUCCESS ?
                ResponseEntity.ok("Bus deleted successfully.") :
                ResponseEntity.notFound().build();
    }
}
