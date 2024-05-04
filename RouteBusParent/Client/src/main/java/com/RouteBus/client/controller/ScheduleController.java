package com.RouteBus.client.controller;

import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.gateway.ScheduleGateway;
import java.util.List;

public class ScheduleController {
    private static final ScheduleController INSTANCE = new ScheduleController();
    private final ScheduleGateway scheduleGateway;

    private ScheduleController() {
        this.scheduleGateway = ScheduleGateway.getInstance();
    }

    public static ScheduleController getInstance() {
        return INSTANCE;
    }

    public List<ScheduleDTO> getAllSchedules() {
        try {
            return scheduleGateway.getAllSchedules();
        } catch (Exception e) {
            System.err.println("Failed to fetch schedules: " + e.getMessage());
            return null;
        }
    }

    public ScheduleDTO getScheduleById(String id) {
        try {
            return scheduleGateway.getScheduleById(id);
        } catch (Exception e) {
            System.err.println("Failed to fetch schedule: " + e.getMessage());
            return null;
        }
    }

    public boolean createSchedule(ScheduleDTO schedule) {
        try {
            return scheduleGateway.createSchedule(schedule);
        } catch (Exception e) {
            System.err.println("Failed to create schedule: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSchedule(ScheduleDTO schedule) {
        try {
            return scheduleGateway.updateSchedule(schedule);
        } catch (Exception e) {
            System.err.println("Failed to update schedule: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSchedule(String id) {
        try {
            return scheduleGateway.deleteSchedule(id);
        } catch (Exception e) {
            System.err.println("Failed to delete schedule: " + e.getMessage());
            return false;
        }
    }
}
