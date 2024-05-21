package com.RouteBus.client.controller;

import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.gateway.ScheduleGateway;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class for managing schedule-related operations.
 */
public class ScheduleController {
    private static final ScheduleController INSTANCE = new ScheduleController();
    private final ScheduleGateway scheduleGateway;

    private ScheduleController() {
        this.scheduleGateway = ScheduleGateway.getInstance();
    }

    /**
     * Retrieves the singleton instance of ScheduleController.
     * @return The singleton instance of ScheduleController.
     */
    public static ScheduleController getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves all schedules.
     * @return A set of ScheduleDTO objects representing all schedules.
     */
    public Set<ScheduleDTO> getAllSchedules() {
        try {
            return scheduleGateway.getAllSchedules();
        } catch (Exception e) {
            System.err.println("Failed to fetch schedules: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a schedule by ID.
     * @param id The ID of the schedule to retrieve.
     * @return The ScheduleDTO object representing the schedule with the specified ID, or null if not found.
     */
    public ScheduleDTO getScheduleById(String id) {
        try {
            return scheduleGateway.getScheduleById(id);
        } catch (Exception e) {
            System.err.println("Failed to fetch schedule: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new schedule.
     * @param schedule The ScheduleDTO object representing the schedule to be created.
     * @return true if the schedule is successfully created, false otherwise.
     */
    public boolean createSchedule(ScheduleDTO schedule) {
        try {
            return scheduleGateway.createSchedule(schedule);
        } catch (Exception e) {
            System.err.println("Failed to create schedule: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing schedule.
     * @param schedule The ScheduleDTO object representing the updated schedule information.
     * @return true if the schedule is successfully updated, false otherwise.
     */
    public boolean updateSchedule(ScheduleDTO schedule) {
        try {
            return scheduleGateway.updateSchedule(schedule);
        } catch (Exception e) {
            System.err.println("Failed to update schedule: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a schedule by ID.
     * @param id The ID of the schedule to delete.
     * @return true if the schedule is successfully deleted, false otherwise.
     */
    public boolean deleteSchedule(String id) {
        try {
            return scheduleGateway.deleteSchedule(id);
        } catch (Exception e) {
            System.err.println("Failed to delete schedule: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves weekly travelers' data.
     * @return A map where keys are dates (Mondays) and values are the total number of travelers for each week.
     */
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
    
    /**
     * Retrieves schedules by route.
     * @param route The RouteDTO object representing the route.
     * @return A list of ScheduleDTO objects representing schedules for the specified route.
     */
    public List<ScheduleDTO> getSchedulesByRoute(RouteDTO route) {
        try {
            return scheduleGateway.getSchedulesByRoute(route.getName());
        } catch (Exception e) {
            System.err.println("Failed to fetch schedules by route: " + e.getMessage());
            return null;
        }
    }
}
