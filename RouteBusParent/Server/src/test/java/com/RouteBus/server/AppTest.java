package com.RouteBus.server;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.CommandLineRunner;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;

public class AppTest {

    @Mock
    private NationalityRepository nationalityRepository;

    private CommandLineRunner commandLineRunner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        App app = new App();
        commandLineRunner = app.demo(nationalityRepository);
    }

    @Test
    public void testCommandLineRunner_WithEmptyDatabase_ShouldLoadNationalities() throws Exception {
        when(nationalityRepository.count()).thenReturn(0L);
        commandLineRunner.run();
        verify(nationalityRepository, times(1)).save(any(Nationality.class));
        verify(nationalityRepository, times(9)).save(any(Nationality.class)); 
    }

    @Test
    public void testCommandLineRunner_WithExistingData_ShouldNotLoadNationalities() throws Exception {
        when(nationalityRepository.count()).thenReturn(1L);  
        commandLineRunner.run();
        verify(nationalityRepository, never()).save(any(Nationality.class)); // No saves should be triggered
    }
}
