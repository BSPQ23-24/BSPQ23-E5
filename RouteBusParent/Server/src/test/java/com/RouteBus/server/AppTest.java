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
//import com.RouteBus.server.model.Nationality;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.lang.reflect.Method;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AppTest {
//
//    @Mock
//    private NationalityRepository nationalityRepository;
//
//    @InjectMocks
//    private App app;
//
//    @Before
//    public void setUp() {
//    }
//
//    @Test
//    public void testDemoWithEmptyDatabase() throws Exception {
//        when(nationalityRepository.count()).thenReturn(0L);
//        CommandLineRunner runner = app.demo(nationalityRepository);
//        runner.run(new String[]{});
//        verify(nationalityRepository, times(App.getNumberOfNationalities())).save(any(Nationality.class));
//        verify(nationalityRepository, atLeastOnce()).count();
//        verifyNoMoreInteractions(nationalityRepository);
//    }
//
//    @Test
//    public void testDemoWithNonEmptyDatabase() throws Exception {
//        when(nationalityRepository.count()).thenReturn(1L);
//        CommandLineRunner runner = app.demo(nationalityRepository);
//        runner.run(new String[]{});
//        verify(nationalityRepository, never()).save(any(Nationality.class));
//        verify(nationalityRepository, atLeastOnce()).count();
//        verifyNoMoreInteractions(nationalityRepository);
//    }
//
//    @Test
//    public void testGetNumberOfNationalities() {
//        int expected = 29;
//        assertEquals(expected, App.getNumberOfNationalities());
//    }
//
//    @Test
//    public void testGetNumberOfLoadedNationalities() {
//        assertEquals(0, App.getNumberOfLoadedNationalities());
//    }
//
//    @Test
//    public void testMainMethod() throws Exception {
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        Method mainMethod = App.class.getMethod("main", String[].class);
//        mainMethod.invoke(null, (Object) new String[]{});
//        System.setOut(System.out);
//        String expectedOutput = "Started App";
//        assertTrue(outContent.toString().contains(expectedOutput));
//    }
//
//}
