package com.RouteBus.server.service;

import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final Logger logger = Logger.getLogger(UserServiceTest.class);

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        userService = new UserService(userRepository);
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
    }

    @Test
    public void getUserById_found() {
        logger.debug("Starting test getUserById_found...");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User found = userService.getUserById(1L);
        assertNotNull(found);
        assertEquals("user@example.com", found.getEmail());

        logger.debug("Test getUserById_found passed successfully.");
    }

    @Test
    public void getUserById_notFound() {
        logger.debug("Starting test getUserById_notFound...");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User found = userService.getUserById(1L);
        assertNull(found);

        logger.debug("Test getUserById_notFound passed successfully.");
    }

    @Test
    public void getUserByEmail_found() {
        logger.debug("Starting test getUserByEmail_found...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        User found = userService.getUserByEmail("user@example.com");
        assertNotNull(found);
        assertEquals("user@example.com", found.getEmail());

        logger.debug("Test getUserByEmail_found passed successfully.");
    }

    @Test
    public void getUserByEmail_notFound() {
        logger.debug("Starting test getUserByEmail_notFound...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        User found = userService.getUserByEmail("user@example.com");
        assertNull(found);

        logger.debug("Test getUserByEmail_notFound passed successfully.");
    }

    @Test
    public void getAllUsers() {
        logger.debug("Starting test getAllUsers...");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> users = userService.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("user@example.com", users.get(0).getEmail());

        logger.debug("Test getAllUsers passed successfully.");
    }

    @Test
    public void createUser_saveReturnsNull() {
        logger.debug("Starting test createUser_saveReturnsNull...");

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(null);
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        UserService.UserServiceResult result = userService.createUser(newUser);
        assertEquals(UserService.UserServiceResult.ERROR, result);

        logger.debug("Test createUser_saveReturnsNull passed successfully.");
    }

    @Test
    public void createUser_success() {
        logger.debug("Starting test createUser_success...");

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        UserService.UserServiceResult result = userService.createUser(newUser);
        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.debug("Test createUser_success passed successfully.");
    }
    
    @Test
    public void createUser_userAlreadyExists() {
        logger.debug("Starting test createUser_userAlreadyExists...");

        User existingUser = new User();
        existingUser.setEmail("existinguser@example.com");
        when(userRepository.findByEmail("existinguser@example.com")).thenReturn(Optional.of(existingUser));
        User newUser = new User();
        newUser.setEmail("existinguser@example.com");
        UserService.UserServiceResult result = userService.createUser(newUser);
        assertEquals(UserService.UserServiceResult.USER_ALREADY_EXISTS, result);

        logger.debug("Test createUser_userAlreadyExists passed successfully.");
    }

    @Test
    public void createUser_error() {
        logger.debug("Starting test createUser_error...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        UserService.UserServiceResult result = userService.createUser(user);
        assertEquals(UserService.UserServiceResult.ERROR, result);

        logger.debug("Test createUser_error passed successfully.");
    }

    @Test
    public void updateUser_found() {
        logger.debug("Starting test updateUser_found...");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        user.setFirstName("Diego");
        UserService.UserServiceResult result = userService.updateUser(user);
        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.debug("Test updateUser_found passed successfully.");
    }

    @Test
    public void updateUser_notFound() {
        logger.debug("Starting test updateUser_notFound...");

        UserService.UserServiceResult result = userService.updateUser(user);
        assertEquals(UserService.UserServiceResult.USER_NOT_FOUND, result);

        logger.debug("Test updateUser_notFound passed successfully.");
    }

    @Test
    public void deleteUser_found() {
        logger.debug("Starting test deleteUser_found...");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        UserService.UserServiceResult result = userService.deleteUser("test@example.com");
        assertEquals(UserService.UserServiceResult.SUCCESS, result);
        verify(userRepository).delete(user);

        logger.debug("Test deleteUser_found passed successfully.");
    }

    @Test
    public void deleteUser_notFound() {
        logger.debug("Starting test deleteUser_notFound...");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        UserService.UserServiceResult result = userService.deleteUser("test@example.com");
        assertEquals(UserService.UserServiceResult.USER_NOT_FOUND, result);

        logger.debug("Test deleteUser_notFound passed successfully.");
    }

    @Test
    public void deleteAllUsers() {
        logger.debug("Starting test deleteAllUsers...");

        UserService.UserServiceResult result = userService.deleteAllUsers();
        assertEquals(UserService.UserServiceResult.SUCCESS, result);

        logger.debug("Test deleteAllUsers passed successfully.");
    }

    @Test
    public void checkUser_found() {
        logger.debug("Starting test checkUser_found...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        assertTrue(userService.checkUser("user@example.com"));

        logger.debug("Test checkUser_found passed successfully.");
    }

    @Test
    public void checkUser_notFound() {
        logger.debug("Starting test checkUser_notFound...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        assertFalse(userService.checkUser("user@example.com"));

        logger.debug("Test checkUser_notFound passed successfully.");
    }

    @Test
    public void checkPassword_correct() {
        logger.debug("Starting test checkPassword_correct...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        assertTrue(userService.checkPassword("user@example.com", "password123"));

        logger.debug("Test checkPassword_correct passed successfully.");
    }

    @Test
    public void checkPassword_incorrect() {
        logger.debug("Starting test checkPassword_incorrect...");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        assertFalse(userService.checkPassword("user@example.com", "wrongpassword"));

        logger.debug("Test checkPassword_incorrect passed successfully.");
    }
}
