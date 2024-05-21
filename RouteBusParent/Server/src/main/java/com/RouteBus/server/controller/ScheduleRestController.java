package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.service.ScheduleService;

import java.util.List;
import java.util.Set;

/**
 * REST controller for managing Schedule entities.
 */
@RestController
@RequestMapping("/Schedule")
public class ScheduleRestController {
    private final ScheduleService scheduleService;

    /**
     * Constructor for ScheduleRestController.
     *
     * @param scheduleService The ScheduleService instance to be used by the controller.
     */
    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * Endpoint to get all schedules.
     *
     * @return ResponseEntity containing a Set of Schedule objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Set<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    /**
     * Endpoint to get a schedule by its ID.
     *
     * @param id The ID of the schedule to retrieve.
     * @return ResponseEntity containing a Schedule object if found, or ResponseEntity.notFound() if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable String id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to create a new schedule.
     *
     * @param schedule The Schedule object to be created.
     * @return ResponseEntity with a success message if creation is successful, or error message otherwise.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@RequestBody Schedule schedule) {
        ScheduleService.ScheduleServiceResult result = scheduleService.createSchedule(schedule);
        if (result == null) {
            return ResponseEntity.internalServerError().body("Internal server error.");
        }
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Schedule created successfully.");
            default:
                return ResponseEntity.badRequest().body("Failed to create schedule.");
        }
    }

    /**
     * Endpoint to update an existing schedule.
     *
     * @param schedule The Schedule object with updated information.
     * @return ResponseEntity with a success message if update is successful, or ResponseEntity.notFound() otherwise.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateSchedule(@RequestBody Schedule schedule) {
        ScheduleService.ScheduleServiceResult result = scheduleService.updateSchedule(schedule);
        return result == ScheduleService.ScheduleServiceResult.SUCCESS ?
            ResponseEntity.ok("Schedule updated successfully.") :
            ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to delete a schedule by its ID.
     *
     * @param id The ID of the schedule to be deleted.
     * @return ResponseEntity with a success message if deletion is successful, or ResponseEntity.notFound() otherwise.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable String id) {
        ScheduleService.ScheduleServiceResult result = scheduleService.deleteSchedule(id);
        return result == ScheduleService.ScheduleServiceResult.SUCCESS ?
            ResponseEntity.ok("Schedule deleted successfully.") :
            ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to get schedules associated with a specific route.
     *
     * @param routeName The name of the route.
     * @return ResponseEntity containing a list of schedules associated with the route.
     */
    @GetMapping("/route/{routeName}")
    public ResponseEntity<List<Schedule>> getSchedulesByRoute(@PathVariable String routeName) {
        return ResponseEntity.ok(scheduleService.getSchedulesByRoute(routeName));
    }

    /**
     * Endpoint to get a schedule by route name and departure time.
     *
     * @param routeName     The name of the route.
     * @param departureTime The departure time.
     * @return ResponseEntity containing the Schedule object if found, or ResponseEntity.notFound() otherwise.
     */
    @GetMapping("/route/{routeName}/departureTime")
    public ResponseEntity<Schedule> getScheduleByRouteAndDepartureTime(@PathVariable String routeName, @RequestParam String departureTime) {
        Schedule schedule = scheduleService.getScheduleByRouteAndDepartureTime(routeName, departureTime);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }
}
