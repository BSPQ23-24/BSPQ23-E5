package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.service.ScheduleService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Schedule")
public class ScheduleRestController {
    private final ScheduleService scheduleService;

    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable String id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

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

    @PutMapping("/update")
    public ResponseEntity<String> updateSchedule(@RequestBody Schedule schedule) {
        ScheduleService.ScheduleServiceResult result = scheduleService.updateSchedule(schedule);
        return result == ScheduleService.ScheduleServiceResult.SUCCESS ?
            ResponseEntity.ok("Schedule updated successfully.") :
            ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable String id) {
        ScheduleService.ScheduleServiceResult result = scheduleService.deleteSchedule(id);
        return result == ScheduleService.ScheduleServiceResult.SUCCESS ?
            ResponseEntity.ok("Schedule deleted successfully.") :
            ResponseEntity.notFound().build();
    }

    @GetMapping("/route/{routeName}")
    public ResponseEntity<List<Schedule>> getSchedulesByRoute(@PathVariable String routeName) {
        return ResponseEntity.ok(scheduleService.getSchedulesByRoute(routeName));
    }

    @GetMapping("/route/{routeName}/departureTime")
    public ResponseEntity<Schedule> getScheduleByRouteAndDepartureTime(@PathVariable String routeName, @RequestParam String departureTime) {
        Schedule schedule = scheduleService.getScheduleByRouteAndDepartureTime(routeName, departureTime);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }
}
