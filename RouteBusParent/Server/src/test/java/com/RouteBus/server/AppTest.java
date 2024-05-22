//package com.RouteBus.server;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.CommandLineRunner;
//
//import com.RouteBus.server.dao.NationalityRepository;
//import com.RouteBus.server.dao.UserRepository;
//import com.RouteBus.server.dao.BusRepository;
//import com.RouteBus.server.dao.StationRepository;
//import com.RouteBus.server.dao.RouteRepository;
//import com.RouteBus.server.dao.ScheduleRepository;
//import com.RouteBus.server.dao.TicketRepository;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AppTest {
//
//    @Mock
//    private NationalityRepository nationalityRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BusRepository busRepository;
//
//    @Mock
//    private StationRepository stationRepository;
//
//    @Mock
//    private RouteRepository routeRepository;
//
//    @Mock
//    private ScheduleRepository scheduleRepository;
//
//    @Mock
//    private TicketRepository ticketRepository;
//
//    @InjectMocks
//    private App app;
//
//    @Before
//    public void setUp() {
//        // Any initialization before tests run
//    }
//
//    @Test
//    public void testDemoWithEmptyDatabase() throws Exception {
//        when(nationalityRepository.count()).thenReturn(0L);
//        CommandLineRunner runner = app.demo(busRepository, nationalityRepository, routeRepository, scheduleRepository, stationRepository, ticketRepository, userRepository);
//        runner.run(new String[]{});
//        
//        verify(nationalityRepository, times(1)).saveAll(anyList());
//        verify(nationalityRepository, atLeastOnce()).count();
//        verifyNoMoreInteractions(nationalityRepository);
//    }
//
//    @Test
//    public void testDemoWithNonEmptyDatabase() throws Exception {
//        when(nationalityRepository.count()).thenReturn(1L);
//        CommandLineRunner runner = app.demo(busRepository, nationalityRepository, routeRepository, scheduleRepository, stationRepository, ticketRepository, userRepository);
//        runner.run(new String[]{});
//        
//        verify(nationalityRepository, never()).saveAll(anyList());
//        verify(nationalityRepository, atLeastOnce()).count();
//        verifyNoMoreInteractions(nationalityRepository);
//    }
//    
//    @Test
//    public void testMainMethod() throws Exception {
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        App.main(new String[]{});
//
//        System.setOut(System.out);
//
//        String expectedOutput = "Started App";
//        assertTrue(outContent.toString().contains(expectedOutput));
//    }
//}