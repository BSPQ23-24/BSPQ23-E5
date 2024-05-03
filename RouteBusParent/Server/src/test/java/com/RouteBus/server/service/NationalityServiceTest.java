package com.RouteBus.server.service;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NationalityServiceTest {

    private static final Logger logger = Logger.getLogger(NationalityServiceTest.class);

    @Mock
    private NationalityRepository nationalityRepository;

    private NationalityService nationalityService;

    @Before
    public void setUp() {
        nationalityService = new NationalityService(nationalityRepository);
    }

    @Test
    public void getAllNationalities_returnsAllNationalities() {
        logger.debug("Starting test getAllNationalities_returnsAllNationalities...");

        Nationality nationality1 = new Nationality("American", "en");
        Nationality nationality2 = new Nationality("British", "en");
        List<Nationality> mockNationalities = Arrays.asList(nationality1, nationality2);
        when(nationalityRepository.findAll()).thenReturn(mockNationalities);

        List<Nationality> allNationalities = nationalityService.getAllNationalities();

        assertEquals(2, allNationalities.size());
        assertEquals("American", allNationalities.get(0).getName());
        assertEquals("British", allNationalities.get(1).getName());

        logger.debug("Test getAllNationalities_returnsAllNationalities passed successfully.");
    }
}
