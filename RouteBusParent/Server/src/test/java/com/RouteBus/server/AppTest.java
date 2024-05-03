package com.RouteBus.server;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.CommandLineRunner;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    private static final Logger logger = Logger.getLogger(AppTest.class);

    @Mock
    private NationalityRepository nationalityRepository;

    @InjectMocks
    private App app;

    @Test
    public void testMain() {
        logger.info("Starting testMain...");
        String[] args = {};
        App.main(args);
        logger.info("testMain completed successfully.");
    }

    @Test
    public void whenNoNationalities_thenAllNationalitiesAreLoaded() throws Exception {
        logger.info("Starting test whenNoNationalities_thenAllNationalitiesAreLoaded...");

        when(nationalityRepository.count()).thenReturn(0L);

        CommandLineRunner runner = app.demo(nationalityRepository);
        runner.run(new String[]{});

        verify(nationalityRepository, times(App.getNumberOfNationalities())).save(any(Nationality.class));

        logger.info("Test whenNoNationalities_thenAllNationalitiesAreLoaded passed successfully.");
    }

    @Test
    public void whenNationalitiesExist_thenNoNationalitiesAreLoaded() throws Exception {
        logger.info("Starting test whenNationalitiesExist_thenNoNationalitiesAreLoaded...");

        when(nationalityRepository.count()).thenReturn(1L);

        CommandLineRunner runner = app.demo(nationalityRepository);
        runner.run(new String[]{});

        verify(nationalityRepository, never()).save(any(Nationality.class));

        logger.info("Test whenNationalitiesExist_thenNoNationalitiesAreLoaded passed successfully.");
    }
}
