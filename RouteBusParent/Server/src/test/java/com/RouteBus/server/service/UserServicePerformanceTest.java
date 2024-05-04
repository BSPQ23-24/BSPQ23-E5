package com.RouteBus.server.service;

import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.User;
import com.RouteBus.server.model.UserRole;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserServicePerformanceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    public UserServicePerformanceTest() {
        String reportPath = generateReportPath(UserServicePerformanceTest.class);
        HtmlReportGenerator reportGenerator = new HtmlReportGenerator(reportPath);
        this.perfTestRule = new JUnitPerfRule(reportGenerator);
    }

    @Rule
    public JUnitPerfRule perfTestRule;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    @JUnitPerfTest(threads = 50, durationMs = 5000, warmUpMs = 1000, maxExecutionsPerSecond = 100)
    public void testCreateUserPerformance() {
        User newUser = mockUser("test" + UUID.randomUUID() + "@example.com");
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        userService.createUser(newUser);
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetUserByEmailPerformance() {
        User user = mockUser("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        userService.getUserByEmail("user@example.com");
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 3000, warmUpMs = 500, maxExecutionsPerSecond = 50)
    public void testGetAllUsersPerformance() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser("user1@example.com"), mockUser("user2@example.com")));
        userService.getAllUsers();
    }
    
    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000, warmUpMs = 1000, maxExecutionsPerSecond = 50)
    public void testUpdateUserPerformance() {
        User existingUser = mockUser("existing@example.com");
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = new User();
        updatedUser.setFirstName("UpdatedName");

        userService.updateUser(updatedUser);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 500, maxExecutionsPerSecond = 30)
    public void testDeleteUserPerformance() {
        User userToDelete = mockUser("delete@example.com");
        when(userRepository.findByEmail("delete@example.com")).thenReturn(Optional.of(userToDelete));
        doNothing().when(userRepository).delete(userToDelete);
        userService.deleteUser("delete@example.com");
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 1000, warmUpMs = 500, maxExecutionsPerSecond = 20)
    public void testDeleteAllUsersPerformance() {
        doNothing().when(userRepository).deleteAll();
        userService.deleteAllUsers();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2500, warmUpMs = 500, maxExecutionsPerSecond = 40)
    public void testCheckUserPerformance() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(new User()));
        userService.checkUser("user@example.com");
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2500, warmUpMs = 500, maxExecutionsPerSecond = 40)
    public void testCheckPasswordPerformance() {
        User user = new User();
        user.setPassword("password123");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        userService.checkPassword("user@example.com", "password123");
    }

    private String generateReportPath(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String className = clazz.getSimpleName();
        String directory = packageName.replace(".", "/");
        return "target/performance_reports/" + directory + "/" + className + ".html";
    }

    private User mockUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setPassword("password123");
        user.setBirthDate(new Date());
        user.setRole(UserRole.CUSTOMER);
        return user;
    }
}
