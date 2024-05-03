package com.RouteBus.server;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.CommandLineRunner;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    @Mock
    private NationalityRepository nationalityRepository;

    @InjectMocks
    private App app;

    @Test
    public void testMain() {
        String[] args = {};
        App.main(args);  
    }

    @Test
    public void whenNoNationalities_thenAllNationalitiesAreLoaded() throws Exception {
        when(nationalityRepository.count()).thenReturn(0L);

        CommandLineRunner runner = app.demo(nationalityRepository);
        runner.run(new String[]{});

        verify(nationalityRepository, times(9)).save(any(Nationality.class));
    }

    @Test
    public void whenNationalitiesExist_thenNoNationalitiesAreLoaded() throws Exception {
        when(nationalityRepository.count()).thenReturn(1L);

        CommandLineRunner runner = app.demo(nationalityRepository);
        runner.run(new String[]{});

        verify(nationalityRepository, never()).save(any(Nationality.class));
    }
}
