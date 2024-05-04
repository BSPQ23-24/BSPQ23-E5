package com.RouteBus.server.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.RouteBus.server.dao.ScheduleRepository;
import com.RouteBus.server.model.Schedule;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

public class ScheduleServicePerformanceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    private ScheduleService scheduleService;

    public ScheduleServicePerformanceTest() {
        String reportPath = generateReportPath(ScheduleServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduleService = new ScheduleService(scheduleRepository);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllSchedulesPerformance() {
        when(scheduleRepository.findAll()).thenReturn(mockSchedules());
        scheduleService.getAllSchedules();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 40)
    public void testGetScheduleByIdPerformance() {
        when(scheduleRepository.findById("123")).thenReturn(Optional.of(new Schedule()));
        scheduleService.getScheduleById("123");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 25)
    public void testCreateSchedulePerformance() {
        Schedule newSchedule = new Schedule();
        newSchedule.setId("NEW123");
        when(scheduleRepository.findById(newSchedule.getId())).thenReturn(Optional.empty());
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(newSchedule);
        scheduleService.createSchedule(newSchedule);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 30)
    public void testUpdateSchedulePerformance() {
        Schedule existingSchedule = new Schedule();
        existingSchedule.setId("EXIST123");
        when(scheduleRepository.findById(existingSchedule.getId())).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(existingSchedule);
        scheduleService.updateSchedule(existingSchedule);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 1000, warmUpMs = 500, maxExecutionsPerSecond = 20)
    public void testDeleteSchedulePerformance() {
        when(scheduleRepository.findById("DEL123")).thenReturn(Optional.of(new Schedule()));
        doNothing().when(scheduleRepository).delete(any(Schedule.class));
        scheduleService.deleteSchedule("DEL123");
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }

    private List<Schedule> mockSchedules() {
        Schedule schedule1 = new Schedule();
        schedule1.setId("123");
        Schedule schedule2 = new Schedule();
        schedule2.setId("456");
        return List.of(schedule1, schedule2);
    }
}
