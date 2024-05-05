package com.RouteBus.server.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.service.ScheduleService;

class ScheduleRestControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleRestController scheduleRestController;

    private static final Logger logger = LogManager.getLogger(ScheduleRestControllerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSchedules() {
        logger.debug("Testing getAllSchedules method...");
        // Arrange
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule(new Route(), new Date(), new Date()));
        when(scheduleService.getAllSchedules()).thenReturn(schedules);

        // Act
        Set<Schedule> result = scheduleRestController.getAllSchedulees();

        // Assert
        assert(result.size() == 1);
        verify(scheduleService, times(1)).getAllSchedules();
        logger.debug("getAllSchedules method test successful.");
    }

    @Test
    void testGetScheduleById() {
        logger.debug("Testing getScheduleById method...");
        // Arrange
        String id = "1";
        Schedule schedule = new Schedule(new Route(), new Date(), new Date());
        when(scheduleService.getScheduleById(id)).thenReturn(schedule);

        // Act
        ResponseEntity<Schedule> result = scheduleRestController.getScheduleById(id);

        // Assert
        assert(result.getStatusCodeValue() == 200);
        verify(scheduleService, times(1)).getScheduleById(id);
        logger.debug("getScheduleById method test successful.");
    }

    @Test
    void testCreateSchedule_Success() {
        logger.debug("Testing createSchedule method - SUCCESS...");
        // Arrange
        Schedule schedule = new Schedule(new Route(), new Date(), new Date());
        when(scheduleService.createSchedule(schedule)).thenReturn(ScheduleService.ScheduleServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> result = scheduleRestController.createSchedule(schedule);

        // Assert
        assert(result.getStatusCodeValue() == 200);
        assert(result.getBody().equals("Schedule created successfully."));
        verify(scheduleService, times(1)).createSchedule(schedule);
        logger.debug("createSchedule method test for SUCCESS case successful.");
    }

    @Test
    void testCreateSchedule_Error() {
        logger.debug("Testing createSchedule method - ERROR...");
        // Arrange
        Schedule schedule = new Schedule(new Route(), new Date(), new Date());
        when(scheduleService.createSchedule(schedule)).thenReturn(ScheduleService.ScheduleServiceResult.ERROR);

        // Act
        ResponseEntity<String> result = scheduleRestController.createSchedule(schedule);

        // Assert
        assert(result.getStatusCodeValue() == 400);
        assert(result.getBody().equals("Failed to create Schedule."));
        verify(scheduleService, times(1)).createSchedule(schedule);
        logger.debug("createSchedule method test for ERROR case successful.");
    }

    @Test
    void testCreateSchedule_Default() {
        logger.debug("Testing createSchedule method - DEFAULT...");
        // Arrange
        Schedule schedule = new Schedule(new Route(), new Date(), new Date());
        when(scheduleService.createSchedule(schedule)).thenReturn(ScheduleService.ScheduleServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> result = scheduleRestController.createSchedule(schedule);

        // Assert
        assert(result.getStatusCodeValue() == 500);
        assert(result.getBody().equals("Internal server error."));
        verify(scheduleService, times(1)).createSchedule(schedule);
        logger.debug("createSchedule method test for DEFAULT case successful.");
    }


    @Test
    void testUpdateSchedule_Success() {
        logger.debug("Testing updateSchedule method - SUCCESS...");
        // Arrange
        Schedule schedule = new Schedule(new Route(), new Date(), new Date());
        when(scheduleService.updateSchedule(schedule)).thenReturn(ScheduleService.ScheduleServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> result = scheduleRestController.updateSchedule(schedule);

        // Assert
        assert(result.getStatusCodeValue() == 200);
        assert(result.getBody().equals("Schedule updated successfully."));
        verify(scheduleService, times(1)).updateSchedule(schedule);
        logger.debug("updateSchedule method test for SUCCESS case successful.");
    }

    @Test
    void testUpdateSchedule_NotFound() {
        logger.debug("Testing updateSchedule method - NOT FOUND...");
        // Arrange
        Schedule schedule = new Schedule(new Route(), new Date(), new Date());
        when(scheduleService.updateSchedule(schedule)).thenReturn(ScheduleService.ScheduleServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> result = scheduleRestController.updateSchedule(schedule);

        // Assert
        assert(result.getStatusCodeValue() == 404);
        verify(scheduleService, times(1)).updateSchedule(schedule);
        logger.debug("updateSchedule method test for NOT FOUND case successful.");
    }

    @Test
    void testDeleteSchedule_Success() {
        logger.debug("Testing deleteSchedule method - SUCCESS...");
        // Arrange
        String id = "1";
        when(scheduleService.deleteSchedule(id)).thenReturn(ScheduleService.ScheduleServiceResult.SUCCESS);

        // Act
        ResponseEntity<String> result = scheduleRestController.deleteSchedule(id);

        // Assert
        assert(result.getStatusCodeValue() == 200);
        assert(result.getBody().equals("Schedule deleted successfully."));
        verify(scheduleService, times(1)).deleteSchedule(id);
        logger.debug("deleteSchedule method test for SUCCESS case successful.");
    }

    @Test
    void testDeleteSchedule_NotFound() {
        logger.debug("Testing deleteSchedule method - NOT FOUND...");
        // Arrange
        String id = "1";
        when(scheduleService.deleteSchedule(id)).thenReturn(ScheduleService.ScheduleServiceResult.NOT_FOUND);

        // Act
        ResponseEntity<String> result = scheduleRestController.deleteSchedule(id);

        // Assert
        assert(result.getStatusCodeValue() == 404);
        verify(scheduleService, times(1)).deleteSchedule(id);
        logger.debug("deleteSchedule method test for NOT FOUND case successful.");
    }
}
