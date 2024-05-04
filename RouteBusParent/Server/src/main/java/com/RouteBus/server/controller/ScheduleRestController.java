package com.RouteBus.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/Schedule")
public class ScheduleRestController {
    private final ScheduleService scheduleService;

    public ScheduleRestController(ScheduleService ScheduleService) {
        this.scheduleService = ScheduleService;
    }

    @GetMapping("/all")
    public List<Schedule> getAllSchedulees() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable String id) {
        Schedule Schedule = scheduleService.getScheduleById(id);
        return Schedule != null ? ResponseEntity.ok(Schedule) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@RequestBody Schedule Schedule) {
        ScheduleService.ScheduleServiceResult result = scheduleService.createSchedule(Schedule);
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Schedule created successfully.");
            case ERROR:
                return ResponseEntity.badRequest().body("Failed to create Schedule.");
            default:
                return ResponseEntity.internalServerError().body("Internal server error.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSchedule(@RequestBody Schedule Schedule) {
        ScheduleService.ScheduleServiceResult result = scheduleService.updateSchedule(Schedule);
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
}
