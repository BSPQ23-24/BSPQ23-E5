/*package com.RouteBus.server;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Mock
    private NationalityRepository nationalityRepository;

    @InjectMocks
    private App app = new App();

    @Before
    public void setUp() {
        when(nationalityRepository.count()).thenReturn(0L);
    }

    @Test
    public void testDemoMethodWhenNoNationalitiesLoaded() throws Exception {
        CommandLineRunner demo = app.demo(nationalityRepository);
        String[] args = {};
        demo.run(args);

        verify(nationalityRepository, times(App.getNumberOfNationalities())).save(any(Nationality.class));
        verify(logger).debug("30 nationalities loaded into the database.");
    }

    @Test
    public void testDemoMethodWhenNationalitiesAlreadyLoaded() throws Exception {
        when(nationalityRepository.count()).thenReturn(30L);

        CommandLineRunner demo = app.demo(nationalityRepository);
        String[] args = {};
        demo.run(args);

        verify(nationalityRepository, never()).save(any(Nationality.class));
        verify(logger).debug("Nationalities are already loaded.");
    }
}
*/