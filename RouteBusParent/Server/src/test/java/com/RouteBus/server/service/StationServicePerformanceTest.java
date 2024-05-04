package com.RouteBus.server.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.RouteBus.server.dao.StationRepository;
import com.RouteBus.server.model.Station;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

public class StationServicePerformanceTest {

    @Mock
    private StationRepository stationRepository;

    private StationService stationService;

    public StationServicePerformanceTest() {
        String reportPath = generateReportPath(StationServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stationService = new StationService(stationRepository);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllStationsPerformance() {
        when(stationRepository.findAll()).thenReturn(mockStations());
        stationService.getAllStations();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 40)
    public void testGetStationByIdPerformance() {
        when(stationRepository.findById("Station1")).thenReturn(Optional.of(new Station()));
        stationService.getStationById("Station1");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 25)
    public void testCreateStationPerformance() {
        Station newStation = new Station();
        newStation.setName("NewStation");
        when(stationRepository.findById(newStation.getName())).thenReturn(Optional.empty());
        when(stationRepository.save(any(Station.class))).thenReturn(newStation);
        stationService.createStation(newStation);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 30)
    public void testUpdateStationPerformance() {
        Station existingStation = new Station();
        existingStation.setName("ExistingStation");
        when(stationRepository.findById(existingStation.getName())).thenReturn(Optional.of(existingStation));
        when(stationRepository.save(any(Station.class))).thenReturn(existingStation);
        stationService.updateStation(existingStation);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 1000, warmUpMs = 500, maxExecutionsPerSecond = 20)
    public void testDeleteStationPerformance() {
        when(stationRepository.findById("DeleteStation")).thenReturn(Optional.of(new Station()));
        doNothing().when(stationRepository).delete(any(Station.class));
        stationService.deleteStation("DeleteStation");
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }

    private List<Station> mockStations() {
        Station station1 = new Station();
        station1.setName("Station1");
        Station station2 = new Station();
        station2.setName("Station2");
        return Arrays.asList(station1, station2);
    }
}
