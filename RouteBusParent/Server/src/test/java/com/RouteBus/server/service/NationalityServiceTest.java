package com.RouteBus.server.service;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NationalityServiceTest {

    @Mock
    private NationalityRepository nationalityRepository;

    @InjectMocks
    private NationalityService nationalityService;

    private final Logger logger = Logger.getLogger(NationalityServiceTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void testGetAllNationalities() {
        List<Nationality> nationalityList = List.of(
                new Nationality("Spain", "Spanish"),
                new Nationality("France", "French")
        );
        when(nationalityRepository.findAll()).thenReturn(nationalityList);

        List<Nationality> result = nationalityService.getAllNationalities();
        assertEquals(nationalityList, result);

        logger.info("getAllNationalities test passed.");
    }
}
