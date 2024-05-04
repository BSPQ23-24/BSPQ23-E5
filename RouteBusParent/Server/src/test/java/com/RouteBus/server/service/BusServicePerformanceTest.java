package com.RouteBus.server.service;

import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.model.Bus;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BusServicePerformanceTest {

    @Mock
    private BusRepository busRepository;

    private BusService busService;

    public BusServicePerformanceTest() {
        String reportPath = generateReportPath(BusServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        busService = new BusService(busRepository);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllBusesPerformance() {
        when(busRepository.findAll()).thenReturn(mockBuses());
        busService.getAllBuses();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 40)
    public void testGetBusByIdPerformance() {
        when(busRepository.findById("ABC123")).thenReturn(Optional.of(new Bus()));
        busService.getBusById("ABC123");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 25)
    public void testCreateBusPerformance() {
        Bus newBus = new Bus();
        newBus.setLicensePlate("NEW123");
        when(busRepository.existsById(newBus.getLicensePlate())).thenReturn(false);
        when(busRepository.save(any(Bus.class))).thenReturn(newBus);
        busService.createBus(newBus);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 30)
    public void testUpdateBusPerformance() {
        Bus existingBus = new Bus();
        existingBus.setLicensePlate("EXIST123");
        when(busRepository.findById(existingBus.getLicensePlate())).thenReturn(Optional.of(existingBus));
        when(busRepository.save(any(Bus.class))).thenReturn(existingBus);
        busService.updateBus(existingBus);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 1000, warmUpMs = 500, maxExecutionsPerSecond = 20)
    public void testDeleteBusPerformance() {
        when(busRepository.existsById("DEL123")).thenReturn(true);
        doNothing().when(busRepository).deleteById("DEL123");
        busService.deleteBus("DEL123");
    }

    private List<Bus> mockBuses() {
        Bus bus1 = new Bus();
        bus1.setLicensePlate("XYZ123");
        bus1.setCapacity(40);
        Bus bus2 = new Bus();
        bus2.setLicensePlate("XYZ456");
        bus2.setCapacity(30);
        return Arrays.asList(bus1, bus2);
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }
}
