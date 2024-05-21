package com.RouteBus.client.controller;

import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.gateway.ScheduleGateway;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleController {
    private static final ScheduleController INSTANCE = new ScheduleController();
    private final ScheduleGateway scheduleGateway;

    private ScheduleController() {
        this.scheduleGateway = ScheduleGateway.getInstance();
    }

    public static ScheduleController getInstance() {
        return INSTANCE;
    }

    public Set<ScheduleDTO> getAllSchedules() {
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

    public Map<String, Long> getWeeklyTravellersData() {
        Set<ScheduleDTO> schedules = getAllSchedules();
        if (schedules == null) {
            return Collections.emptyMap();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        return schedules.stream()
                .collect(Collectors.groupingBy(
                        schedule -> {
                            cal.setTime(schedule.getDepartureTime());
                            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                            return sdf.format(cal.getTime());
                        },
                        TreeMap::new,
                        Collectors.summingLong(schedule -> schedule.getTickets().size())
                ));
    }
    
    public List<ScheduleDTO> getSchedulesByRoute(RouteDTO route) {
        try {
            return scheduleGateway.getSchedulesByRoute(route.getName());
        } catch (Exception e) {
            System.err.println("Failed to fetch schedules by route: " + e.getMessage());
            return null;
        }
    }
}
