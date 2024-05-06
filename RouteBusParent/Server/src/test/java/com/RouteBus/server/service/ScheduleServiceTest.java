package com.RouteBus.server.service;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.RouteBus.server.dao.ScheduleRepository;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Schedule;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.service.ScheduleService.ScheduleServiceResult;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private final Logger logger = Logger.getLogger(ScheduleServiceTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void testGetScheduleById() {
        String id = "1";
        Schedule schedule = new Schedule();
        schedule.setId(id);
        when(scheduleRepository.findById(id)).thenReturn(Optional.of(schedule));

        Schedule result = scheduleService.getScheduleById(id);
        assertEquals(schedule, result);

        logger.info("getScheduleById test passed.");
    }

    @Test
    public void testGetAllSchedules() {
        List<Schedule> scheduleList = List.of(new Schedule(), new Schedule());
        when(scheduleRepository.findAll()).thenReturn(scheduleList);

        Set<Schedule> result = scheduleService.getAllSchedules();
        assertEquals(new HashSet<>(scheduleList), result);

        logger.info("getAllSchedules test passed.");
    }

    @Test
    public void testCreateSchedule_ScheduleDoesNotExist() {
        Schedule newSchedule = new Schedule(new Route(), new Date(), new Date());

        when(scheduleRepository.findById(newSchedule.getId())).thenReturn(Optional.empty());
        when(scheduleRepository.save(newSchedule)).thenReturn(newSchedule);

        ScheduleServiceResult result = scheduleService.createSchedule(newSchedule);
        assertEquals(ScheduleServiceResult.SUCCESS, result);

        logger.info("createSchedule_ScheduleDoesNotExist test passed.");
    }

    @Test
    public void testCreateSchedule_ScheduleAlreadyExists() {
        Schedule existingSchedule = new Schedule(new Route(), new Date(), new Date());

        when(scheduleRepository.findById(existingSchedule.getId())).thenReturn(Optional.of(existingSchedule));

        ScheduleServiceResult result = scheduleService.createSchedule(existingSchedule);
        assertEquals(ScheduleServiceResult.ERROR, result);

        logger.info("createSchedule_ScheduleAlreadyExists test passed.");
    }

    @Test
    public void testCreateSchedule_SaveError() {
        Schedule newSchedule = new Schedule(new Route(), new Date(), new Date());

        when(scheduleRepository.findById(newSchedule.getId())).thenReturn(Optional.empty());
        when(scheduleRepository.save(newSchedule)).thenReturn(null);

        ScheduleServiceResult result = scheduleService.createSchedule(newSchedule);
        assertEquals(ScheduleServiceResult.ERROR, result);

        logger.info("createSchedule_SaveError test passed.");
    }

    @Test
    public void testUpdateSchedule_ScheduleFound() {
        Schedule existingSchedule = new Schedule("id", null, null, null, null);
        Schedule updatedSchedule = new Schedule("id", new Route("", "", "", 0), new Date(1L), new Date(2L), new HashSet<Ticket>());

        when(scheduleRepository.findById(existingSchedule.getId())).thenReturn(Optional.of(existingSchedule));
        ScheduleServiceResult result = scheduleService.updateSchedule(updatedSchedule);
        assertEquals(ScheduleServiceResult.SUCCESS, result);
        assertEquals(new Date(1L), existingSchedule.getDepartureTime());
        assertEquals(new Date(2L), existingSchedule.getArrivalTime());
        assertEquals(updatedSchedule.getRoute(), existingSchedule.getRoute());
        verify(scheduleRepository).save(existingSchedule);
    }
    
    @Test
    public void testUpdateSchedule_NoChange() {
        Schedule existingSchedule = new Schedule(new Route(), new Date(1L), new Date(2L));
        Schedule updatedSchedule = new Schedule(new Route(), new Date(1L), new Date(2L));

        when(scheduleRepository.findById(existingSchedule.getId())).thenReturn(Optional.of(existingSchedule));
        ScheduleServiceResult result = scheduleService.updateSchedule(updatedSchedule);
        assertEquals(ScheduleServiceResult.SUCCESS, result);

        logger.info("updateSchedule_NoChange test passed.");
    }


    @Test
    public void testUpdateSchedule_ScheduleNotFound() {
        Schedule updatedSchedule = new Schedule(new Route(), new Date(1L), new Date(2L));

        when(scheduleRepository.findById(updatedSchedule.getId())).thenReturn(Optional.empty());
        ScheduleServiceResult result = scheduleService.updateSchedule(updatedSchedule);
        assertEquals(ScheduleServiceResult.NOT_FOUND, result);

        logger.info("updateSchedule_ScheduleNotFound test passed.");
    }

    @Test
    public void testDeleteSchedule_ScheduleFound() {
        String id = "1";
        Schedule scheduleToDelete = new Schedule();
        scheduleToDelete.setId(id);

        when(scheduleRepository.findById(id)).thenReturn(Optional.of(scheduleToDelete));

        ScheduleServiceResult result = scheduleService.deleteSchedule(id);
        assertEquals(ScheduleServiceResult.SUCCESS, result);

        logger.info("deleteSchedule_ScheduleFound test passed.");
    }

    @Test
    public void testDeleteSchedule_ScheduleNotFound() {
        String id = "1";

        when(scheduleRepository.findById(id)).thenReturn(Optional.empty());

        ScheduleServiceResult result = scheduleService.deleteSchedule(id);
        assertEquals(ScheduleServiceResult.NOT_FOUND, result);

        logger.info("deleteSchedule_ScheduleNotFound test passed.");
    }
}
