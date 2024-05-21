package com.RouteBus.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.service.ScheduleService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleRestControllerTest {

    private static final Logger logger = LogManager.getLogger(ScheduleRestControllerTest.class);

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleRestController scheduleRestController;

    private Schedule mockSchedule;

    @Before
    public void setUp() {
        mockSchedule = new Schedule();
        mockSchedule.setId("1");
    }

    @Test
    public void testGetAllSchedulees() {
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(mockSchedule);
        when(scheduleService.getAllSchedules()).thenReturn(schedules);

        ResponseEntity<Set<Schedule>> response = scheduleRestController.getAllSchedules();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(schedules, response.getBody());
        verify(scheduleService).getAllSchedules();
        logger.debug("getAllSchedulees method test completed successfully.");
    }

    @Test
    public void testGetScheduleByIdFound() {
        when(scheduleService.getScheduleById("1")).thenReturn(mockSchedule);
        ResponseEntity<Schedule> response = scheduleRestController.getScheduleById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockSchedule, response.getBody());
        verify(scheduleService).getScheduleById("1");
        logger.debug("getScheduleById method test completed successfully for found case.");
    }

    @Test
    public void testGetScheduleByIdNotFound() {
        when(scheduleService.getScheduleById("2")).thenReturn(null);
        ResponseEntity<Schedule> response = scheduleRestController.getScheduleById("2");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(scheduleService).getScheduleById("2");
        logger.debug("getScheduleById method test completed successfully for not found case.");
    }

    @Test
    public void testCreateScheduleSuccess() {
        when(scheduleService.createSchedule(mockSchedule)).thenReturn(ScheduleService.ScheduleServiceResult.SUCCESS);
        ResponseEntity<String> response = scheduleRestController.createSchedule(mockSchedule);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Schedule created successfully.", response.getBody());
        verify(scheduleService).createSchedule(mockSchedule);
        logger.debug("createSchedule method test completed successfully for SUCCESS case.");
    }

    @Test
    public void testCreateScheduleError() {
        when(scheduleService.createSchedule(mockSchedule)).thenReturn(ScheduleService.ScheduleServiceResult.ERROR);
        ResponseEntity<String> response = scheduleRestController.createSchedule(mockSchedule);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create schedule.", response.getBody());
        verify(scheduleService).createSchedule(mockSchedule);
        logger.debug("createSchedule method test for ERROR case successful.");
    }

    @Test
    public void testCreateScheduleNullResult() {
        when(scheduleService.createSchedule(mockSchedule)).thenReturn(null);
        ResponseEntity<String> response = scheduleRestController.createSchedule(mockSchedule);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());
        verify(scheduleService).createSchedule(mockSchedule);
        logger.debug("Test createScheduleNullResult completed successfully.");
    }

    @Test
    public void testUpdateScheduleSuccess() {
        when(scheduleService.updateSchedule(mockSchedule)).thenReturn(ScheduleService.ScheduleServiceResult.SUCCESS);
        ResponseEntity<String> response = scheduleRestController.updateSchedule(mockSchedule);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Schedule updated successfully.", response.getBody());
        verify(scheduleService).updateSchedule(mockSchedule);
        logger.debug("updateSchedule method test completed successfully for SUCCESS case.");
    }

    @Test
    public void testUpdateScheduleNotFound() {
        when(scheduleService.updateSchedule(mockSchedule)).thenReturn(ScheduleService.ScheduleServiceResult.NOT_FOUND);
        ResponseEntity<String> response = scheduleRestController.updateSchedule(mockSchedule);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(scheduleService).updateSchedule(mockSchedule);
        logger.debug("updateSchedule method test completed successfully for NOT FOUND case.");
    }

    @Test
    public void testDeleteScheduleSuccess() {
        when(scheduleService.deleteSchedule("1")).thenReturn(ScheduleService.ScheduleServiceResult.SUCCESS);
        ResponseEntity<String> response = scheduleRestController.deleteSchedule("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Schedule deleted successfully.", response.getBody());
        verify(scheduleService).deleteSchedule("1");
        logger.debug("deleteSchedule method test completed successfully for SUCCESS case.");
    }

    @Test
    public void testDeleteScheduleNotFound() {
        when(scheduleService.deleteSchedule("2")).thenReturn(ScheduleService.ScheduleServiceResult.NOT_FOUND);
        ResponseEntity<String> response = scheduleRestController.deleteSchedule("2");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(scheduleService).deleteSchedule("2");
        logger.debug("deleteSchedule method test completed successfully for NOT FOUND case.");
    }
}
