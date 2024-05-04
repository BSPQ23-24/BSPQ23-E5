package com.RouteBus.server.service;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;
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

import static org.mockito.Mockito.*;

public class NationalityServicePerformanceTest {

    @Mock
    private NationalityRepository nationalityRepository;

    private NationalityService nationalityService;

    public NationalityServicePerformanceTest() {
        String reportPath = generateReportPath(NationalityServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        nationalityService = new NationalityService(nationalityRepository);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllNationalitiesPerformance() {
        when(nationalityRepository.findAll()).thenReturn(mockNationalities());
        nationalityService.getAllNationalities();
    }

    private List<Nationality> mockNationalities() {
        Nationality n1 = new Nationality("Spain", "es");
        Nationality n2 = new Nationality("France", "fr");
        Nationality n3 = new Nationality("Germany", "de");
        return Arrays.asList(n1, n2, n3);
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }
}
