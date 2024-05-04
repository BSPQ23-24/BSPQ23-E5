package com.RouteBus.server.service;

import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.model.Route;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RouteServicePerformanceTest {

    @Mock
    private RouteRepository routeRepository;

    private RouteService routeService;

    public RouteServicePerformanceTest() {
        String reportPath = generateReportPath(RouteServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        routeService = new RouteService(routeRepository);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllRoutesPerformance() {
        when(routeRepository.findAll()).thenReturn(Arrays.asList(new Route(), new Route()));
        routeService.getAllRoutes();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 40)
    public void testGetRouteByIdPerformance() {
        when(routeRepository.findById("route1")).thenReturn(Optional.of(new Route()));
        routeService.getRouteById("route1");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 30)
    public void testCreateRoutePerformance() {
        Route route = new Route();
        route.setName("NewRoute");
        when(routeRepository.findById(route.getName())).thenReturn(Optional.empty());
        when(routeRepository.save(route)).thenReturn(route);
        routeService.createRoute(route);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000, warmUpMs = 1000, maxExecutionsPerSecond = 50)
    public void testUpdateRoutePerformance() {
        Route existingRoute = new Route();
        existingRoute.setName("ExistingRoute");
        when(routeRepository.findById(existingRoute.getName())).thenReturn(Optional.of(existingRoute));
        when(routeRepository.save(any(Route.class))).thenReturn(existingRoute);
        routeService.updateRoute(existingRoute);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 1500, warmUpMs = 500, maxExecutionsPerSecond = 25)
    public void testDeleteRoutePerformance() {
        when(routeRepository.findById("route1")).thenReturn(Optional.of(new Route()));
        doNothing().when(routeRepository).delete(any(Route.class));
        routeService.deleteRoute("route1");
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 45)
    public void testObtainRoutesByBusPerformance() {
        when(routeRepository.findByBusesLicensePlate("ABC123")).thenReturn(Arrays.asList(new Route(), new Route()));
        routeService.obtainRoutesByBus("ABC123");
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }
}
